/// <reference path="../Scripts/angular-1.1.4.js" />

/*#######################################################################
  
  Dan Wahlin
  http://twitter.com/DanWahlin
  http://weblogs.asp.net/dwahlin
  http://pluralsight.com/training/Authors/Details/dan-wahlin

  Normally like to break AngularJS apps into the following folder structure
  at a minimum:

  /app
      /controllers      
      /directives
      /services
      /partials
      /views

  #######################################################################*/

var app = angular.module('customersApp',  []).controller("NavbarController", ['$scope', '$routeParams', function($scope, $routeParams) {
    alert($routeParams);
}]);;

//This configures the routes and associates each route with a view and a controller
app.config(function ($routeProvider) {
    $routeProvider
        .when('/',
            {
                controller: 'ListController',
                templateUrl: '/app/partials/list.html'
            })
/*
        .when('/rooms',
            {
                controller: 'RoomList',
                templateUrl: '/app/partials/rooms.html'
            })
        .when('/room/:roomID',
            {
                controller: 'RoomController',
                templateUrl: '/app/partials/room.html'
            })
         .when('/list/:roomID',
            {
                controller: 'ListController',
                templateUrl: '/app/partials/list.html'
            })
         .when('/editRoom/:roomID',
            {
                controller: 'itemController',
                templateUrl: '/app/partials/editItem.html'
            })
         .when('/editItem/:roomID/:itemID',
            {
                controller: 'itemController',
                templateUrl: '/app/partials/editItem.html'
            })
        .when('/customerorders/:customerID',
            {
                controller: 'itemController',
                templateUrl: '/app/partials/customerOrders.html'
            })
        .when('/orders',
            {
                controller: 'OrdersController',
                templateUrl: '/app/partials/orders.html'
            })
*/
        .otherwise({ redirectTo: '/' });
});

app.filter('getById', function() {
          return function(input, id) {
            var i=0, len=input.length;
            for (; i<len; i++) {
              if (+input[i].sensor_id == +id) {
                return input[i];
              }
            }
            return null;
          }
        });



