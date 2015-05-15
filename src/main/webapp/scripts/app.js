(function () {

    var app = angular.module('musicdb', []);

    app.controller('LabelsController', ['$http', function ($http) {
        var ctrl = this;
        this.labels = [];

        $http.get('/label/list').success(function (data) {
            ctrl.labels = data;
        });
    }]);

    app.controller('LabelAddController', ['$http', function ($http) {
        var ctrl = this;
        this.label = {};

        this.add = function () {
            var url = '/label/add?name=' + encodeURIComponent(ctrl.label.name);
            if (typeof ctrl.label.beatportId !== 'undefined' && ctrl.label.beatportId !== null)
                url += '&beatportId=' + encodeURIComponent(ctrl.label.beatportId);
            if (typeof ctrl.label.beatportUrl !== 'undefined' && ctrl.label.beatportUrl !== null)
                url += '&beatportUrl=' + encodeURIComponent(ctrl.label.beatportUrl);
            if (typeof ctrl.label.discogsId !== 'undefined' && ctrl.label.discogsId !== null)
                url += '&discogsId=' + encodeURIComponent(ctrl.label.discogsId);
            $http.get(url).success(function (data) {
                alert('Added');
            }).error(function (data, status, headers, config) {
                alert('Error ' + status);
            });
        };
    }]);

})();
