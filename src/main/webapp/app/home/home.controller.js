(function() {
    'use strict';

    angular
        .module('boardGamesApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', 'BoardGame', 'BoardGameSearch', '$state'];

    function HomeController ($scope, Principal, LoginService, BoardGame, BoardGameSearch, $state) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;

        // Board game
        vm.boardGames = [];
        vm.search = search;
        vm.loadAll = loadAll;


        getAccount();
        loadAll();

        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }

        function loadAll() {
            BoardGame.query(function(result) {
            vm.boardGames = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                 return vm.loadAll();
            }

            BoardGameSearch.query({query: vm.searchQuery}, function(result) {
                vm.boardGames = result;
            });
        }    }

})();
