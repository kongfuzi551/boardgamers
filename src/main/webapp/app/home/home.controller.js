(function() {
    'use strict';

    angular
        .module('boardGamesApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', 'BoardGame', 'BoardGameSearch', 'ParseLinks', 'AlertService', '$state'];

    function HomeController ($scope, Principal, LoginService, BoardGame, BoardGameSearch,  ParseLinks, AlertService, $state) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;

        // Board game
        vm.boardGames = [];
        vm.loadPage = loadPage;
        vm.page = 0;
        vm.links = {
           last: 0
        };
        vm.predicate = 'usersRated';
        vm.reset = reset;
        vm.reverse = false;
        vm.clear = clear;
        vm.loadAll = loadAll;
        vm.search = search;
        vm.showActions = showActions;

        vm.actions = -1;

        getAccount();
        loadAll();

        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        function showActions() {
            vm.actions = true;
        }

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }

       function loadPage(page) {
            vm.page = page;
            loadAll();
        }

        function loadAll () {
            if (vm.currentSearch) {
                BoardGameSearch.query({
                    query: vm.currentSearch,
                    page: vm.page,
                    size: 20,
                    sort: sort()
                }, onSuccess, onError);
            } else {
                BoardGame.query({
                    page: vm.page,
                    size: 20,
                    sort: sort()
                }, onSuccess, onError);
            }
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                for (var i = 0; i < data.length; i++) {
                    vm.boardGames.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function reset () {
            vm.page = 0;
            vm.boardGames = [];
            loadAll();
        }

          function clear () {
                    vm.boardGames = [];
                    vm.links = null;
                    vm.page = 0;
                    vm.predicate = 'id';
                    vm.reverse = true;
                    vm.searchQuery = null;
                    vm.currentSearch = null;
                    vm.loadAll();
                }

                function search (searchQuery) {
                    if (!searchQuery){
                        return vm.clear();
                    }
                    vm.boardGames = [];
                    vm.links = null;
                    vm.page = 0;
                    vm.predicate = '_score';
                    vm.reverse = false;
                    vm.currentSearch = searchQuery;
                    vm.loadAll();
                }
    }

})();

// IVR: Darken image
//$('.darken').hover(function() {
//    $(this).find('img').fadeTo(500, 0.5);
//}, function() {
//    $(this).find('img').fadeTo(500, 1);
//});


