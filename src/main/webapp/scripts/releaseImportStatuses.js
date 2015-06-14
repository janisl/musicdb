(function () {

    var app = angular.module( 'releaseImportStatuses', [] );

    app.controller( 'ReleaseImportStatusesController', [ 'ReleaseImportStatus', function( ReleaseImportStatus ) {
        var ctrl = this;
        this.filterText = '';
        this.statuses = [];

        this.getList = function() {
            ctrl.statuses = ReleaseImportStatus.query();
        };
        
        this.delete = function( status ) {
            if ( confirm( "Are you sure you want to delete this status?" ) === true ) {
                status.$delete( {}, function() {
                    ctrl.getList();
                }, function( httpResponse ) {
                    alert( 'Error ' + httpResponse );
                });
            }
        };
        
        this.getList();
    }]);

    app.controller( 'ReleaseImportStatusAddController', [ 'ReleaseImportStatus', '$location', function( ReleaseImportStatus, $location ) {
        var ctrl = this;
        this.status = new ReleaseImportStatus();

        this.submit = function() {
            ctrl.status.$save( {}, function() {
                $location.path( '/releaseImportStatuses/' );
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };
    }]);

    app.controller( 'ReleaseImportStatusEditController', [ 'ReleaseImportStatus', '$routeParams', '$location', function( ReleaseImportStatus, $routeParams, $location ) {
        var ctrl = this;
        this.id = $routeParams.id;
        ctrl.status = ReleaseImportStatus.get( { id: $routeParams.id } );

        this.submit = function () {
            ctrl.status.$update( {}, function() {
                $location.path( '/releaseImportStatuses/' );
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };
    }]);

    app.controller( 'ReleaseImportStatusDetailsController', [ 'Release', 'ReleaseImportStatus', '$routeParams', function( Release, ReleaseImportStatus, $routeParams ) {
        var ctrl = this;
        ctrl.status = ReleaseImportStatus.get( { id: $routeParams.id } );
        ctrl.releases = Release.byImportStatus( { importStatusId: $routeParams.id } );
    }]);

    app.controller( 'ReleasesWithoutImportStatusController', [ 'Release', function( Release ) {
        var ctrl = this;
        ctrl.status = { name: "Without status" };
        ctrl.releases = Release.withoutImportStatus();
    }]);

})();
