(function () {

    var app = angular.module('musicdb', ['ngRoute', 'labels']);

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

})();
