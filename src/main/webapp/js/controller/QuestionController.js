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
$scope.successMessage;
$scope.errorMessage;
$scope.complete = false; 
$scope.showImproveRange = false; 
$scope.currentId = '';
$scope.currentParent = '';

$scope.answer = function(id, isTrue) {
	var url;
	$scope.showImproveRange = false;
	if (isTrue) {
		url = "http://" + $location.host() + ":" + $location.port() + '/rest/Question/approve/' + id + '/' + "TEST_USER";
		$scope.createApprovalRequest(url);	    
		alert("Thank you for your confirmation!");
	}
	else {
		url = "http://" + $location.host() + ":" + $location.port() + '/rest/Question/disapprove/' + id + '/' + "TEST_USER";
		$scope.createApprovalRequest(url);
		alert("Ups. Not...? - Thanks anyway...");
	}
	// TODO remove doesn't works!
	$scope.questions.slice(id-1, 1);
};

$scope.editAnswer = function(id, parentName) {
	$scope.currentId = id;
	$scope.currentParent = parentName;
    $scope.showImproveRange = true;
	$scope.parent = $scope.questions[id-1].parent;
	$scope.child = $scope.questions[id-1].child; 
};

$scope.saveAnswers = function(id) {
	$scope.test();
	if ($scope.complete == true) {		
		$scope.showImproveRange = false;
		// make a server request
		var url = "http://" + $location.host() + ":" + $location.port() + '/rest/Question/improve/' + $scope.currentId + '/' + "TEST_USER" + '/' + $scope.additions;
	    $http.get(url)
	      .success(function (data) {
	      //$scope.facts = data;
	    })
	    .error(function (data, status, headers, config) {
	      $scope.errorMessage = "Couldn't load the list of statements, error # " + status;
	      alert('errorMessage=' + $scope.errorMessage);
	    });
		$scope.additions = '';
	}
	$scope.questions.slice(id-1, 1);
};

$scope.createApprovalRequest = function(url) {
	$http.get(url)
    .success(function (data) {
      $scope.successMessage = data;
    })
    .error(function (data, status, headers, config) {
      $scope.errorMessage = "Couldn't load the list of statements, error # " + status;
      alert('errorMessage=' + $scope.errorMessage);
    });
};

$scope.test = function() {
	alert($scope.additions.length);
	$scope.complete = true;
	if ($scope.additions.length == 0) {
		$scope.complete = false;
	}
};

});