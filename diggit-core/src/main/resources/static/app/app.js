var app = angular.module('diggit', []);

app.controller('DiggitController', function($scope, $http) {
  var sequencer = 100;
  $scope.links = [];

  $http.get('rest/links').then(function(response) {
    $scope.links = response.data;

  });
  $scope.newLink = '';


  $scope.addLink = function() {
    $scope.links.push({_id: generateId(), path: $scope.newLink, votes: 0});
    $scope.newLink = '';
  };

  $scope.vote = function(id, value) {
    angular.forEach($scope.links, function(link){
      if (link._id === id) {
        link.votes = link.votes + value;
      }
    });
  }

  function generateId() {
    return sequencer++;
  }
});

