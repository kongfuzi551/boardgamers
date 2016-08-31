(function() {
    'use strict';

    angular
        .module('boardGamesApp')
        .factory('BoardGameSearch', BoardGameSearch);

    BoardGameSearch.$inject = ['$resource'];

    function BoardGameSearch($resource) {
        var resourceUrl =  'api/_search/board-games/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
