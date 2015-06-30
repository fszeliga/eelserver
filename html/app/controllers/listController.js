
app.controller('ListController', function ($scope, $routeParams, $filter, roomService) {

    //I like to have an init() for controllers that need to perform some initialization. Keeps things in
    //one place...not required though especially in the simple example below
    init();


    var websocket;
    function connect() {    
	var wsUri = "ws://IMI-elab1.imi.kit.edu:7955/websockets/els"
        websocket = new WebSocket(wsUri);
        websocket.binaryType = "arraybuffer";
        websocket.onopen = function(evt) { onOpen(evt) };
        websocket.onerror = function(evt) { onError(evt) };
        websocket.onmessage = function(evt) { onMessage(evt) };

        return websocket;
    }

    function onOpen() {
        console.log("Opened Connection");
	$.each( $scope.sensors, function( index, value ){
		console.log("upate req for sensor: ",value.sensor_id);
		sendRequest(value.sensor_id);
    	});

    }

    function onError(evt) {
        alert("The Connection could not be established");
        websocket.close();
    }

    function sendText(json) {
        console.log("sending text: " + json);
        websocket.send(json);
    }

    function sendBinary(bytes) {
        console.log("sending binary: " + Object.prototype.toString.call(bytes));
        websocket.send(bytes);
    }          

    function onMessage(evt) {
        var int8View = new Uint8Array(evt.data);
        var data_help = [];
        console.log("onMessage with id:",int8View);
        data_help[0] = int8View[3];
        data_help[1] = int8View[4];

        var id_help = [];
        id_help[0] = int8View[1];
        id_help[1] = int8View[2];

        console.log("onMessage with id:",int8View);
	var id = intFromBytes(int8View[1],int8View[2])

	var data = intFromBytes(int8View[3],int8View[4])

        changeRootStatus(id,data);

        $('#value_'+id).text(data);
        if (!data) {
            $('#icon_'+id).removeClass('on');
            $('#icon_'+id).addClass('off');
            console.log($('#icon_'+id));
        }
       else {
            $('#icon_'+id).removeClass('off');
            $('#icon_'+id).addClass('on');
        }
    }

    function changeRootStatus(sensor_id, value) {
	console.log("changeStatus",sensor_id, value);	
        var item = $filter('getById')($scope.room.items, sensor_id);
        var control;
	var curVal = value;
	if(item.is_float == 1){
		curVal = curVal/10;
		console.log("item is a float")	
	}
       
	if(item.status == "passive"){
		item.value = curVal;
		if(item.symbol == "NA"){
			$('#passive_value_text_'+sensor_id).text(curVal);
		}else{
			$('#passive_value_text_'+sensor_id).text(curVal + " " + item.symbol);
		}
		console.log("changeRootStatus","item is passiv sensor");
	} else {
		control = item.controls[0];
		control.active = curVal;
		if (value == 100){
			item.status = 'on';
			console.log("switched on");
		}
		else  {
			item.status = 'off';
		}
		console.log("changeRootStatus","item is active sensor");

	}

    }

    $scope.changeStatus = function (sensor_id, value) {
        changeRootStatus(sensor_id, value);
    };

    function toggleCheckbox(e){
        //writeToScreen(document.getElementById(e).checked);
      
        if(document.getElementById(e).checked){ //if true the turn it off
            sendEvent(e, 0x00);
        } else {
            sendEvent(e, 0x64);
        }
        
    }


    function sendRequest(sensor) {
        var bytes = new Uint8Array(8);
        bytes[0]=0x00;
        bytes[1]=sensor >> 8;
        bytes[2]=sensor;
        bytes[3]=0x00;
        bytes[4]=0x00;
        bytes[5]=0x00;
        bytes[6]=0x05;
        bytes[7]=0x00;

        sendBinary(bytes);
    }

    function sendEvent(sensor, value) {
        var bytes = new Uint8Array(8);
        bytes[0]=0x45;
        bytes[1]=0x00;
        bytes[2]=sensor;
        bytes[3]=0x00;
        bytes[4]=value;
        bytes[5]=0x00;
        bytes[6]=0x01;
        bytes[7]=0x00;

        //writeToScreen(String.fromCharCode.apply(null, bytes));

        sendBinary(bytes);
    }

    function intFromBytes( x , y){
        ///var bytes = new Uint8Array(8);
        //bytes[0]=x[0];
        //bytes[1]=x[1];
        //var val = 0;
        //for (var i = 0; i < x.length; ++i) {        
        //    val += x[i];        
        //    if (i < x.length-1) {
        //        val = val << 8;
        //    }
        //}
	var val = x << 8 | y;
		console.log("val = ",val);
        return val;
    }

    function setGlobalVars($scope, $routeParams){
        $scope.roomID = 1;
    }

    function init() {
        $scope.room = roomService.getRoom(1);//roomService.getRoom($routeParams.roomID);
        $scope.sensors = roomService.getSensors();
	//set sliders

        setGlobalVars($scope);
        connect();
	  $('#defaultslide').slider({ 
	    max: 1000,
	    min: 0,
	    value: 500,
	    slide: function(e,ui) {
	      $('#currentval').html(ui.value);
	    }
	  });
        console.log("initialized room");
    }

    $scope.sendStatus = function (item, value) {
        
         var item = $filter('getById')($scope.room.items, item.sensor_id);
         var control;

	if(item.status == "passive"){
		console.log("changeRootStatus","item is passiv sensor");
	} else {
		control = item.controls[0]
		 if (control.active==0) {
		    value = 100;
		 } else {
		    value = 0;
		 }
		 $scope.changeStatus(item.sensor_id, value);
		 if (!value) {
		    value = control.active;
		 }
       		sendEvent(item.sensor_id,control.active);
		console.log("changeRootStatus","item is active sensor");
        	console.log('send Event',item.sensor_id,control.active);
	}

    };

    $scope.slideValue = function (sensor_id, value) {
        sendEvent(sensor_id,value);
        console.log('send Event',sensor_id,value);
    };
});

