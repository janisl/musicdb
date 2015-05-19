(function () {

    var app = angular.module('beatportArtists', []);

    app.controller('BeatportArtistsController', ['$http', function ($http) {
        var ctrl = this;
        this.filterText = '';
        this.artists = [];
        this.importUrl = '';

        this.getList = function() {
            $http.get('/beatport/artist/').success(function (data) {
                ctrl.artists = data;
            });
        };

        this.import = function () {
            $http.get('/beatport/artist/get?url=' + encodeURIComponent(ctrl.importUrl)).success(function (data) {
                ctrl.importUrl = '';
                ctrl.getList();
            }).error(function (data, status, headers, config) {
                alert('Error ' + status + data);
            });
        };
        
        this.reimport = function(artistId) {
            var url = '/beatport/artist/' + artistId + '/reimport';
            $http.get(url).success(function (data) {
                alert('Reimported');
                ctrl.getList();
            }).error(function (data, status, headers, config) {
                alert('Error ' + status);
            });
        };
        
        this.delete = function(artistId) {
            if (confirm("Are you sure you want to delete this artist?") === true) {
                var url = '/beatport/artist/' + artistId;
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
