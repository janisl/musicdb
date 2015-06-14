(function () {

    var app = angular.module( 'trackImportStatuses', [] );

    app.controller( 'TrackImportStatusesController', [ 'TrackImportStatus', function( TrackImportStatus ) {
        var ctrl = this;
        this.filterText = '';
        this.statuses = [];

        this.getList = function() {
            ctrl.statuses = TrackImportStatus.query();
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

    app.controller( 'TrackImportStatusAddController', [ 'TrackImportStatus', '$location', function( TrackImportStatus, $location ) {
        var ctrl = this;
        this.status = new TrackImportStatus();

        this.submit = function() {
            ctrl.status.$save( {}, function() {
                $location.path( '/trackImportStatuses/' );
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };
    }]);

    app.controller( 'TrackImportStatusEditController', [ 'TrackImportStatus', '$routeParams', '$location', function( TrackImportStatus, $routeParams, $location ) {
        var ctrl = this;
        this.id = $routeParams.id;
        ctrl.status = TrackImportStatus.get( { id: $routeParams.id } );

        this.submit = function () {
            ctrl.status.$update( {}, function() {
                $location.path( '/trackImportStatuses/' );
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };
    }]);

})();
