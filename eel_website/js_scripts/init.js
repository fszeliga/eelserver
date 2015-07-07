var app = angular.module('elab', ['ui.bootstrap']);

app.filter('filter_symbols', function() {
	var non_displayed_symbols = ['NA', '0','',' '];
	
	return function(symbol) {
		if(non_displayed_symbols.indexOf(symbol) !== -1){return ''; }
		else {return ' ' + symbol;}
	};
    
  });
  
app.filter('filterSensorByID', function() {
	var non_displayed_symbols = ['NA', '0','',' '];
	
	return function(s_list, sen_id) {
		var result = -1;
		
		angular.forEach( s_list, function(sensor){
			console.log('checking sensor_list ' + sensor.sensor_id + ' | wanted ' + sen_id );
			if(sensor.sensor_id == sen_id){
				console.log('found sensor: ', sensor.sensor_id);
				result = sensor;
			}
		});
		return result;
	};
    
  });
  
  app.factory('jsonService', function($http) {

    var getData = function() {

        return $http({method:"GET", url:"http://imi-elab1.imi.kit.edu/get_all_sensors.php"}).then(function(result){
            return result.data;
        });
    };
    return { getData: getData };
});
