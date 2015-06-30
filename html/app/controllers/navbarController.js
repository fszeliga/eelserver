app.controller('NavbarController', function ($scope, $routeParams, $location, roomService) {
   /* var path = $location.path();
    if (path.split('/')[1]=='room')
        $scope.roomID = path.split('/')[2];
    if (path.split('/')[1]=='list')
        $scope.roomID = path.split('/')[2 ];
    else 
        $scope.roomID = 5; */   

    console.log("roomService",roomService.getRooms())
    $scope.rooms = roomService.getRooms();

    $scope.getClass = function (path) {
        if ($location.path().substr(0, path.length) == path) {
            return true
        } else {
            return false;
        }
    }


    $scope.roomID = localStorage.roomID;
    console.log("l-",localStorage);
});
