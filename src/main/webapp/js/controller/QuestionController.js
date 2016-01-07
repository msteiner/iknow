/*globals angular */
/*eslint-env browser */
var app = angular.module('iknow_app',[]);
app.controller('question_ctrl', function($scope, $location, $http) {
$scope.parent = '';
$scope.relation = '';
$scope.child = '';
$scope.additions = '';
$scope.questions = [
{id:1, parent:'Baum',  relation:'has',        child:"Ast"},
{id:2, parent:'Baum',  relation:'has',        child:"Rad"},
{id:3, parent:'Baum',  relation:'has many',   child:"Blatt"},
{id:4, parent:'Blatt', relation:'is',         child:"organisch"},
{id:5, parent:'Blatt', relation:'is part of', child:"Rad"},
{id:6, parent:'Wald',  relation:'has many',   child:"Baum"}
];
$scope.edit = true;
$scope.error = false;
$scope.incomplete = false; 
$scope.hideform = true; 
$scope.currentId = '';
$scope.currentParent = '';

$scope.answer = function(id, isTrue) {
	$scope.hideform = true;
	if (isTrue) {
		alert("Thank you for your confirmation!");
	}
	else {
		alert("Ups. Not...? - Thanks anyway...");
	}
	// make a server request
	// TODO remove doesn't works!
	$scope.questions.slice(id-1, 1);
};

$scope.editAnswer = function(id, parentName) {
	$scope.currentId = id;
	$scope.currentParent = parentName;
    $scope.hideform = false;
	$scope.parent = $scope.questions[id-1].parent;
	$scope.child = $scope.questions[id-1].child; 
};

$scope.saveAnswers = function(id) {
	$scope.test();
	if ($scope.incomplete == true) {		
		$scope.hideform = true;
	}
	else {
		
	}
	// make a server request
	if ($scope.incomplete == true) {		
		var url = "http://" + $location.host() + ":" + $location.port() + '/rest/Question/' + $scope.currentId + '/' + "TEST_USER" + '/' + $scope.additions;
	    $http.get(url)
	      .success(function (data) {
	      //$scope.facts = data;
	    })
	    .error(function (data, status, headers, config) {
	      $scope.errorMessage = "Couldn't load the list of statements, error # " + status;
	      alert('errorMessage=' + $scope.errorMessage);
	    });
    }
	// TODO remove doesn't works!
	$scope.questions.slice(id-1, 1);
	$scope.additions = '';
	$scope.incomplete = false;
};

$scope.test = function() {
	$scope.incomplete = false;
	if (!$scope.additions.length == 0) {
		$scope.incomplete = true;
	}
};

});