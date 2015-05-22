(function() {

    var app = angular.module( 'beatportTracks', [] );

    app.controller( 'BeatportTracksController', [ 'BeatportTrack', function( BeatportTrack ) {
        var ctrl = this;
        this.filterText = '';
        this.tracks = [];
        this.importUrl = '';

        this.getList = function() {
            ctrl.tracks = BeatportTrack.query();
        };
        
        this.delete = function( track ) {
            if ( confirm( "Are you sure you want to delete this track?" ) === true ) {
                track.$delete( {}, function() {
                    ctrl.getList();
                }).error( function( httpResponse ) {
                    alert( 'Error ' + httpResponse );
                });
            }
        };
        
        this.getList();
    }]);

})();
