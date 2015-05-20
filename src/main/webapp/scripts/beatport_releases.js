(function () {

    var app = angular.module('beatportReleases', []);

    app.controller('BeatportReleasesController', ['$http', function ($http) {
        var ctrl = this;
        this.filterText = '';
        this.releases = [];
        this.importUrl = '';

        this.getList = function() {
            $http.get('/beatport/release/').success(function (data) {
                ctrl.releases = data;
            });
        };

        this.import = function () {
            $http.get('/beatport/release/get?url=' + encodeURIComponent(ctrl.importUrl)).success(function (data) {
                ctrl.importUrl = '';
                ctrl.getList();
            }).error(function (data, status, headers, config) {
                alert('Error ' + status + data);
            });
        };
        
        this.reimport = function(releaseId) {
            var url = '/beatport/release/' + releaseId + '/reimport';
            $http.get(url).success(function (data) {
                alert('Reimported');
                ctrl.getList();
            }).error(function (data, status, headers, config) {
                alert('Error ' + status);
            });
        };
        
        this.delete = function(releaseId) {
            if (confirm("Are you sure you want to delete this release?") === true) {
                var url = '/beatport/release/' + releaseId;
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
