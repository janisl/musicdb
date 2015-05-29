(function() {

    var app = angular.module( 'discogsReleases', [] );

    app.controller('DiscogsReleaseDetailsController', [ 'DiscogsRelease', '$routeParams', function( DiscogsRelease, $routeParams ) {
        this.release = DiscogsRelease.get( { id: $routeParams.id } );
    }]);

})();
