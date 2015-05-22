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
            update: { method: 'PUT' }
        });
    }]);

    app.factory( 'Track', [ '$resource', function( $resource ) {
        return $resource( '/track/:id', { id: '@id' }, {
            update: { method: 'PUT' }
        });
    }]);

    app.factory( 'BeatportArtist', [ '$resource', function( $resource ) {
        return $resource( '/beatport/artist/:id', { id: '@id' }, {
            import: { method: 'GET', url: '/beatport/artist/get' },
            reimport: { method: 'GET', url: '/beatport/artist/:id/reimport' }
        });
    }]);

    app.factory( 'BeatportGenre', [ '$resource', function( $resource ) {
        return $resource( '/beatport/genre/:id', { id: '@id' } );
    }]);

    app.factory( 'BeatportLabel', [ '$resource', function( $resource ) {
        return $resource( '/beatport/label/:id', { id: '@id' }, {
            import: { method: 'GET', url: '/beatport/label/get' },
            reimport: { method: 'GET', url: '/beatport/label/:id/reimport' }
        });
    }]);

    app.factory( 'BeatportRelease', [ '$resource', function( $resource ) {
        return $resource( '/beatport/release/:id', { id: '@id' }, {
            import: { method: 'GET', url: '/beatport/release/get' },
            reimport: { method: 'GET', url: '/beatport/release/:id/reimport' }
        });
    }]);

    app.factory( 'BeatportTrack', [ '$resource', function( $resource ) {
        return $resource( '/beatport/track/:id', { id: '@id' } );
    }]);

    app.factory( 'MixxxTrack', [ '$resource', function( $resource ) {
        return $resource( '/mixxx/track/:id', { id: '@id' } );
    }]);

    app.factory( 'MixxxTrackCue', [ '$resource', function( $resource ) {
        return $resource( '/mixxx/track/:trackId/cues', { trackId: '@trackId' } );
    }]);

})();
