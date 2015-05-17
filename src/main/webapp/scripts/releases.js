(function () {

    var app = angular.module('releases', []);

    app.controller('ReleasesController', ['$http', function ($http) {
        var ctrl = this;
        this.filterText = '';
        this.releases = [];

        this.getList = function() {
            $http.get('/release/').success(function (data) {
                ctrl.releases = data;
            });
        };
        
        this.delete = function(releaseId) {
            if (confirm("Are you sure you want to delete this release?") === true) {
                var url = '/release/' + releaseId;
                $http.delete(url).success(function (data) {
                    ctrl.getList();
                }).error(function (data, status, headers, config) {
                    alert('Error ' + status);
                });
            }
        };
        
        this.getList();
    }]);

    app.controller('ReleaseAddController', ['$http', '$location', function ($http, $location) {
        var ctrl = this;
        this.release = {};

        this.submit = function () {
            $http.post('/release/', ctrl.release).success(function (data) {
                $location.path('/releases/');
            }).error(function (data, status, headers, config) {
                alert('Error ' + status);
            });
        };
    }]);

    app.controller('ReleaseEditController', ['$http', '$routeParams', '$location', function ($http, $routeParams, $location) {
        var ctrl = this;
        this.id = $routeParams.id;
        this.release = {};

        $http.get('/release/' + $routeParams.id).success(function (data) {
            ctrl.release = data;
        });

        this.submit = function () {
            $http.put('/release/' + ctrl.id, ctrl.release).success(function (data) {
                $location.path('/releases/');
            }).error(function (data, status, headers, config) {
                alert('Error ' + status);
            });
        };
    }]);

})();
