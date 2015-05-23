(function() {

    var app = angular.module( 'mixxxPlaylists', [] );

    app.controller( 'MixxxPlaylistsController', [ 'MixxxPlaylist', function( MixxxPlaylist ) {
        this.filterText = '';
        this.playlists = MixxxPlaylist.query();
    }]);

    app.controller( 'MixxxPlaylistDetailsController', [ 'MixxxPlaylist', 'MixxxPlaylistTrack', '$routeParams', function( MixxxPlaylist, MixxxPlaylistTrack, $routeParams ) {
        this.id = $routeParams.id;
        this.playlist = MixxxPlaylist.get( { id: $routeParams.id } );
        this.tracks = MixxxPlaylistTrack.query( { playlistId: $routeParams.id } );
    }]);

})();
