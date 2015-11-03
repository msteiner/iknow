angular.module('iknow_app', []).controller('person_ctrl', function($scope) {
    $scope.firstName = "John",
    $scope.lastName = "Deer",
    $scope.fullName = function() {
        return $scope.firstName + " " + $scope.lastName;
    }
});