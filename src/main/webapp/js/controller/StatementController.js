var app = angular.module('iknow_app',[]);
app.controller('statement_ctrl', function($scope, $location, $http) {
  
  /**
   * Get statements via a method.
   */
  $scope.getFacts = function() {
    var url = $location.absUrl() + 'rest/Statement/' + $scope.neuron1 + '/' + $scope.selectedItem + '/' + $scope.neuron2;
    alert('url=' + url);
    $http.get(url)
      .success(function (data) {
      $scope.facts = data;
    })
    .error(function (data, status, headers, config) {
      $scope.errorMessage = "Couldn't load the list of statements, error # " + status;
      alert('errorMessage=' + $scope.errorMessage);
    })
  }
  
  /**
   * Initialize relations.
   */
  $scope.relations = $http.get($location.absUrl() + 'rest/relations')
    .success(function (data) {
      $scope.relations = data;
    })
    .error(function (data, status, headers, config) {
      $scope.errorMessage = "Couldn't load the list of relations, error # " + status;
    });

});