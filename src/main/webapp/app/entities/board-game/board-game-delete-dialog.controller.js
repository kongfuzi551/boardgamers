(function() {
    'use strict';

    angular
        .module('boardGamesApp')
        .controller('BoardGameDeleteController',BoardGameDeleteController);

    BoardGameDeleteController.$inject = ['$uibModalInstance', 'entity', 'BoardGame'];

    function BoardGameDeleteController($uibModalInstance, entity, BoardGame) {
        var vm = this;

        vm.boardGame = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BoardGame.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
