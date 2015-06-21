(function () {

    var app = angular.module( 'downloads', [] );

    app.controller( 'DownloadsController', [ 'Download', function( Download ) {
        this.filterText = '';
        this.downloads = Download.query();
    }]);

})();
