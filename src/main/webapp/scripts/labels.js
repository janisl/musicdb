(function () {

    var app = angular.module('labels', []);

    app.controller('LabelsController', ['$http', function ($http) {
        var ctrl = this;
        this.filterText = '';
        this.labels = [];

        this.getList = function() {
            $http.get('/label/').success(function (data) {
                ctrl.labels = data;
            });
        };
        
        this.delete = function(labelId) {
            if (confirm("Are you sure you want to delete this label?") === true) {
                var url = '/label/' + labelId;
                $http.delete(url).success(function (data) {
                    ctrl.getList();
                }).error(function (data, status, headers, config) {
                    alert('Error ' + status);
                });
            }
        };
        
        this.getList();
    }]);

    app.controller('LabelAddController', ['$http', '$location', function ($http, $location) {
        var ctrl = this;
        this.label = {};

        this.submit = function () {
            $http.post('/label/', ctrl.label).success(function (data) {
                $location.path('/labels/');
            }).error(function (data, status, headers, config) {
                alert('Error ' + status);
            });
        };
    }]);

    app.controller('LabelEditController', ['$http', '$routeParams', '$location', function ($http, $routeParams, $location) {
        var ctrl = this;
        this.id = $routeParams.id;
        this.label = {};

        $http.get('/label/' + $routeParams.id).success(function (data) {
            ctrl.label = data;
        });

        this.submit = function () {
            $http.put('/label/' + ctrl.id, ctrl.label).success(function (data) {
                $location.path('/labels/');
            }).error(function (data, status, headers, config) {
                alert('Error ' + status);
            });
        };
    }]);

})();
