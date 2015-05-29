(function () {

    var app = angular.module( 'musicdb', [ 'ngRoute', 'resources',
        'artists', 'genres', 'labels', 'releases', 'tracks',
        'beatportArtists', 'beatportGenres', 'beatportLabels', 'beatportReleases', 'beatportTracks',
        'mixxxCrates', 'mixxxPlaylists', 'mixxxTracks',
        'discogsArtists', 'discogsLabels' ]);

    app.config( [ '$routeProvider', function( $routeProvider ) {
        $routeProvider
            .when( '/', {
                templateUrl: 'views/home.html',
                controller: 'HomeController',
                controllerAs: 'home'
            })
            .when( '/artists/', {
                templateUrl: 'views/artists/list.html',
                controller: 'ArtistsController',
                controllerAs: 'artists'
            })
            .when( '/artists/add/', {
                templateUrl: 'views/artists/add_edit.html',
                controller: 'ArtistAddController',
                controllerAs: 'artistCtrl'
            })
            .when( '/artists/:id/', {
                templateUrl: 'views/artists/add_edit.html',
                controller: 'ArtistEditController',
                controllerAs: 'artistCtrl'
            })
            .when( '/genres/', {
                templateUrl: 'views/genres/list.html',
                controller: 'GenresController',
                controllerAs: 'genres'
            })
            .when( '/genres/add/', {
                templateUrl: 'views/genres/add_edit.html',
                controller: 'GenreAddController',
                controllerAs: 'genreCtrl'
            })
            .when( '/genres/:id/', {
                templateUrl: 'views/genres/add_edit.html',
                controller: 'GenreEditController',
                controllerAs: 'genreCtrl'
            })
            .when( '/labels/', {
                templateUrl: 'views/labels/list.html',
                controller: 'LabelsController',
                controllerAs: 'labels'
            })
            .when( '/labels/add/', {
                templateUrl: 'views/labels/add_edit.html',
                controller: 'LabelAddController',
                controllerAs: 'labelCtrl'
            })
            .when( '/labels/:id/', {
                templateUrl: 'views/labels/add_edit.html',
                controller: 'LabelEditController',
                controllerAs: 'labelCtrl'
            })
            .when( '/releases/', {
                templateUrl: 'views/releases/list.html',
                controller: 'ReleasesController',
                controllerAs: 'releases'
            })
            .when( '/releases/add/', {
                templateUrl: 'views/releases/add_edit.html',
                controller: 'ReleaseAddController',
                controllerAs: 'releaseCtrl'
            })
            .when( '/releases/:id/', {
                templateUrl: 'views/releases/add_edit.html',
                controller: 'ReleaseEditController',
                controllerAs: 'releaseCtrl'
            })
            .when( '/tracks/', {
                templateUrl: 'views/tracks/list.html',
                controller: 'TracksController',
                controllerAs: 'tracks'
            })
            .when( '/tracks/add/', {
                templateUrl: 'views/tracks/add_edit.html',
                controller: 'TrackAddController',
                controllerAs: 'trackCtrl'
            })
            .when( '/tracks/:id/', {
                templateUrl: 'views/tracks/add_edit.html',
                controller: 'TrackEditController',
                controllerAs: 'trackCtrl'
            })
            .when( '/pianos/', {
                templateUrl: 'views/pianos.html'
            })
            
            .when( '/beatport/artists/', {
                templateUrl: 'views/beatport/artists/list.html',
                controller: 'BeatportArtistsController',
                controllerAs: 'artists'
            })
            .when( '/beatport/artists/:id/', {
                templateUrl: 'views/beatport/artists/details.html',
                controller: 'BeatportArtistDetailsController',
                controllerAs: 'artistCtrl'
            })
            .when( '/beatport/genres/', {
                templateUrl: 'views/beatport/genres/list.html',
                controller: 'BeatportGenresController',
                controllerAs: 'genres'
            })
            .when( '/beatport/labels/', {
                templateUrl: 'views/beatport/labels/list.html',
                controller: 'BeatportLabelsController',
                controllerAs: 'labels'
            })
            .when( '/beatport/labels/:id/', {
                templateUrl: 'views/beatport/labels/details.html',
                controller: 'BeatportLabelDetailsController',
                controllerAs: 'labelCtrl'
            })
            .when( '/beatport/releases/', {
                templateUrl: 'views/beatport/releases/list.html',
                controller: 'BeatportReleasesController',
                controllerAs: 'releases'
            })
            .when( '/beatport/releases/:id', {
                templateUrl: 'views/beatport/releases/details.html',
                controller: 'BeatportReleaseDetailsController',
                controllerAs: 'releaseCtrl'
            })
            .when( '/beatport/tracks/', {
                templateUrl: 'views/beatport/tracks/list.html',
                controller: 'BeatportTracksController',
                controllerAs: 'tracks'
            })
            
            .when( '/mixxx/tracks/', {
                templateUrl: 'views/mixxx/tracks/list.html',
                controller: 'MixxxTracksController',
                controllerAs: 'tracks'
            })
            .when( '/mixxx/tracks/:trackId/cues/', {
                templateUrl: 'views/mixxx/tracks/cues.html',
                controller: 'MixxxTrackCuesController',
                controllerAs: 'cues'
            })
            .when( '/mixxx/playlists/', {
                templateUrl: 'views/mixxx/playlists/list.html',
                controller: 'MixxxPlaylistsController',
                controllerAs: 'playlists'
            })
            .when( '/mixxx/playlists/:id/', {
                templateUrl: 'views/mixxx/playlists/details.html',
                controller: 'MixxxPlaylistDetailsController',
                controllerAs: 'playlistCtrl'
            })
            .when( '/mixxx/crates/', {
                templateUrl: 'views/mixxx/crates/list.html',
                controller: 'MixxxCratesController',
                controllerAs: 'crates'
            })
            .when( '/mixxx/crates/:id/', {
                templateUrl: 'views/mixxx/crates/details.html',
                controller: 'MixxxCrateDetailsController',
                controllerAs: 'crateCtrl'
            })

            .when( '/discogs/artists/:id/', {
                templateUrl: 'views/discogs/artist.html',
                controller: 'DiscogsArtistDetailsController',
                controllerAs: 'artistCtrl'
            })
            .when( '/discogs/labels/:id/', {
                templateUrl: 'views/discogs/label.html',
                controller: 'DiscogsLabelDetailsController',
                controllerAs: 'labelCtrl'
            })
            
            .otherwise({
                redirectTo: '/'
            });
    }]);

    app.controller('HomeController', [function () {
    }]);

    app.filter('trackTime', function() {
        return function( input ) {
            input = input || '';
            if ( input.substring( 0, 3 ) === "00:" ) {
                return input.substring( 3 );
            }
            return input;
        };
    });
    
})();
