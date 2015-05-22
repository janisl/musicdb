(function() {

    var app = angular.module( 'mixxxTracks', [] );

    app.controller( 'MixxxTracksController', [ 'MixxxTrack', function( MixxxTrack ) {
        this.filterText = '';
        this.tracks = MixxxTrack.query();
    }]);

})();
