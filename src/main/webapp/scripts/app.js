(function () {

    var app = angular.module('musicdb', ['ngRoute']);

    app.config(['$routeProvider', function($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'views/home.html',
                controller: 'HomeController',
                controllerAs: 'home'
            })
            .when('/labels/', {
                templateUrl: 'views/labels/list.html',
                controller: 'LabelsController',
                controllerAs: 'labels'
            })
            .when('/labels/add/', {
                templateUrl: 'views/labels/add_edit.html',
                controller: 'LabelAddController',
                controllerAs: 'labelCtrl'
            })
            .when('/labels/:id/', {
                templateUrl: 'views/labels/add_edit.html',
                controller: 'LabelEditController',
                controllerAs: 'labelCtrl'
            })
            .otherwise({
                redirectTo: '/'
            });
    }]);

    app.controller('HomeController', [function () {
    }]);

    app.controller('LabelsController', ['$http', function ($http) {
        var ctrl = this;
        this.labels = [];

        this.getList = function() {
            $http.get('/label/list').success(function (data) {
                ctrl.labels = data;
            });
        };
        
        this.delete = function(labelId) {
            if (confirm("Are you sure you want to delete this label?") === true) {
                var url = '/label/delete/' + labelId;
                $http.get(url).success(function (data) {
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
            var url = '/label/add?name=' + encodeURIComponent(ctrl.label.name);
            if (typeof ctrl.label.beatportId !== 'undefined' && ctrl.label.beatportId !== null)
                url += '&beatportId=' + encodeURIComponent(ctrl.label.beatportId);
            if (typeof ctrl.label.beatportUrl !== 'undefined' && ctrl.label.beatportUrl !== null)
                url += '&beatportUrl=' + encodeURIComponent(ctrl.label.beatportUrl);
            if (typeof ctrl.label.discogsId !== 'undefined' && ctrl.label.discogsId !== null)
                url += '&discogsId=' + encodeURIComponent(ctrl.label.discogsId);
            $http.get(url).success(function (data) {
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
            var url = '/label/update/' + ctrl.id + '?name=' + encodeURIComponent(ctrl.label.name);
            if (typeof ctrl.label.beatportId !== 'undefined' && ctrl.label.beatportId !== null)
                url += '&beatportId=' + encodeURIComponent(ctrl.label.beatportId);
            if (typeof ctrl.label.beatportUrl !== 'undefined' && ctrl.label.beatportUrl !== null)
                url += '&beatportUrl=' + encodeURIComponent(ctrl.label.beatportUrl);
            if (typeof ctrl.label.discogsId !== 'undefined' && ctrl.label.discogsId !== null)
                url += '&discogsId=' + encodeURIComponent(ctrl.label.discogsId);
            $http.get(url).success(function (data) {
                $location.path('/labels/');
            }).error(function (data, status, headers, config) {
                alert('Error ' + status);
            });
        };
    }]);

})();
