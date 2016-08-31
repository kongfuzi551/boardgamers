(function() {
    'use strict';

    angular
        .module('boardGamesApp')
        .controller('BoardGameController', BoardGameController);

    BoardGameController.$inject = ['$scope', '$state', 'BoardGame', 'BoardGameSearch'];

    function BoardGameController ($scope, $state, BoardGame, BoardGameSearch) {
        var vm = this;
        
        vm.boardGames = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

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
