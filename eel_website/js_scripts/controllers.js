/**
 * @author Filip
 */
app.controller('sensorController', function ($scope, $log, $http, $filter, sensors_service) {
	$http.get("http://127.0.0.1:8020/eel_website/php_scripts/get_all_sensors.php?callback=JSON_CALLBACK")
  		 .success(function(response){$scope.sensor_list2 = response.records ;});
	
	var websocket;
	
	var sensor_values = {
    "controllable": [
        {
            "sensor_id": "10",
            "name": "T\u00fcr",
            "data_id": "2",
            "continuous": "0",
            "is_float": "0",
            "vlow": "0",
            "vhigh": "100",
            "symbol": "NA",
            "icon": "vr-door",
            "data_comment": "boolean values for simply on\/of, controllable by user"
        },
        {
            "sensor_id": "11",
            "name": "Licht",
            "data_id": "3",
            "continuous": "1",
            "is_float": "0",
            "vlow": "0",
            "vhigh": "100",
            "symbol": "NA",
            "icon": "vr-lightbulb",
            "data_comment": "boolean values for simply on\/of, controllable by user"
        },
        {
            "sensor_id": "12",
            "name": "Fenster",
            "data_id": "2",
            "continuous": "0",
            "is_float": "0",
            "vlow": "0",
            "vhigh": "100",
            "symbol": "NA",
            "icon": "vr-window",
            "data_comment": "boolean values for simply on\/of, controllable by user"
        },
        {
            "sensor_id": "13",
            "name": "Tischlampe",
            "data_id": "3",
            "continuous": "1",
            "is_float": "0",
            "vlow": "0",
            "vhigh": "100",
            "symbol": "NA",
            "icon": "vr-lightbulb",
            "data_comment": "boolean values for simply on\/of, controllable by user"
        },
        {
            "sensor_id": "14",
            "name": "PC",
            "data_id": "2",
            "continuous": "0",
            "is_float": "0",
            "vlow": "0",
            "vhigh": "100",
            "symbol": "NA",
            "icon": "vr-pc",
            "data_comment": "boolean values for simply on\/of, controllable by user"
        },
        {
            "sensor_id": "15",
            "name": "Ventilator",
            "data_id": "3",
            "continuous": "1",
            "is_float": "0",
            "vlow": "0",
            "vhigh": "100",
            "symbol": "NA",
            "icon": "vr-fan",
            "data_comment": "boolean values for simply on\/of, controllable by user"
        },
        {
            "sensor_id": "16",
            "name": "Heizung",
            "data_id": "3",
            "continuous": "1",
            "is_float": "0",
            "vlow": "0",
            "vhigh": "100",
            "symbol": "NA",
            "icon": "vr-heat",
            "data_comment": "boolean values for simply on\/of, controllable by user"
        },
        {
            "sensor_id": "17",
            "name": "Jalousie",
            "data_id": "2",
            "continuous": "0",
            "is_float": "0",
            "vlow": "0",
            "vhigh": "100",
            "symbol": "NA",
            "icon": "vr-align-justify",
            "data_comment": "boolean values for simply on\/of, controllable by user"
        }
    ],
    "not_controllable": [
        {
            "sensor_id": "1",
            "name": "Innentemperatur",
            "data_id": "5",
            "continuous": "1",
            "is_float": "1",
            "vlow": "-100",
            "vhigh": "100",
            "symbol": "\u00b0C",
            "icon": "fa-temp",
            "data_comment": "used for temperature in Celsius"
        },
        {
            "sensor_id": "2",
            "name": "Innenluftfeuchtigkeit",
            "data_id": "6",
            "continuous": "1",
            "is_float": "0",
            "vlow": "0",
            "vhigh": "100",
            "symbol": "%",
            "icon": "vr-hum",
            "data_comment": "used for humidity in %"
        },
        {
            "sensor_id": "3",
            "name": "Au\u00dfentemperatur",
            "data_id": "5",
            "continuous": "1",
            "is_float": "1",
            "vlow": "-100",
            "vhigh": "100",
            "symbol": "\u00b0C",
            "icon": "fa-temp",
            "data_comment": "used for temperature in Celsius"
        },
        {
            "sensor_id": "4",
            "name": "Au\u00dfenluftfeuchtigkeit",
            "data_id": "6",
            "continuous": "1",
            "is_float": "0",
            "vlow": "0",
            "vhigh": "100",
            "symbol": "%",
            "icon": "vr-hum",
            "data_comment": "used for humidity in %"
        },
        {
            "sensor_id": "5",
            "name": "Helligkeitssensor",
            "data_id": "6",
            "continuous": "1",
            "is_float": "0",
            "vlow": "0",
            "vhigh": "100",
            "symbol": "%",
            "icon": "0",
            "data_comment": "used for humidity in %"
        },
        {
            "sensor_id": "6",
            "name": "Bewegungssensor",
            "data_id": "1",
            "continuous": "0",
            "is_float": "0",
            "vlow": "0",
            "vhigh": "100",
            "symbol": "NA",
            "icon": "0",
            "data_comment": "boolean values for simply on\/of, not controllable by user"
        },
        {
            "sensor_id": "7",
            "name": "Stuhl",
            "data_id": "1",
            "continuous": "0",
            "is_float": "0",
            "vlow": "0",
            "vhigh": "100",
            "symbol": "NA",
            "icon": "vr-chair",
            "data_comment": "boolean values for simply on\/of, not controllable by user"
        },
        {
            "sensor_id": "8",
            "name": "CO2 Sensor",
            "data_id": "100",
            "continuous": "0",
            "is_float": "0",
            "vlow": "0",
            "vhigh": "0",
            "symbol": "0",
            "icon": "0",
            "data_comment": "dummy used for unknown"
        },
        {
            "sensor_id": "9",
            "name": "Anzahl Personen",
            "data_id": "100",
            "continuous": "0",
            "is_float": "0",
            "vlow": "0",
            "vhigh": "0",
            "symbol": "0",
            "icon": "0",
            "data_comment": "dummy used for unknown"
        },
        {
            "sensor_id": "18",
            "name": "Rauchmelder",
            "data_id": "1",
            "continuous": "0",
            "is_float": "0",
            "vlow": "0",
            "vhigh": "100",
            "symbol": "NA",
            "icon": "vr-smoke",
            "data_comment": "boolean values for simply on\/of, not controllable by user"
        },
        {
            "sensor_id": "19",
            "name": "PC Monitor",
            "data_id": "8",
            "continuous": "1",
            "is_float": "1",
            "vlow": "0",
            "vhigh": "100",
            "symbol": "W",
            "icon": "vr-pc",
            "data_comment": "Energieverbrauch f\u00fcr Funksteckdosen"
        },
        {
            "sensor_id": "20",
            "name": "Tischlampe",
            "data_id": "8",
            "continuous": "1",
            "is_float": "1",
            "vlow": "0",
            "vhigh": "100",
            "symbol": "W",
            "icon": "vr-lightbulb",
            "data_comment": "Energieverbrauch f\u00fcr Funksteckdosen"
        },
        {
            "sensor_id": "21",
            "name": "Deckenlampe",
            "data_id": "8",
            "continuous": "1",
            "is_float": "1",
            "vlow": "0",
            "vhigh": "100",
            "symbol": "W",
            "icon": "vr-lightbulb",
            "data_comment": "Energieverbrauch f\u00fcr Funksteckdosen"
        },
        {
            "sensor_id": "22",
            "name": "Ventilator",
            "data_id": "8",
            "continuous": "1",
            "is_float": "1",
            "vlow": "0",
            "vhigh": "100",
            "symbol": "W",
            "icon": "vr-fan",
            "data_comment": "Energieverbrauch f\u00fcr Funksteckdosen"
        },
        {
            "sensor_id": "1000",
            "name": "Linker Lichtschalter",
            "data_id": "100",
            "continuous": "0",
            "is_float": "0",
            "vlow": "0",
            "vhigh": "0",
            "symbol": "0",
            "icon": "vr-switch",
            "data_comment": "dummy used for unknown"
        },
        {
            "sensor_id": "1001",
            "name": "Rechter Lichtschalter",
            "data_id": "100",
            "continuous": "0",
            "is_float": "0",
            "vlow": "0",
            "vhigh": "0",
            "symbol": "0",
            "icon": "vr-switch",
            "data_comment": "dummy used for unknown"
        },
        {
            "sensor_id": "5000",
            "name": "day\/night",
            "data_id": "100",
            "continuous": "0",
            "is_float": "0",
            "vlow": "0",
            "vhigh": "0",
            "symbol": "0",
            "icon": "vr-day-night",
            "data_comment": "dummy used for unknown"
        }
    ],
    "success": 1
};

	init();
	
	function init(){
			$scope.sensor_list = sensors_service.getSensors(sensor_values);
			// check if got json obeject (success tag), then check if sensor list created
			connect();
	}
	
	$scope.sensorClick = function(sen){
		if(sen.active == 'standby'){
			alert('Have not received initial value from the server!\nPlease wait and reload site.');
			return;
		}
		
		if(sen.active == 'on'){
			sendEvent(sen.sensor_id, 0);
		} else if(sen.active =='off'){
			sendEvent(sen.sensor_id, 100);
		}
		sendRequest(sen.sensor_id);

	};
  
	function connect() {    
		var wsUri = "ws://IMI-elab1.imi.kit.edu:7955/websockets/els";
		websocket = new WebSocket(wsUri);
		websocket.binaryType = "arraybuffer";
		websocket.onopen = function(evt) { onOpen(evt); };
		websocket.onerror = function(evt) { onError(evt); };
		websocket.onmessage = function(evt) { onMessage(evt); };
		$log.log('opened connection');
		return websocket;
	}
  
	function onOpen() {
		$log.log("Opened Connection");
		angular.forEach( $scope.sensor_list, function( sensor ){
			$log.log("upate req for sensor: ", sensor.sensor_id);
			sendRequest(sensor.sensor_id);
		});
	}
	
	function onError(evt) {
		alert("The Connection could not be established");
		websocket.close();
	}
	  
	function onMessage(evt) {
		var int8View = new Uint8Array(evt.data);
		
		var id_help = [];
		id_help[0] = int8View[1];
		id_help[1] = int8View[2];
		
		var data_help = [];
		data_help[0] = int8View[3];
		data_help[1] = int8View[4];
		
		var s_id = intFromBytes(int8View[1],int8View[2]);
		var s_data = intFromBytes(int8View[3],int8View[4]);
						
		var sensor = $filter('filterSensorByID')($scope.sensor_list, s_id);
		
		if(sensor == -1){
			console.log('wrong sensor');
			return;
		}
		
		if(sensor.isFloat){
			s_data = s_data / 10;
			console.log("item is a float");
		}
		
		sensor.value = s_data; // set the sensor value in scope
		
		if(sensor.type == 'passive'){
			sensor.active = 'off';
		} else if(sensor.type == 'active'){
			if(sensor.value > 0) sensor.active = 'on';
			else if(sensor.value == 0) sensor.active = 'off';
		}
				
		$scope.$digest();//used to update the view	       
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
        bytes[1]=sensor >> 8;
        bytes[2]=sensor;
        bytes[3]=value >> 8;
        bytes[4]=value;
        bytes[5]=0x00;
        bytes[6]=0x01;
        bytes[7]=0x00;

        sendBinary(bytes);
    }
	
    function sendBinary(bytes) {
        websocket.send(bytes);
    }
        
    function intFromBytes( x , y){
		var val = x << 8 | y;
        return val;
    }

});