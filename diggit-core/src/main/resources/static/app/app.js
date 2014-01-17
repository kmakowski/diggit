var app = angular.module('diggit', []);

app.controller('DiggitController', function($scope) {
  var sequencer = 100;

  $scope.links = [];
  $scope.newLink = '';


  $scope.addLink = function() {
    $scope.links.push({id: generateId(), path: $scope.newLink, votes: 0});
    $scope.newLink = '';
  };

  $scope.vote = function(id, value) {
    angular.forEach($scope.links, function(link){
      if (link.id === id) {
        link.votes = link.votes + value;
      }
    });
  }

  function generateId() {
    return sequencer++;
  }
});

