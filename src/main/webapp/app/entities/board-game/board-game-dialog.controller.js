(function() {
    'use strict';

    angular
        .module('boardGamesApp')
        .controller('BoardGameDialogController', BoardGameDialogController);

    BoardGameDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BoardGame'];

    function BoardGameDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BoardGame) {
        var vm = this;

        vm.boardGame = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.boardGame.id !== null) {
                BoardGame.update(vm.boardGame, onSaveSuccess, onSaveError);
            } else {
                BoardGame.save(vm.boardGame, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('boardGamesApp:boardGameUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
