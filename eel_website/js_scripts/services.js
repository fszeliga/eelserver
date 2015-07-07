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
        console.log("getting non control sensors");
		angular.forEach(s_list.not_controllable, function(sen) {
		    $log.log(sen.name);
		    //var sensor = angular.extend(sen, defaultSensor);
		    var sensor = angular.merge(sen, defaultSensor);
            sensors.push(sensor);
		});
		
		angular.forEach(s_list.controllable, function(sen) {
		    $log.log(sen.name);
		    //var sensor = angular.extend(sen, defaultSensor);
		    var sensor = angular.merge(sen, defaultSensor);
			sensor.type = 'active';
            sensors.push(sensor);
		});
/*
		angular.forEach(sensors,function(sen){
        //$(sensors).each(function(i){
        	var name = sen.name;
			if (name.indexOf("Ventilator") != -1){
			   sen.icon = 'icon-fan';
			}else if (name.indexOf("feuchtigkeit") != -1){
			   sen.icon = 'icon-hum';
			}else if (name.indexOf("Stuhl") != -1){
			   sen.icon = 'icon-chair';
			}else if (name.indexOf("temp") != -1){
			   sen.icon = 'icon-temp';
			}else if (name.indexOf("T\u00fcr") != -1){
			   sen.icon = 'icon-door';
			}else if (name.indexOf("Jalousie") != -1){
			   sen.icon = 'fa-align-justify';
			}else if (name.indexOf("lampe") != -1 || name.indexOf("Licht") != -1){
			   sen.icon = 'fa-lightbulb-o';
			}else if (name.indexOf("Time") != -1 || name.indexOf("Zeit") != -1){
			   sen.icon = 'fa-clock-o';
			}else if (name.indexOf("Heizung") != -1){
			   sen.icon = 'fa-fire';
			}else if (name.indexOf("Fenster") != -1){
			   sen.icon = 'fa-picture-o';
			}else if (name.indexOf("Monitor") != -1 || name.indexOf("PC") != -1){
			   sen.icon = 'fa-laptop';
			}else if (name.indexOf("night") != -1){
			   sen.icon = 'fa-moon-o';
			}else if (name.indexOf("sensor") != -1){
			   sen.icon = 'fa-bullseye';
			}
        });
*/
        return sensors;
   };

});



    
