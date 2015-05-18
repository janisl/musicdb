(function () {

    var app = angular.module('genres', []);

    app.controller('GenresController', ['$http', function ($http) {
        var ctrl = this;
        this.filterText = '';
        this.genres = [];

        this.getList = function() {
            $http.get('/genre/').success(function (data) {
                ctrl.genres = data;
            });
        };
        
        this.delete = function(genreId) {
            if (confirm("Are you sure you want to delete this genre?") === true) {
                var url = '/genre/' + genreId;
                $http.delete(url).success(function (data) {
                    ctrl.getList();
                }).error(function (data, status, headers, config) {
                    alert('Error ' + status);
                });
            }
        };
        
        this.getList();
    }]);

    app.controller('GenreAddController', ['$http', '$location', function ($http, $location) {
        var ctrl = this;
        this.genre = {};

        this.submit = function () {
            $http.post('/genre/', ctrl.genre).success(function (data) {
                $location.path('/genres/');
            }).error(function (data, status, headers, config) {
                alert('Error ' + status);
            });
        };
    }]);

    app.controller('GenreEditController', ['$http', '$routeParams', '$location', function ($http, $routeParams, $location) {
        var ctrl = this;
        this.id = $routeParams.id;
        this.genre = {};

        $http.get('/genre/' + $routeParams.id).success(function (data) {
            ctrl.genre = data;
        });

        this.submit = function () {
            $http.put('/genre/' + ctrl.id, ctrl.genre).success(function (data) {
                $location.path('/genres/');
            }).error(function (data, status, headers, config) {
                alert('Error ' + status);
            });
        };
    }]);

})();
