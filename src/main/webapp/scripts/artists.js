(function () {

    var app = angular.module('artists', []);

    app.controller('ArtistsController', ['$http', function ($http) {
        var ctrl = this;
        this.filterText = '';
        this.artists = [];

        this.getList = function() {
            $http.get('/artist/').success(function (data) {
                ctrl.artists = data;
            });
        };
        
        this.delete = function(artistId) {
            if (confirm("Are you sure you want to delete this artist?") === true) {
                var url = '/artist/' + artistId;
                $http.delete(url).success(function (data) {
                    ctrl.getList();
                }).error(function (data, status, headers, config) {
                    alert('Error ' + status);
                });
            }
        };
        
        this.getList();
    }]);

    app.controller('ArtistAddController', ['$http', '$location', function ($http, $location) {
        var ctrl = this;
        this.artist = {};

        this.submit = function () {
            $http.post('/artist/', ctrl.artist).success(function (data) {
                $location.path('/artists/');
            }).error(function (data, status, headers, config) {
                alert('Error ' + status);
            });
        };
    }]);

    app.controller('ArtistEditController', ['$http', '$routeParams', '$location', function ($http, $routeParams, $location) {
        var ctrl = this;
        this.id = $routeParams.id;
        this.artist = {};

        $http.get('/artist/' + $routeParams.id).success(function (data) {
            ctrl.artist = data;
        });

        this.submit = function () {
            $http.put('/artist/' + ctrl.id, ctrl.artist).success(function (data) {
                $location.path('/artists/');
            }).error(function (data, status, headers, config) {
                alert('Error ' + status);
            });
        };
    }]);

})();
