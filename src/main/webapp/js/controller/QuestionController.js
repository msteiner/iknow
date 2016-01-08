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
$scope.complete = false; 
$scope.showImproveRange = false; 
$scope.currentId = '';
$scope.currentParent = '';

$scope.editAnswer = function(id, parentName) {
	$scope.currentId = id;
	$scope.currentParent = parentName;
    $scope.showImproveRange = true;
	$scope.parent = $scope.questions[id-1].parent;
	$scope.child = $scope.questions[id-1].child; 
};

$scope.saveAnswers = function(id) {
	$scope.test();
	if ($scope.complete === true) {		
		$scope.showImproveRange = false;
		// make a server request
		var url = "http://" + $location.host() + ":" + $location.port() + '/rest/Question/improve/' + $scope.currentId + '/' + "TEST_USER" + '/' + $scope.additions;
	    var success = "Ahso. Das wusste ich nicht. Danke dir.";
	    var error = "Hm. Ein Fehler. Ich kann mir dein Wissen nicht merken...";
	    $scope.doRequest(url, success, error);
		$scope.additions = '';
		$scope.questions.slice(id-1, 1);
	}
};

$scope.createApprovalRequest = function(id) {
	var url = "http://" + $location.host() + ":" + $location.port() + '/rest/Question/approve/' + id + '/' + "TEST_USER";
	var success = "Das hab ich mir gedacht. Danke für deine Bestätigung.";
	var error = "Hm. Ein Fehler. Ich kann das nicht approven...";
	$scope.doRequest(url, success, error);
};

$scope.createDisapprovalRequest = function(id) {
	var url = "http://" + $location.host() + ":" + $location.port() + '/rest/Question/disapprove/' + id + '/' + "TEST_USER";
	var success = "Waaas? - Nicht...? Danke dennoch für deine Bestätigung.";
	var error = "Hm. Ein Fehler. Ich kann das nicht disapproven...";
	$scope.doRequest(url, success, error);
};

$scope.doRequest = function(url, success, error) {
	$http.get(url)
    .success(function (data) {
    	$scope.successMessage = success;
    })
    .error(function (data, status, headers, config) {
      $scope.errorMessage = error + ", error # " + status;
    });
};

$scope.test = function() {
	$scope.complete = true;
	if ($scope.additions.length === 0) {
		$scope.complete = false;
	}
};

});