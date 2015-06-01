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

        this.submit = function() {
            ctrl.release.$save( {}, function() {
                $location.path( '/releases/' );
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };
    }]);

    app.controller( 'ReleaseEditController', [ 'Release', '$routeParams', '$location', function( Release, $routeParams, $location ) {
        var ctrl = this;
        this.id = $routeParams.id;
        this.release = Release.get( { id: $routeParams.id } );

        this.submit = function() {
            ctrl.release.$update( {}, function() {
                $location.path( '/releases/' );
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
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
