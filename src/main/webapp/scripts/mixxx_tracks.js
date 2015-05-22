(function() {

    var app = angular.module( 'mixxxTracks', [] );

    app.controller( 'MixxxTracksController', [ 'MixxxTrack', function( MixxxTrack ) {
        this.filterText = '';
        this.tracks = MixxxTrack.query();
    }]);

    app.controller( 'MixxxTrackCuesController', [ 'MixxxTrackCue', '$routeParams', function( MixxxTrackCue, $routeParams ) {
        this.filterText = '';
        this.cues = MixxxTrackCue.query( { trackId: $routeParams.trackId } );
    }]);

})();
