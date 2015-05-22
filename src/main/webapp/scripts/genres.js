(function () {

    var app = angular.module( 'genres', [] );

    app.controller( 'GenresController', [ 'Genre', function( Genre ) {
        var ctrl = this;
        this.filterText = '';
        this.genres = [];

        this.getList = function() {
            ctrl.genres = Genre.query();
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

    app.controller( 'GenreAddController', [ 'Genre', '$location', function( Genre, $location ) {
        var ctrl = this;
        this.genre = new Genre();

        this.submit = function() {
            ctrl.genre.$save( {}, function() {
                $location.path( '/genres/' );
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };
    }]);

    app.controller( 'GenreEditController', [ 'Genre', '$routeParams', '$location', function( Genre, $routeParams, $location ) {
        var ctrl = this;
        this.id = $routeParams.id;
        ctrl.genre = Genre.get( { id: $routeParams.id } );

        this.submit = function () {
            ctrl.genre.$update( {}, function() {
                $location.path( '/genres/' );
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };
    }]);

})();
