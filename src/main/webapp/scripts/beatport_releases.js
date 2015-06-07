(function() {

    var app = angular.module( 'beatportReleases', [] );

    app.controller( 'BeatportReleasesController', [ 'BeatportRelease', function( BeatportRelease ) {
        var ctrl = this;
        this.filterText = '';
        this.releases = [];
        this.importUrl = '';

        this.getList = function() {
            ctrl.releases = BeatportRelease.query();
        };

        this.import = function() {
            BeatportRelease.import( { url: ctrl.importUrl }, function() {
                ctrl.importUrl = '';
                ctrl.getList();
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };
        
        this.reimport = function( release ) {
            release.$reimport( {}, function() {
                alert( 'Reimported' );
                ctrl.getList();
            }, function( httpResponse  ) {
                alert( 'Error ' + httpResponse  );
            });
        };
        
        this.delete = function( release ) {
            if ( confirm( "Are you sure you want to delete this release?" ) === true ) {
                release.$delete( {}, function() {
                    ctrl.getList();
                }).error( function( httpResponse ) {
                    alert( 'Error ' + httpResponse );
                });
            }
        };
        
        this.getList();
    }]);

    app.controller( 'BeatportReleaseDetailsController', [ 'BeatportRelease', 'BeatportTrack', '$routeParams', '$location', function( BeatportRelease, BeatportTrack, $routeParams, $location ) {
        var ctrl = this;
        
        this.getList = function() {
            ctrl.release = BeatportRelease.get( { id: $routeParams.id } );
            ctrl.tracks = BeatportTrack.byRelease( { releaseId: $routeParams.id } );
        };
        
        this.importToLibrary = function( release ) {
            BeatportRelease.import( { id: release.id }, function( data ) {
                $location.path( '/releases/' + data.id );
            }, function( httpResponse  ) {
                alert( 'Error ' + httpResponse  );
            });
        };
        
        this.reimport = function( release ) {
            release.$reimport( {}, function() {
                alert( 'Reimported' );
                ctrl.getList();
            }, function( httpResponse  ) {
                alert( 'Error ' + httpResponse  );
            });
        };
        
        this.delete = function( release ) {
            if ( confirm( "Are you sure you want to delete this release?" ) === true ) {
                release.$delete( {}, function() {
                    $location.path( '/beatport/releases/' );
                }).error( function( httpResponse ) {
                    alert( 'Error ' + httpResponse );
                });
            }
        };
        
        this.getList();
    }]);

    app.directive("beatportReleases", function() {
        return {
            restrict: 'A',
            scope: {
                releases: '=',
                showLabel: '@'
            },
            templateUrl: 'views/beatport/releases/list_directive.html'
        };
    });
    
})();
