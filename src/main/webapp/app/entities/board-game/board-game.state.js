(function() {
    'use strict';

    angular
        .module('boardGamesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('board-game', {
            parent: 'entity',
            url: '/board-game',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'BoardGames'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/board-game/board-games.html',
                    controller: 'BoardGameController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('board-game-detail', {
            parent: 'entity',
            url: '/board-game/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'BoardGame'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/board-game/board-game-detail.html',
                    controller: 'BoardGameDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'BoardGame', function($stateParams, BoardGame) {
                    return BoardGame.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'board-game',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('board-game-detail.edit', {
            parent: 'board-game-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/board-game/board-game-dialog.html',
                    controller: 'BoardGameDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BoardGame', function(BoardGame) {
                            return BoardGame.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('board-game.new', {
            parent: 'board-game',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/board-game/board-game-dialog.html',
                    controller: 'BoardGameDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                yearPublished: null,
                                minPlayers: null,
                                maxPlayers: null,
                                playingTime: null,
                                minAge: null,
                                minPlaytime: null,
                                maxPlaytime: null,
                                description: null,
                                thumbnail: null,
                                usersRated: null,
                                avgRating: null,
                                bayesAvgRating: null,
                                rank: null,
                                image: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('board-game', null, { reload: true });
                }, function() {
                    $state.go('board-game');
                });
            }]
        })
        .state('board-game.edit', {
            parent: 'board-game',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/board-game/board-game-dialog.html',
                    controller: 'BoardGameDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BoardGame', function(BoardGame) {
                            return BoardGame.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('board-game', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('board-game.delete', {
            parent: 'board-game',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/board-game/board-game-delete-dialog.html',
                    controller: 'BoardGameDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BoardGame', function(BoardGame) {
                            return BoardGame.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('board-game', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
