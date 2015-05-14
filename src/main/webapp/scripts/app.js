(function(){

var app = angular.module('musicdb', []);

app.controller('LabelsController', ['$http', function($http) {
  var ctrl = this;
  this.labels = [];

   $http.get('/label/list').success(function(data){
     ctrl.labels = data;
   });  
}]);

})();
