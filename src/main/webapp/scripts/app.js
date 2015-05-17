(function () {

    var app = angular.module('musicdb', ['ngRoute', 'labels', 'artists']);

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
            .when('/artists/', {
                templateUrl: 'views/artists/list.html',
                controller: 'ArtistsController',
                controllerAs: 'artists'
            })
            .when('/artists/add/', {
                templateUrl: 'views/artists/add_edit.html',
                controller: 'ArtistAddController',
                controllerAs: 'artistCtrl'
            })
            .when('/artists/:id/', {
                templateUrl: 'views/artists/add_edit.html',
                controller: 'ArtistEditController',
                controllerAs: 'artistCtrl'
            })
            .otherwise({
                redirectTo: '/'
            });
    }]);

    app.controller('HomeController', [function () {
    }]);

})();
