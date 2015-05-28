(function() {

    var app = angular.module( 'discogsLabels', [] );

    app.controller('DiscogsLabelDetailsController', [ 'DiscogsLabel', '$routeParams', function( DiscogsLabel, $routeParams ) {
        this.label = DiscogsLabel.get( { id: $routeParams.id } );
        
        this.filterText = '';
        //ctrl.releases = DiscogsRelease.byLabel( { labelId: $routeParams.id } );
    }]);

})();
