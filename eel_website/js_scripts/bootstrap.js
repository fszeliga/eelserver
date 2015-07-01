var app = angular.module('elab', []);

app.controller('sensorController', function($scope, $http, $log) {
  $http.get("http://127.0.0.1:8020/eel_website/php_scripts/get_all_sensors.php?callback=JSON_CALLBACK")
  	.success(function(response){$scope.sensor_list = response.records ;});
  	
  $scope.sensorClick = function(id){
  	$log.log(id);
  }
  	
  $scope.sensor_values = {"controllable":[{"sensor_id":"10","name":"T\u00fcr","data_id":"2","continuous":"0","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, controllable by user"},{"sensor_id":"11","name":"Licht","data_id":"3","continuous":"1","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, controllable by user"},{"sensor_id":"12","name":"Fenster","data_id":"2","continuous":"0","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, controllable by user"},{"sensor_id":"13","name":"Tischlampe","data_id":"3","continuous":"1","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, controllable by user"},{"sensor_id":"14","name":"PC","data_id":"2","continuous":"0","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, controllable by user"},{"sensor_id":"15","name":"Ventilator","data_id":"3","continuous":"1","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, controllable by user"},{"sensor_id":"16","name":"Heizung","data_id":"3","continuous":"1","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, controllable by user"},{"sensor_id":"17","name":"Jalousie","data_id":"2","continuous":"0","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, controllable by user"}],"not_controllable":[{"sensor_id":"1","name":"Innentemperatur [C\u00b0]","data_id":"5","continuous":"1","is_float":"1","vlow":"-100","vhigh":"100","symbol":"C","data_comment":"used for temperature in Celsius"},{"sensor_id":"2","name":"Innenluftfeuchtigkeit [%]","data_id":"6","continuous":"1","is_float":"0","vlow":"0","vhigh":"100","symbol":"%","data_comment":"used for humidity in %"},{"sensor_id":"3","name":"Au\u00dfentemperatur [C\u00b0]","data_id":"5","continuous":"1","is_float":"1","vlow":"-100","vhigh":"100","symbol":"C","data_comment":"used for temperature in Celsius"},{"sensor_id":"4","name":"Au\u00dfenluftfeuchtigkeit [%]","data_id":"6","continuous":"1","is_float":"0","vlow":"0","vhigh":"100","symbol":"%","data_comment":"used for humidity in %"},{"sensor_id":"5","name":"Helligkeitssensor","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"},{"sensor_id":"6","name":"Bewegungssensor","data_id":"1","continuous":"0","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, not controllable by user"},{"sensor_id":"7","name":"Stuhl","data_id":"1","continuous":"0","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, not controllable by user"},{"sensor_id":"8","name":"CO2 Sensor","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"},{"sensor_id":"9","name":"Anzahl Personen","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"},{"sensor_id":"18","name":"Rauchmelder","data_id":"1","continuous":"0","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, not controllable by user"},{"sensor_id":"19","name":"PC Monitor [W]","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"},{"sensor_id":"20","name":"Tischlampe [W]","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"},{"sensor_id":"21","name":"Deckenlampe [W]","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"},{"sensor_id":"22","name":"Ventilator [W]","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"},{"sensor_id":"1000","name":"Linker Lichtschalter","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"},{"sensor_id":"1001","name":"Rechter Lichtschalter","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"},{"sensor_id":"5000","name":"day\/night","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"}],"success":1}

});