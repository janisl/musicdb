(function() {

    var app = angular.module( 'beatportGenres', [] );

    app.controller( 'BeatportGenresController', [ 'BeatportGenre', function( BeatportGenre ) {
        var ctrl = this;
        this.filterText = '';
        this.genres = [];
        this.importUrl = '';

        this.getList = function() {
            ctrl.genres = BeatportGenre.query();
        };

        this.delete = function( genre ) {
            if ( confirm( "Are you sure you want to delete this genre?" ) === true ) {
                genre.$delete( {}, function() {
                    ctrl.getList();
                }, function( httpResponse ) {
                    alert( 'Error ' + httpResponse );
                });
            }
        };
        
        this.getList();
    }]);

})();
