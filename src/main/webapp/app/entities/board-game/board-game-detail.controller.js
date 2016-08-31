(function() {
    'use strict';

    angular
        .module('boardGamesApp')
        .controller('BoardGameDetailController', BoardGameDetailController);

    BoardGameDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BoardGame'];

    function BoardGameDetailController($scope, $rootScope, $stateParams, previousState, entity, BoardGame) {
        var vm = this;

        vm.boardGame = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('boardGamesApp:boardGameUpdate', function(event, result) {
            vm.boardGame = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
