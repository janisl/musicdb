(function() {

    var app = angular.module( 'tracks', [] );

    app.controller( 'TracksController', [ 'Track', function( Track ) {
        var ctrl = this;
        this.filterText = '';
        this.tracks = [];

        this.getList = function() {
            ctrl.tracks = Track.query();
        };

        this.delete = function( track ) {
            if ( confirm( "Are you sure you want to delete this track?" ) === true ) {
                track.$delete( {}, function() {
                    ctrl.getList();
                }, function( httpResponse ) {
                    alert( 'Error ' + httpResponse );
                });
            }
        };

        this.getList();
    }]);

    app.controller( 'TrackAddController', [ 'Track', '$location', function( Track, $location ) {
        var ctrl = this;
        this.track = new Track();

        this.submit = function() {
            ctrl.track.$save( {}, function() {
                $location.path( '/tracks/' );
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };
    }]);

    app.controller( 'TrackEditController', [ 'Track', '$routeParams', '$location', function( Track, $routeParams, $location ) {
        var ctrl = this;
        this.id = $routeParams.id;
        this.track = Track.get( { id: $routeParams.id } );

        this.submit = function() {
            ctrl.track.$update( {}, function() {
                $location.path( '/tracks/' );
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };
    }]);

    app.controller( 'TrackTagsController', [ 'Track', '$routeParams', '$location', function( Track, $routeParams, $location ) {
        var ctrl = this;
        this.tags = Track.tags( { id: $routeParams.id } );

        this.setTags = function() {
            Track.setTags( { id: $routeParams.id }, function() {
                ctrl.tags = Track.tags( { id: $routeParams.id } );
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };
    }]);

})();
