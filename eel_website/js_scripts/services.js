app.service('sensors_service',function( $log){

    this.getSensors = function(s_list){
        var sensors = [];

        var defaultSensor = {
            type: 'passive',//passive or active, means not controllable (temp) or controllable  (door)
            //type: 'unknown',//continous or discrete, but already in the JSON
	    	value: '0',
			active: 'standby',
			received_init: 0
        };
        
		angular.forEach(s_list.not_controllable, function(sen) {
		    $log.log(sen.name);
		    console.log(sen.icon);
		    var sensor = angular.merge(sen, defaultSensor);
            sensors.push(sensor);
		});
		
		angular.forEach(s_list.controllable, function(sen) {
		    $log.log(sen.name);
		    var sensor = angular.merge(sen, defaultSensor);
			sensor.type = 'active';
            sensors.push(sensor);
		});

        return sensors;
   };

});



    
