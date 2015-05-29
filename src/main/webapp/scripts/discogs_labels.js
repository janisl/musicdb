(function() {

    var app = angular.module( 'discogsLabels', [] );

    app.controller('DiscogsLabelDetailsController', [ 'DiscogsLabel', '$routeParams', function( DiscogsLabel, $routeParams ) {
        var ctrl = this;
        this.filterText = '';
        this.pages = [];
            
        this.label = DiscogsLabel.get( { id: $routeParams.id }, function() {
            ctrl.getReleases( 1 );
        });
        
        this.getReleases = function( page ) {
            ctrl.releases = DiscogsLabel.releases( { id: $routeParams.id, page: page }, function() {
                ctrl.pages = [];
                for (var i = 1; i <= ctrl.releases.pagination.pages; i++) {
                    ctrl.pages.push( { pageNumber: i, isCurrent: i === ctrl.releases.pagination.page } );
                }
            });
        };
    }]);

})();
