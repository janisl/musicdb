(function () {

    var app = angular.module( 'resources', [ 'ngResource' ] );

    app.config( [ '$resourceProvider', function( $resourceProvider ) {
        // Don't strip trailing slashes from calculated URLs
        $resourceProvider.defaults.stripTrailingSlashes = false;
    }]);

    app.factory( 'Artist', [ '$resource', function( $resource ) {
        return $resource( '/artist/:id', { id: '@id' }, {
            update: { method: 'PUT' }
        });
    }]);

    app.factory( 'Download', [ '$resource', function( $resource ) {
        return $resource( '/download');
    }]);

    app.factory( 'Genre', [ '$resource', function( $resource ) {
        return $resource( '/genre/:id', { id: '@id' }, {
            update: { method: 'PUT' }
        });
    }]);

    app.factory( 'Label', [ '$resource', function( $resource ) {
        return $resource( '/label/:id', { id: '@id' }, {
            update: { method: 'PUT' }
        });
    }]);

    app.factory( 'Release', [ '$resource', function( $resource ) {
        return $resource( '/release/:id', { id: '@id' }, {
            update: { method: 'PUT' },
            byImportStatus: { method: 'GET', url: '/releaseImportStatus/:importStatusId/releases', isArray: true },
            withoutImportStatus: { method: 'GET', url: '/releaseImportStatus/releases', isArray: true },
            createDir: { method: 'GET', url: '/release/:id/createDir' },
            setCover: { method: 'GET', url: '/release/:id/setCover' },
            moveCover: { method: 'GET', url: '/release/:id/moveCover' },
            moveTracks: { method: 'GET', url: '/release/:id/moveTracks' },
            moveTracksAndSetTags: { method: 'GET', url: '/release/:id/moveTracksAndSetTags' }
        });
    }]);

    app.factory( 'ReleaseImportStatus', [ '$resource', function( $resource ) {
        return $resource( '/releaseImportStatus/:id', { id: '@id' }, {
            update: { method: 'PUT' }
        });
    }]);

    app.factory( 'Track', [ '$resource', function( $resource ) {
        return $resource( '/track/:id', { id: '@id' }, {
            update: { method: 'PUT' },
            tags: { method: 'GET', url: '/track/:id/tags' },
            setTags: { method: 'GET', url: '/track/:id/setTags' }
        });
    }]);

    app.factory( 'TrackImportStatus', [ '$resource', function( $resource ) {
        return $resource( '/trackImportStatus/:id', { id: '@id' }, {
            update: { method: 'PUT' }
        });
    }]);

    app.factory( 'BeatportArtist', [ '$resource', function( $resource ) {
        return $resource( '/beatport/artist/:id', { id: '@id' }, {
            import: { method: 'GET', url: '/beatport/artist/get' },
            reimport: { method: 'GET', url: '/beatport/artist/:id/reimport' },
            importReleases: { method: 'GET', url: '/beatport/artist/:id/importReleases' }
        });
    }]);

    app.factory( 'BeatportGenre', [ '$resource', function( $resource ) {
        return $resource( '/beatport/genre/:id', { id: '@id' } );
    }]);

    app.factory( 'BeatportLabel', [ '$resource', function( $resource ) {
        return $resource( '/beatport/label/:id', { id: '@id' }, {
            import: { method: 'GET', url: '/beatport/label/get' },
            reimport: { method: 'GET', url: '/beatport/label/:id/reimport' },
            releases: { method: 'GET', url: '/beatport/label/:id/releases' },
            importReleases: { method: 'GET', url: '/beatport/label/:id/importReleases' }
        });
    }]);

    app.factory( 'BeatportRelease', [ '$resource', function( $resource ) {
        return $resource( '/beatport/release/:id', { id: '@id' }, {
            import: { method: 'GET', url: '/beatport/release/get' },
            reimport: { method: 'GET', url: '/beatport/release/:id/reimport' },
            importToLibrary: { method: 'GET', url: '/beatport/release/:id/import' },
            byLabel: { method: 'GET', url: '/beatport/label/:labelId/releases', isArray: true },
            byArtist: { method: 'GET', url: '/beatport/artist/:artistId/releases', isArray: true }
        });
    }]);

    app.factory( 'BeatportTrack', [ '$resource', function( $resource ) {
        return $resource( '/beatport/track/:id', { id: '@id' }, {
            byRelease: { method: 'GET', url: '/beatport/release/:releaseId/tracks', isArray: true }
        } );
    }]);

    app.factory( 'MixxxTrack', [ '$resource', function( $resource ) {
        return $resource( '/mixxx/track/:id', { id: '@id' }, {
            notLinked: { method: 'GET', url: '/mixxx/track/notLinked', isArray: true }
        } );
    }]);

    app.factory( 'MixxxTrackCue', [ '$resource', function( $resource ) {
        return $resource( '/mixxx/track/:trackId/cues', { trackId: '@trackId' } );
    }]);

    app.factory( 'MixxxPlaylist', [ '$resource', function( $resource ) {
        return $resource( '/mixxx/playlist/:id', { id: '@id' } );
    }]);

    app.factory( 'MixxxPlaylistTrack', [ '$resource', function( $resource ) {
        return $resource( '/mixxx/playlist/:playlistId/track', { playlistId: '@playlistId' } );
    }]);

    app.factory( 'MixxxCrate', [ '$resource', function( $resource ) {
        return $resource( '/mixxx/crate/:id', { id: '@id' } );
    }]);

    app.factory( 'MixxxCrateTrack', [ '$resource', function( $resource ) {
        return $resource( '/mixxx/crate/:crateId/track', { crateId: '@crateId' } );
    }]);

    app.factory( 'DiscogsLabel', [ '$resource', function( $resource ) {
        return $resource( '/discogs/labels/:id', { id: '@id' }, {
            releases: { method: 'GET', url: '/discogs/labels/:id/releases' }
        });
    }]);

    app.factory( 'DiscogsArtist', [ '$resource', function( $resource ) {
        return $resource( '/discogs/artists/:id', { id: '@id' }, {
            releases: { method: 'GET', url: '/discogs/artists/:id/releases' }
        });
    }]);

    app.factory( 'DiscogsMaster', [ '$resource', function( $resource ) {
        return $resource( '/discogs/masters/:id', { id: '@id' }, {
            versions: { method: 'GET', url: '/discogs/masters/:id/versions' }
        });
    }]);

    app.factory( 'DiscogsRelease', [ '$resource', function( $resource ) {
        return $resource( '/discogs/releases/:id', { id: '@id' } );
    }]);

    app.factory( 'DiscogsSearch', [ '$resource', function( $resource ) {
        return $resource( '/discogs/database/search', {}, {
            query: { method: 'GET', isArray: false } } );
    }]);

})();
