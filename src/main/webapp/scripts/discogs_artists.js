(function() {

    var app = angular.module( 'discogsArtists', [] );

    app.controller('DiscogsArtistDetailsController', [ 'DiscogsArtist', '$routeParams', function( DiscogsArtist, $routeParams ) {
        var ctrl = this;
        this.filterText = '';
        this.pages = [];
            
        this.artist = DiscogsArtist.get( { id: $routeParams.id }, function() {
            ctrl.getReleases( 1 );
        });
        
        this.getReleases = function( page ) {
            ctrl.releases = DiscogsArtist.releases( { id: $routeParams.id, page: page }, function() {
                ctrl.pages = [];
                for (var i = 1; i <= ctrl.releases.pagination.pages; i++) {
                    ctrl.pages.push( { pageNumber: i, isCurrent: i === ctrl.releases.pagination.page } );
                }
            });
        };
    }]);

    app.directive("discogsArtist", function() {
        return {
            restrict: 'A',
            scope: {
                artist: '=',
                last: '='
            },
            templateUrl: 'views/discogs/artist_directive.html'
        };
    });

})();
