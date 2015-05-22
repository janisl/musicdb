(function() {

    var app = angular.module( 'artists', [] );

    app.controller( 'ArtistsController', [ 'Artist', function( Artist ) {
        var ctrl = this;
        this.filterText = '';
        this.artists = [];

        this.getList = function() {
            ctrl.artists = Artist.query();
        };
        
        this.delete = function( artist ) {
            if ( confirm( "Are you sure you want to delete this artist?" ) === true ) {
                artist.$delete( {}, function() {
                    ctrl.getList();
                }, function( httpResponse ) {
                    alert( 'Error ' + httpResponse );
                });
            }
        };
        
        this.getList();
    }]);

    app.controller('ArtistAddController', ['Artist', '$location', function (Artist, $location) {
        var ctrl = this;
        this.artist = new Artist();

        this.submit = function () {
            ctrl.artist.$save( {}, function() {
                $location.path( '/artists/' );
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };
    }]);

    app.controller('ArtistEditController', ['Artist', '$routeParams', '$location', function (Artist, $routeParams, $location) {
        var ctrl = this;
        this.id = $routeParams.id;
        this.artist = Artist.get( { id: $routeParams.id } );

        this.submit = function () {
            ctrl.artist.$update( {} , function() {
                $location.path( '/artists/' );
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };
    }]);

})();
