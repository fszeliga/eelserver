/*#######################################################################
  
  Dan Wahlin
  http://twitter.com/DanWahlin
  http://weblogs.asp.net/dwahlin
  http://pluralsight.com/training/Authors/Details/dan-wahlin

  Normally like the break AngularJS controllers into separate files.
  Kept them together here since they're small and it's easier to look through them.
  example. 

  #######################################################################*/



//This controller retrieves data from the customersService and associates it with the $scope
//The $scope is ultimately bound to the customers view
app.controller('CustomersController', function ($scope, customersService) {

    //I like to have an init() for controllers that need to perform some initialization. Keeps things in
    //one place...not required though especially in the simple example below
    init();

    function init() {
        $scope.customers = customersService.getCustomers();
    }

    $scope.insertCustomer = function () {
        var name = $scope.item.name;
        var type = $scope.item.type;
        var name = $scope.item.name;
        var width = $scope.item.width;
        var height = $scope.item.heigth;
        roomService.insertItem(0, itemId, type, name);
        $scope.newCustomer.firstName = '';
        $scope.newCustomer.lastName = '';
        $scope.newCustomer.city = '';
    };

    $scope.deleteCustomer = function (id) {
        customersService.deleteCustomer(id);
    };
});


app.controller('RoomController', function ($scope, $routeParams, roomService) {

    //I like to have an init() for controllers that need to perform some initialization. Keeps things in
    //one place...not required though especially in the simple example below
    init();


        
    function hasParentClass( e, classname ) {
        if(e === document) return false;
        if( classie.has( e, classname ) ) {
            return true;
        }
        return e.parentNode && hasParentClass( e.parentNode, classname );
    }

    // http://coveroverflow.com/a/11381730/989439
    function mobilecheck() {
        var check = false;
        (function(a){if(/(android|ipad|playbook|silk|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino/i.test(a)||/1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(a.substr(0,4)))check = true})(navigator.userAgent||navigator.vendor||window.opera);
        return check;
    }

   $scope.changeStatus = function (item, value) {
        var item = $filter('getById')($scope.room.items, item.sensor_id);
        var control = item.controls;
        console.log(item);
        if (item.continious)
            control.active = value;
        else {
            if (control.active == 100){
                control.active = 0;
                item.status = 'off';
            }
            else  {
                control.active = 100;
                item.status = 'on';
            }
        }

        console.log("send command ",item.sensor_id,control.active);
    };

    function init() {
        console.log("init");
        $scope.room = roomService.getRoom($routeParams.roomID);
        console.log("rooms: ",$scope.room);
        $scope.defaults = roomService.getDefaults();
        console.log("defaults: ",$scope.defaults);

    
        var container = document.getElementById( 'st-container' ),
            eventtype = mobilecheck() ? 'touchstart' : 'click',
            resetMenu = function() {
                classie.remove( container, 'st-menu-open' );
            },
            bodyClickFn = function(evt) {
                if( !hasParentClass( evt.target, 'st-menu' ) ) {
                    resetMenu();
                    document.removeEventListener( eventtype, bodyClickFn );
                }
            };
            button = $('#menu > button');
            el = $(button);
        console.log(el);

        var effect = el.attr( 'data-effect' );

        el.bind( eventtype, function( ev ) {
            ev.stopPropagation();
            ev.preventDefault();
            container.className = 'st-container'; // clear
            classie.add( container, effect );
            setTimeout( function() {
                classie.add( container, 'st-menu-open' );
            }, 25 );
            $(document).bind( eventtype, bodyClickFn );
        });

        $( "#room").resizable();
        //$( "#menu-9" ).accordion();
        $( "#menu-9 li" ).draggable({
          appendTo: "#room #items",
          helper: "clone",
          start: function( event, ui ) {
            resetMenu();
            console.log("def")
          }
        });

    $( "#items li" ).draggable({
      start: function( event, ui ) {
        console.log("abc");
      }
    });
    console.log("draggable registered",$( "#items li" ).length)
    $( "#room" ).droppable({
            activeClass: "ui-state-default",
            hoverClass: "ui-state-hover",
            accept: ".defaultItem",
            drop: function( event, ui ) {

                console.log("dropped");
                $( this ).find( ".placeholder" ).remove();
                var el = $( "<li></li>" ).text( ui.draggable.text() );
                el.css("left",event.clientX);
                el.css("top",event.clientY);
                el.addClass("ui-state-default ng-scope ng-binding ui-selectee item");
                el.draggable({ 
                    containment: "#room", 
                    scroll: false, 
                    helper: "none" });
                //$('#items').append(el);

                
                var name = ui.draggable.text();
                var type = "custom";
                roomService.insertItem(0, 1, type, name);
                $scope.items.push(roomService.getItem(1))
                console.log(roomService.getItems(0));
                
            }
        })
    }
});

app.controller('itemController', function ($scope, $routeParams, roomService) {

    //I like to have an init() for controllers that need to perform some initialization. Keeps things in
    //one place...not required though especially in the simple example below

    var roomID = parseInt($routeParams.roomID);
    var itemId = parseInt($routeParams.itemID);
    init();   

    $scope.insertItem = function () {
        //console.log("abc");
        var name = $scope.item.name;
        var type = $scope.item.type;
        roomService.insertItem(0, itemId, type, name);
        location.href = "#/list";
    };

    $scope.loadBackgrounds = function() {
        var dir = "Content/backgrounds/";
        var fileextension = ".jpg";
        $scope.backgrounds = [];
        $.ajax({
            //This will retrieve the contents of the folder if the folder is configured as 'browsable'
            url: dir,
            success: function (data) {
                //Lsit all png file names in the page

                $(data).find("a:contains(" + fileextension + ")").each(function () {
                    var filename = this.href.split('/').pop();
                    $scope.backgrounds.push({'name': filename.split('.').shift(),'src':dir + filename});
                });
            }
        });
    }

     $scope.loadIcons = function() {
        $scope.icons = roomService.getIcons();
    }

    function init() {
        for(var key in test) {
            console.log(test[key]);
        }

        console.log($routeParams.roomID, $routeParams.itemID);
        $scope.item = roomService.getService(roomID,itemId);
        console.log(roomService.getItem(roomID,itemId));
        $scope.defaults = roomService.getDefaults();
        $scope.icons = roomService.getIcons();
    }

    $scope.insertItem = function () {
        //console.log("abc");
        var name = $scope.item.name;
        var type = $scope.item.type;
        roomService.insertItem(0, itemId, type, name);
        location.href = "#/list";
    };

    $scope.deleteCustomer = function (id) {
        customersService.deleteCustomer(id);
    };
});

//This controller retrieves data from the customersService and associates it with the $scope
//The $scope is bound to the orders view
app.controller('OrdersController', function ($scope, customersService) {
    $scope.customers = [];

    //I like to have an init() for controllers that need to perform some initialization. Keeps things in
    //one place...not required though especially in the simple example below
    init();

    function init() {
        $scope.customers = customersService.getCustomers();
    }
});


app.controller('RoomList', function ($scope, $routeParams, $location, roomService) {

    //I like to have an init() for controllers that need to perform some initialization. Keeps things in
    //one place...not required though especially in the simple example below
    init();

    function init() {
        console.log("init RoomListController");
        $scope.rooms = roomService.getRooms();
         $scope.roomID
        console.log("RoomsX: ",$scope.rooms);
         localStorage.removeItem('roomID');
    }

     $scope.saveRoom = function(id) {
       console.log("saveRoom ",id);
       $scope.roomID = id;
       localStorage.roomID=id;
        console.log($scope.roomID)
          $scope.roomID = id;
          console.log($scope.roomID)
       $location.path('/room/'+id);
    }


});



//This controller is a child controller that will inherit functionality from a parent
//It's used to track the orderby parameter and ordersTotal for a customer. Put it here rather than duplicating 
//setOrder and orderby across multiple controllers.
app.controller('OrderChildController', function ($scope) {
    $scope.orderby = 'product';
    $scope.reverse = false;
    $scope.ordersTotal = 0.00;

    init();

    function init() {
        //Calculate grand total
        //Handled at this level so we don't duplicate it across parent controllers
        if ($scope.customer && $scope.customer.orders) {
            var total = 0.00;
            for (var i = 0; i < $scope.customer.orders.length; i++) {
                var order = $scope.customer.orders[i];
                total += order.orderTotal;
            }
            $scope.ordersTotal = total;
        }
    }

    $scope.setOrder = function (orderby) {
        if (orderby === $scope.orderby)
        {
            $scope.reverse = !$scope.reverse;
        }
        $scope.orderby = orderby;
    };

});