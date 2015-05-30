(function() {

    var app = angular.module( 'discogsSearch', [] );

    app.controller('DiscogsSearchController', [ 'DiscogsSearch', function( DiscogsSearch ) {
        var ctrl = this;
        this.filterText = '';
        this.pages = [];
        this.params = {};
        this.results = {};
            
        this.search = function( page ) {
            ctrl.params.page = page;
            ctrl.results = DiscogsSearch.query( ctrl.params, function() {
                ctrl.pages = [];
                for (var i = 1; i <= ctrl.results.pagination.pages; i++) {
                    ctrl.pages.push( { pageNumber: i, isCurrent: i === ctrl.results.pagination.page } );
                }
            }, function( httpResponse  ) {
                alert( 'Error ' + httpResponse  );
            });
        };
    }]);

})();
