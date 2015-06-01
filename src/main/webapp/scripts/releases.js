(function() {

    var app = angular.module( 'releases', [] );

    app.controller( 'ReleasesController', [ 'Release', function( Release ) {
        var ctrl = this;
        this.filterText = '';
        this.releases = [];

        this.getList = function() {
            ctrl.releases = Release.query();
        };

        this.delete = function( release ) {
            if ( confirm( "Are you sure you want to delete this release?" ) === true ) {
                release.$delete( {}, function() {
                    ctrl.getList();
                }, function( httpResponse ) {
                    alert( 'Error ' + httpResponse );
                });
            }
        };

        this.getList();
    }]);

    app.controller( 'ReleaseAddController', [ 'Release', '$location', function( Release, $location ) {
        var ctrl = this;
        this.release = new Release();
        this.release.tracks = [];

        this.submit = function() {
            ctrl.release.$save( {}, function() {
                $location.path( '/releases/' );
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };
        
        this.addTrack = function() {
            ctrl.release.tracks.push( {} );
        };
    }]);

    app.controller( 'ReleaseEditController', [ 'Release', '$routeParams', '$location', function( Release, $routeParams, $location ) {
        var ctrl = this;
        this.id = $routeParams.id;
        this.release = Release.get( { id: $routeParams.id } );

        this.submit = function() {
            ctrl.release.$update( {}, function() {
                $location.path( '/releases/' + ctrl.id );
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };
        
        this.addTrack = function() {
            ctrl.release.tracks.push( {} );
        };
        
        this.removeTrack = function( track ) {
            for ( var i = ctrl.release.tracks.length - 1; i >= 0; i-- ) {
                if (ctrl.release.tracks[i] === track) {
                   ctrl.release.tracks.splice( i, 1 );
                }
            }
        };
    }]);

    app.controller( 'ReleaseDetailsController', [ 'Release', '$routeParams', '$location', function( Release, $routeParams, $location ) {
        var ctrl = this;
        this.id = $routeParams.id;
        this.release = Release.get( { id: $routeParams.id } );

        this.delete = function() {
            if ( confirm( "Are you sure you want to delete this release?" ) === true ) {
                ctrl.release.$delete( {}, function() {
                    $location.path( '/releases/' );
                }, function( httpResponse ) {
                    alert( 'Error ' + httpResponse );
                });
            }
        };
    }]);

})();
