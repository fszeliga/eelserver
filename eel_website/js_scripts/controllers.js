/**
 * @author Filip
 */
app.controller('sensorController', ["$scope", "$log", "$http", "$filter", "sensors_service", "jsonService",
function($scope, $log, $http, $filter, sensors_service, jsonService) {
	delete $http.defaults.headers.common['X-Requested-With'];
	var promiseResponse = $http.get("http://imi-elab1.imi.kit.edu/filip/php_scripts/get_all_sensors.php");

	var websocket;

	init();

	function init() {

		promiseResponse.success(function(data, status, headers, config) {
			var sensors = data;
			$scope.sensor_list = sensors_service.getSensors(sensors);
			connect();
			if (!("ontouchstart" in document.documentElement)) {
				document.documentElement.className += " no-touch";
			}
		}).error(function(data, status, headers, config) {
			console.log("didnt work with that json" + status);
		});

		// check if got json obeject (success tag), then check if sensor list created
		var touch = window.ontouchstart || navigator.MaxTouchPoints > 0 || navigator.msMaxTouchPoints > 0;

	}


	$scope.sensorClick = function(sen) {
		if (sen.active == 'standby') {
			alert('Have not received initial value from the server!\nPlease wait and reload site.');
			return;
		}

		if (sen.active == 'on') {
			sendEvent(sen.sensor_id, 0);
		} else if (sen.active == 'off') {
			sendEvent(sen.sensor_id, 100);
		}
		sendRequest(sen.sensor_id);

	};

	function connect() {
		var wsUri = "ws://IMI-elab1.imi.kit.edu:7955/websockets/els";
		websocket = new WebSocket(wsUri);
		websocket.binaryType = "arraybuffer";
		websocket.onopen = function(evt) {
			onOpen(evt);
		};
		websocket.onerror = function(evt) {
			onError(evt);
		};
		websocket.onmessage = function(evt) {
			onMessage(evt);
		};
		$log.log('opened connection');
		return websocket;
	}

	function onOpen() {
		$log.log("Opened Connection");
		angular.forEach($scope.sensor_list, function(sensor) {
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

		var s_id = intFromBytes(int8View[1], int8View[2]);
		var s_data = intFromBytes(int8View[3], int8View[4]);

		var sensor = $filter('filterSensorByID')($scope.sensor_list, s_id);

		if (sensor == -1) {
			console.log('wrong sensor');
			return;
		}

		if (sensor.isFloat) {
			s_data = s_data / 10;
			console.log("item is a float");
		}

		sensor.value = s_data;
		// set the sensor value in scope

		if (sensor.type == 'passive') {
			sensor.active = 'off';
		} else if (sensor.type == 'active') {
			if (sensor.value > 0)
				sensor.active = 'on';
			else if (sensor.value == 0)
				sensor.active = 'off';
		}

		$scope.$digest();
		//used to update the view
	}

	function sendRequest(sensor) {
		var bytes = new Uint8Array(8);
		bytes[0] = 0x00;
		bytes[1] = sensor >> 8;
		bytes[2] = sensor;
		bytes[3] = 0x00;
		bytes[4] = 0x00;
		bytes[5] = 0x00;
		bytes[6] = 0x05;
		bytes[7] = 0x00;
		sendBinary(bytes);
	}

	function sendEvent(sensor, value) {
		var bytes = new Uint8Array(8);
		bytes[0] = 0x45;
		bytes[1] = sensor >> 8;
		bytes[2] = sensor;
		bytes[3] = value >> 8;
		bytes[4] = value;
		bytes[5] = 0x00;
		bytes[6] = 0x01;
		bytes[7] = 0x00;

		sendBinary(bytes);
	}

	function sendBinary(bytes) {
		websocket.send(bytes);
	}

	function intFromBytes(x, y) {
		var val = x << 8 | y;
		return val;
	}

	function hoverTouchUnstick() {
		// Check if the device supports touch events
		if ('ontouchstart' in document.documentElement) {
			// Loop through each stylesheet
			for (var sheetI = document.styleSheets.length - 1; sheetI >= 0; sheetI--) {
				var sheet = document.styleSheets[sheetI];
				// Verify if cssRules exists in sheet
				if (sheet.cssRules) {
					// Loop through each rule in sheet
					for (var ruleI = sheet.cssRules.length - 1; ruleI >= 0; ruleI--) {
						var rule = sheet.cssRules[ruleI];
						// Verify rule has selector text
						if (rule.selectorText) {
							// Replace hover psuedo-class with active psuedo-class
							rule.selectorText = rule.selectorText.replace(":hover", ":active");
						}
					}
				}
			}
		}
	}

	function removeHoverCSSRule() {
		if ('createTouch' in document) {
			try {
				var ignore = /:hover/;
				for (var i = 0; i < document.styleSheets.length; i++) {
					var sheet = document.styleSheets[i];
					if (!sheet.cssRules) {
						continue;
					}
					for (var j = sheet.cssRules.length - 1; j >= 0; j--) {
						var rule = sheet.cssRules[j];
						if (rule.type === CSSRule.STYLE_RULE && ignore.test(rule.selectorText)) {
							sheet.deleteRule(j);
						}
					}
				}
			} catch(e) {
			}
		}
	}

}]);
