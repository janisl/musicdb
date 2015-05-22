(function() {

    var app = angular.module( 'beatportLabels', [] );

    app.controller( 'BeatportLabelsController', [ 'BeatportLabel', function( BeatportLabel ) {
        var ctrl = this;
        this.filterText = '';
        this.labels = [];
        this.importUrl = '';

        this.getList = function() {
            ctrl.labels = BeatportLabel.query();
        };

        this.import = function() {
            BeatportLabel.import( { url: ctrl.importUrl }, function() {
                ctrl.importUrl = '';
                ctrl.getList();
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };
        
        this.reimport = function( label ) {
            label.$reimport( {}, function() {
                alert( 'Reimported' );
                ctrl.getList();
            }, function( httpResponse  ) {
                alert( 'Error ' + httpResponse  );
            });
        };
        
        this.delete = function( label ) {
            if ( confirm( "Are you sure you want to delete this label?" ) === true ) {
                label.$delete( {}, function() {
                    ctrl.getList();
                }).error( function( httpResponse ) {
                    alert( 'Error ' + httpResponse );
                });
            }
        };
        
        this.getList();
    }]);

})();
