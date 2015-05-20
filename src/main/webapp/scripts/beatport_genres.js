(function () {

    var app = angular.module('beatportGenres', []);

    app.controller('BeatportGenresController', ['$http', function ($http) {
        var ctrl = this;
        this.filterText = '';
        this.genres = [];
        this.importUrl = '';

        this.getList = function() {
            $http.get('/beatport/genre/').success(function (data) {
                ctrl.genres = data;
            });
        };

        this.delete = function(genreId) {
            if (confirm("Are you sure you want to delete this genre?") === true) {
                var url = '/beatport/genre/' + genreId;
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
