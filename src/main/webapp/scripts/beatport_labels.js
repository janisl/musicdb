(function () {

    var app = angular.module('beatportLabels', []);

    app.controller('BeatportLabelsController', ['$http', function ($http) {
        var ctrl = this;
        this.filterText = '';
        this.labels = [];
        this.importUrl = '';

        this.getList = function() {
            $http.get('/beatport/label/').success(function (data) {
                ctrl.labels = data;
            });
        };

        this.import = function () {
            $http.get('/beatport/label/get?url=' + encodeURIComponent(ctrl.importUrl)).success(function (data) {
                ctrl.importUrl = '';
                ctrl.getList();
            }).error(function (data, status, headers, config) {
                alert('Error ' + status + data);
            });
        };
        
        this.reimport = function(labelId) {
            var url = '/beatport/label/' + labelId + '/reimport';
            $http.get(url).success(function (data) {
                alert('Reimported');
                ctrl.getList();
            }).error(function (data, status, headers, config) {
                alert('Error ' + status);
            });
        };
        
        this.delete = function(labelId) {
            if (confirm("Are you sure you want to delete this label?") === true) {
                var url = '/beatport/label/' + labelId;
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
