(function () {

    var app = angular.module('beatportTracks', []);

    app.controller('BeatportTracksController', ['$http', function ($http) {
        var ctrl = this;
        this.filterText = '';
        this.tracks = [];
        this.importUrl = '';

        this.getList = function() {
            $http.get('/beatport/track/').success(function (data) {
                ctrl.tracks = data;
            });
        };

        this.delete = function(trackId) {
            if (confirm("Are you sure you want to delete this track?") === true) {
                var url = '/beatport/track/' + trackId;
                $http.delete(url).success(function (data) {
                    ctrl.getList();
                }).error(function (data, status, headers, config) {
                    alert('Error ' + status);
                });
            }
        };
        
        this.getList();
    }]);

})();
