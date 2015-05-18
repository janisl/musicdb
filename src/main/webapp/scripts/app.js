(function () {

    var app = angular.module('musicdb', ['ngRoute', 'artists', 'genres', 'labels', 'releases']);

    app.config(['$routeProvider', function($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'views/home.html',
                controller: 'HomeController',
                controllerAs: 'home'
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
            .when('/genres/', {
                templateUrl: 'views/genres/list.html',
                controller: 'GenresController',
                controllerAs: 'genres'
            })
            .when('/genres/add/', {
                templateUrl: 'views/genres/add_edit.html',
                controller: 'GenreAddController',
                controllerAs: 'genreCtrl'
            })
            .when('/genres/:id/', {
                templateUrl: 'views/genres/add_edit.html',
                controller: 'GenreEditController',
                controllerAs: 'genreCtrl'
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
            .when('/releases/', {
                templateUrl: 'views/releases/list.html',
                controller: 'ReleasesController',
                controllerAs: 'releases'
            })
            .when('/releases/add/', {
                templateUrl: 'views/releases/add_edit.html',
                controller: 'ReleaseAddController',
                controllerAs: 'releaseCtrl'
            })
            .when('/releases/:id/', {
                templateUrl: 'views/releases/add_edit.html',
                controller: 'ReleaseEditController',
                controllerAs: 'releaseCtrl'
            })
            .otherwise({
                redirectTo: '/'
            });
    }]);

    app.controller('HomeController', [function () {
    }]);

})();
