(function() {

    var app = angular.module( 'beatportArtists', [] );

    app.controller( 'BeatportArtistsController', [ 'BeatportArtist', function( BeatportArtist ) {
        var ctrl = this;
        this.filterText = '';
        this.artists = [];
        this.importUrl = '';

        this.getList = function() {
            ctrl.artists = BeatportArtist.query();
        };

        this.import = function() {
            BeatportArtist.import( { url: ctrl.importUrl }, function() {
                ctrl.importUrl = '';
                ctrl.getList();
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };
        
        this.reimport = function( artist ) {
            artist.$reimport( {}, function() {
                alert( 'Reimported' );
                ctrl.getList();
            }, function( httpResponse  ) {
                alert( 'Error ' + httpResponse  );
            });
        };
        
        this.delete = function( artist ) {
            if ( confirm( "Are you sure you want to delete this artist?" ) === true ) {
                artist.$delete( {}, function() {
                    ctrl.getList();
                }).error( function( httpResponse ) {
                    alert( 'Error ' + httpResponse );
                });
            }
        };
        
        this.getList();
    }]);

    app.controller( 'BeatportArtistDetailsController', [ 'BeatportArtist', 'BeatportRelease', '$routeParams', '$location', function( BeatportArtist, BeatportRelease, $routeParams, $location ) {
        var ctrl = this;
        this.filterText = '';

        this.getList = function() {
            ctrl.artist = BeatportArtist.get( { id: $routeParams.id } );
            ctrl.releases = BeatportRelease.byArtist( { artistId: $routeParams.id } );
        };

        this.reimport = function( artist ) {
            artist.$reimport( {}, function() {
                alert( 'Reimported' );
                ctrl.getList();
            }, function( httpResponse  ) {
                alert( 'Error ' + httpResponse  );
            });
        };
        
        this.importReleases = function() {
            ctrl.artist.$importReleases( {}, function() {
                alert( 'Imported' );
                ctrl.getList();
            }, function( httpResponse  ) {
                alert( 'Error ' + httpResponse  );
            });
        };
        
        this.delete = function( artist ) {
            if ( confirm( "Are you sure you want to delete this artist?" ) === true ) {
                artist.$delete( {}, function() {
                    $location.path( '/beatport/artists/' );
                }).error( function( httpResponse ) {
                    alert( 'Error ' + httpResponse );
                });
            }
        };
        
        this.getList();
    }]);

    app.directive("beatportArtists", function() {
        return {
            restrict: 'A',
            scope: {
                artists: '='
            },
            templateUrl: 'views/beatport/artists/list_directive.html'
        };
    });

    app.directive("beatportLogo", function() {
        return {
            restrict: 'A',
            templateUrl: 'views/beatport/logo.html'
        };
    });

})();
