var app = angular.module('iknow_app',[])
app.controller('relation_ctrl', function($scope, $location, $http) {
  
  $scope.getFacts = function() {
    $http.get($location.absUrl() + 'rest/Statement/Baum')
      .success(function (data) {
      $scope.facts = data;
    })
    .error(function (data, status, headers, config) {
      $scope.errorMessage = "Couldn't load the list of relations, error # " + status;
      alert('errorMessage=' + $scope.errorMessage);
    });
  }
      
  $scope.items = [
      {id: 'IS', value: 'is'}, 
      {id: 'IS_NOT', value: 'is not' }, 
      {id: 'HAS', value: 'has' }, 
      {id: 'HAS_MANY', value: 'has more than one' }, 
      {id: 'IS_PART_OF', value: 'is part of' }];
  });