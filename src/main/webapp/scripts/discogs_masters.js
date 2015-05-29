(function() {

    var app = angular.module( 'discogsMasters', [] );

    app.controller('DiscogsMasterDetailsController', [ 'DiscogsMaster', '$routeParams', function( DiscogsMaster, $routeParams ) {
        var ctrl = this;
        this.filterText = '';
        this.pages = [];
            
        this.master = DiscogsMaster.get( { id: $routeParams.id }, function() {
            ctrl.getVersions( 1 );
        });
        
        this.getVersions = function( page ) {
            ctrl.versions = DiscogsMaster.versions( { id: $routeParams.id, page: page }, function() {
                ctrl.pages = [];
                for (var i = 1; i <= ctrl.versions.pagination.pages; i++) {
                    ctrl.pages.push( { pageNumber: i, isCurrent: i === ctrl.versions.pagination.page } );
                }
            });
        };
    }]);

})();
