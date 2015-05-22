(function() {

    var app = angular.module( 'beatportArtists', [] );

    app.controller( 'BeatportArtistsController', [ 'BeatportArtist', function( BeatportArtist ) {
        var ctrl = this;
        this.filterText = '';
        this.artists = [];
        this.importUrl = '';

        this.getList = function() {
            ctrl.artists = BeatportArtist.query();
        };

        this.import = function() {
            BeatportArtist.import( { url: ctrl.importUrl }, function() {
                ctrl.importUrl = '';
                ctrl.getList();
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };
        
        this.reimport = function( artist ) {
            artist.$reimport( {}, function() {
                alert( 'Reimported' );
                ctrl.getList();
            }, function( httpResponse  ) {
                alert( 'Error ' + httpResponse  );
            });
        };
        
        this.delete = function( artist ) {
            if ( confirm( "Are you sure you want to delete this artist?" ) === true ) {
                artist.$delete( {}, function() {
                    ctrl.getList();
                }).error( function( httpResponse ) {
                    alert( 'Error ' + httpResponse );
                });
            }
        };
        
        this.getList();
    }]);

})();
