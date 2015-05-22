(function() {

    var app = angular.module( 'labels', [] );

    app.controller( 'LabelsController', [ 'Label', function( Label ) {
        var ctrl = this;
        this.filterText = '';
        this.labels = [];

        this.getList = function() {
            ctrl.labels = Label.query();
        };

        this.delete = function( label ) {
            if ( confirm( "Are you sure you want to delete this label?" ) === true ) {
                label.$delete( {}, function() {
                    ctrl.getList();
                }, function( httpResponse ) {
                    alert( 'Error ' + httpResponse );
                });
            }
        };

        this.getList();
    }]);

    app.controller( 'LabelAddController', [ 'Label', '$location', function( Label, $location ) {
        var ctrl = this;
        this.label = new Label();

        this.submit = function() {
            ctrl.label.$save( {}, function() {
                $location.path( '/labels/' );
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };
    }]);

    app.controller( 'LabelEditController', [ 'Label', '$routeParams', '$location', function( Label, $routeParams, $location ) {
        var ctrl = this;
        this.id = $routeParams.id;
        this.label = Label.get( { id: $routeParams.id } );

        this.submit = function() {
            ctrl.label.$update( {}, function() {
                $location.path( '/labels/' );
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };
    }]);

})();
