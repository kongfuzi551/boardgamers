(function() {
    'use strict';
    angular
        .module('boardGamesApp')
        .factory('BoardGame', BoardGame);

    BoardGame.$inject = ['$resource'];

    function BoardGame ($resource) {
        var resourceUrl =  'api/board-games/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
