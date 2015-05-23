(function() {

    var app = angular.module( 'mixxxCrates', [] );

    app.controller( 'MixxxCratesController', [ 'MixxxCrate', function( MixxxCrate ) {
        this.filterText = '';
        this.crates = MixxxCrate.query();
    }]);

    app.controller( 'MixxxCrateDetailsController', [ 'MixxxCrate', 'MixxxCrateTrack', '$routeParams', function( MixxxCrate, MixxxCrateTrack, $routeParams ) {
        this.id = $routeParams.id;
        this.crate = MixxxCrate.get( { id: $routeParams.id } );
        this.tracks = MixxxCrateTrack.query( { crateId: $routeParams.id } );
    }]);

})();
