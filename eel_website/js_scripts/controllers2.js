app.controller('sensorController',  function($scope, $http, $filter, $log, sensors_service) {
/*load sensor list in scope variable sensor_list from the server...still not parsed for use with agularjs!*/

	$http.get("http://127.0.0.1:8020/eel_website/php_scripts/get_all_sensors.php?callback=JSON_CALLBACK")
  		.success(function(response){$scope.sensor_list = response.records ;});
  
  	$scope.sensorClick = function(s){
  		var sensor = $filter('filter')($scope.sensors, {sensor_id:s.sensor_id})[0];
  		$log.log(sensor);
  	};
  	
  	$scope.sensor_values = {"controllable":[{"sensor_id":"10","name":"T\u00fcr","data_id":"2","continuous":"0","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, controllable by user"},{"sensor_id":"11","name":"Licht","data_id":"3","continuous":"1","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, controllable by user"},{"sensor_id":"12","name":"Fenster","data_id":"2","continuous":"0","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, controllable by user"},{"sensor_id":"13","name":"Tischlampe","data_id":"3","continuous":"1","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, controllable by user"},{"sensor_id":"14","name":"PC","data_id":"2","continuous":"0","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, controllable by user"},{"sensor_id":"15","name":"Ventilator","data_id":"3","continuous":"1","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, controllable by user"},{"sensor_id":"16","name":"Heizung","data_id":"3","continuous":"1","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, controllable by user"},{"sensor_id":"17","name":"Jalousie","data_id":"2","continuous":"0","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, controllable by user"}],"not_controllable":[{"sensor_id":"1","name":"Innentemperatur [C\u00b0]","data_id":"5","continuous":"1","is_float":"1","vlow":"-100","vhigh":"100","symbol":"C","data_comment":"used for temperature in Celsius"},{"sensor_id":"2","name":"Innenluftfeuchtigkeit [%]","data_id":"6","continuous":"1","is_float":"0","vlow":"0","vhigh":"100","symbol":"%","data_comment":"used for humidity in %"},{"sensor_id":"3","name":"Au\u00dfentemperatur [C\u00b0]","data_id":"5","continuous":"1","is_float":"1","vlow":"-100","vhigh":"100","symbol":"C","data_comment":"used for temperature in Celsius"},{"sensor_id":"4","name":"Au\u00dfenluftfeuchtigkeit [%]","data_id":"6","continuous":"1","is_float":"0","vlow":"0","vhigh":"100","symbol":"%","data_comment":"used for humidity in %"},{"sensor_id":"5","name":"Helligkeitssensor","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"},{"sensor_id":"6","name":"Bewegungssensor","data_id":"1","continuous":"0","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, not controllable by user"},{"sensor_id":"7","name":"Stuhl","data_id":"1","continuous":"0","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, not controllable by user"},{"sensor_id":"8","name":"CO2 Sensor","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"},{"sensor_id":"9","name":"Anzahl Personen","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"},{"sensor_id":"18","name":"Rauchmelder","data_id":"1","continuous":"0","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, not controllable by user"},{"sensor_id":"19","name":"PC Monitor [W]","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"},{"sensor_id":"20","name":"Tischlampe [W]","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"},{"sensor_id":"21","name":"Deckenlampe [W]","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"},{"sensor_id":"22","name":"Ventilator [W]","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"},{"sensor_id":"1000","name":"Linker Lichtschalter","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"},{"sensor_id":"1001","name":"Rechter Lichtschalter","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"},{"sensor_id":"5000","name":"day\/night","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"}],"success":1};
  	var websocket;

  	init();
	
	function init(){
		$scope.sensors = sensors_service.getSensors($scope.sensor_values);
		
		connect();
	}
		/*
		 sendStatus = function (sensor) {
		
			var sensor = $filter('filter')($scope.sensors, {id:sensor.sensor_id});
			var control;
	
			if(item.type == "passive"){
				$log.log("changeRootStatus","item is passiv sensor");
			} else {
				control = item.controls[0]
				
			 	if (control.active==0) { value = 100; }
			 	else { value = 0; }
			 	
			 	$scope.changeStatus(item.sensor_id, value);
			 	if (!value) {
			    	value = control.active;
			 	}
			 	
				sendEvent(item.sensor_id,control.active);
				
				$log.log("changeRootStatus","item is active sensor");
				$log.log('send Event',item.sensor_id,control.active);
			}
		} 	
		 
		 */
	
    
    function connect() {    
		var wsUri = "ws://IMI-elab1.imi.kit.edu:7955/websockets/els";
        websocket = new WebSocket(wsUri);
        websocket.binaryType = "arraybuffer";
        websocket.onopen = function(evt) { onOpen(evt); };
        websocket.onerror = function(evt) { onError(evt); };
        websocket.onmessage = function(evt) { onMessage(evt); };

        return websocket;
    }

    function onOpen() {
        $log.log("Opened Connection");
		angular.forEach( $scope.sensors, function( sensor ){
			$log.log("upate req for sensor: ", sensor.sensor_id);
			sendRequest(sensor.sensor_id);
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
        $log.log("onMessage data:",int8View);

        var id_help = [];
        id_help[0] = int8View[1];
        id_help[1] = int8View[2];
        
        var data_help = [];
        data_help[0] = int8View[3];
        data_help[1] = int8View[4];

		var s_id = intFromBytes(int8View[1],int8View[2]);
		var s_data = intFromBytes(int8View[3],int8View[4]);
		
		$log.log("onMessage received update: sensor "+ s_id + " data " + s_data);

		var sensor = $filter('filter')($scope.sensors, {sensor_id:s_id})[0];
		$log.log('OnMessage filtered sensor: ' + sensor.sensor_id);
		
		sensor.value = s_data;
		
		var sensor2 = $filter('filter')($scope.sensors, {sensor_id:s_id})[0];
		$log.log('OnMessage checking sensor: ' + sensor2.sensor_id + ' value ' + sensor2.value);
		
		$log.log('OnMessage set sensor: ' + sensor);

        /*changeRootStatus(id,data);

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
		*/
       
       }

    function changeRootStatus(sensor_id, value) {
		console.log("changeStatus",sensor_id, value);	
        var item = $filter('getById')($scope.room.items, sensor_id);
        var control;
		var curVal = value;
		
		if(item.is_float == 1){
			curVal = curVal/10;
			console.log("item is a float");
		}
       
		if(item.status == "passive"){
			item.value = curVal;
			if(item.symbol == "NA"){
				//$('#passive_value_text_'+sensor_id).text(curVal);
			}else{
				//$('#passive_value_text_'+sensor_id).text(curVal + " " + item.symbol);
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

});