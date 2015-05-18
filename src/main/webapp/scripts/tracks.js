(function () {

    var app = angular.module('tracks', []);

    app.controller('TracksController', ['$http', function ($http) {
        var ctrl = this;
        this.filterText = '';
        this.tracks = [];

        this.getList = function() {
            $http.get('/track/').success(function (data) {
                ctrl.tracks = data;
            });
        };
        
        this.delete = function(trackId) {
            if (confirm("Are you sure you want to delete this track?") === true) {
                var url = '/track/' + trackId;
                $http.delete(url).success(function (data) {
                    ctrl.getList();
                }).error(function (data, status, headers, config) {
                    alert('Error ' + status);
                });
            }
        };
        
        this.getList();
    }]);

    app.controller('TrackAddController', ['$http', '$location', function ($http, $location) {
        var ctrl = this;
        this.track = {};

        this.submit = function () {
            $http.post('/track/', ctrl.track).success(function (data) {
                $location.path('/tracks/');
            }).error(function (data, status, headers, config) {
                alert('Error ' + status);
            });
        };
    }]);

    app.controller('TrackEditController', ['$http', '$routeParams', '$location', function ($http, $routeParams, $location) {
        var ctrl = this;
        this.id = $routeParams.id;
        this.track = {};

        $http.get('/track/' + $routeParams.id).success(function (data) {
            ctrl.track = data;
        });

        this.submit = function () {
            $http.put('/track/' + ctrl.id, ctrl.track).success(function (data) {
                $location.path('/tracks/');
            }).error(function (data, status, headers, config) {
                alert('Error ' + status);
            });
        };
    }]);

})();
