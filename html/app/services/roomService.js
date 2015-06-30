//This handles retrieving data and is used by controllers. 3 options (server, factory, provider) with 
//each doing the same thing just structuring the functions/data differently.
app.service('roomService', function () {
    this.getRooms = function () {
        return rooms;
    };

    this.getIcons = function () {
        return icons;
    };

    this.getRoom = function (id) {
        var room = rooms.filter(function( room ) {
          return room.id == id;
        })[0];

        if (id == 1) 
            room.items = this.getSensors();

        return room;
    };

    this.getIcons = function () {
       return icons;
    };

    this.getItem = function (roomId, itemId) {
        var item = this.getItems(roomId).filter(function( item ) {
             return item.itemId == itemId;
        })[0];

        return item;
    };

    this.getItems = function (roomId) {
        if (roomId == 1)
            return this.getSensors();
        else 
            return this.getRoom(roomId).items;
    };

    this.insertItem = function (roomId, itemId, type, name, posX, posY) {   
        var room = this.getRoom(roomId);
        var topID = room.items.length +1;

        var defaultItem = defaultItems.filter(function( defaultItem ) {
          return defaultItem.name == type;
        })[0];

        room.items.push({
            ItemId: topID,
            type: type,
            posx: posX,
            posy: posY,
            width: defaultItem.width,
            height: defaultItem.height,
            icon: defaultItem.icon
        });


    };

    this.getSensors = function(){
        var sensors = [];

        var defaultSensor = {
            icon: 'fa-square-o',
            width: 50,
            height: 50,
            status: 'passive',
	    value: '0',
        };

        var defaultSensorControllable = jQuery.extend(true, {}, defaultSensor);
        defaultSensorControllable.controls = [{ 
                name: 'boolSensor',
                sensor: 0x00,
                active: 0,
                values: [ 
                    {value: 0000, icon: '', name:'on'}, // first one is default
                    {value: 1111, icon: '', name:'off'}
                ]
            }]
        defaultSensorControllable.status =  'boolean';

        var defaultSensorControllableCont = jQuery.extend(true, {}, defaultSensor);
         defaultSensorControllableCont.controls = [{ 
                name: 'contSensor',
                sensor: 0x00,
                active: 0,
                values: [ 
                    {value: 0000, icon: '', name:'on'}, // first one is default
                    {value: 1111, icon: '', name:'off'}
                ]
            }]

        defaultSensorControllableCont.status =  'cont';

        $(sensor_values.controllable).each(function(){
            var sensor_value = this;
            if (sensor_value.continuous == 0)
                var sensor = $.extend(sensor_value, jQuery.extend(true, {}, defaultSensorControllable));
            else
                var sensor = $.extend(sensor_value,  jQuery.extend(true, {}, defaultSensorControllableCont));
            sensors.push(sensor);
        })

        $(sensor_values.not_controllable).each(function(){
            var sensor_value = this;
            var sensor = $.extend(sensor_value, defaultSensor);
            sensors.push(sensor);
         })

	var fromLeft = 0;

        $(sensors).each(function(i){
            //console.log(this);
            if (this.name) {
                var name = this.name;
                    if (name.indexOf("Ventilator") != -1){
                        sensors[i].icon = 'icon-fan';
                    }
                    if (name.indexOf("feuchtigkeit") != -1){
                        sensors[i].icon = 'icon-hum';
                    }
                    if (name.indexOf("Stuhl") != -1){
                        sensors[i].icon = 'icon-chair';
                    }
                    if (name.indexOf("temp") != -1){
                        sensors[i].icon = 'icon-temp';
                    }
                    if (name.indexOf("T\u00fcr") != -1){
                        sensors[i].icon = 'icon-door';
                    }

                     if (name.indexOf("Jalousie") != -1){
                        sensors[i].icon = 'fa-align-justify'
                    }
                    if (name.indexOf("lampe") != -1 || name.indexOf("Licht") != -1){
                        sensors[i].icon = 'fa-lightbulb-o';
                    }         
                    if (name.indexOf("Time") != -1 || name.indexOf("Zeit") != -1){
                        sensors[i].icon = 'fa-clock-o'
                    }
                    if (name.indexOf("Heizung") != -1){
                        sensors[i].icon = 'fa-fire'
                    }
                    if (name.indexOf("Fenster") != -1){
                        sensors[i].icon = 'fa-picture-o'
                    }
                    if (name.indexOf("Monitor") != -1 || name.indexOf("PC") != -1){
                        sensors[i].icon = 'fa-laptop'
                    }
                    if (name.indexOf("night") != -1){
                        sensors[i].icon = 'fa-moon-o'
                    }
                    if (name.indexOf("sensor") != -1){
                        sensors[i].icon = 'fa-bullseye'
                    }
           
               if (name.indexOf("Fenster") != -1){
                    sensors[i].posx= 0;
                    sensors[i].posy= 170;

                } else if (name.indexOf("Stuhl") != -1){
                    sensors[i].posx= 350;
                    sensors[i].posy= 200;
                    sensors[i].width= 100;
                    sensors[i].height= 100;

                } else if (name.indexOf("PC Monitor") != -1){
                    sensors[i].posx= 300;
                    sensors[i].posy= 70;
                    sensors[i].width= 200;
                    sensors[i].height= 100;

                }else if (name.indexOf("Heizung") != -1){
                    sensors[i].posx= 0;
                    sensors[i].posy= 270;

                } else if (name.indexOf("Jalousie") != -1){
                    sensors[i].posx= 050;
                    sensors[i].posy= 170;

                } else if (name.indexOf("Ventilator") != -1){
                    sensors[i].posx= 600;
                    sensors[i].posy= 70;

                } else {
                    fromLeft = fromLeft+55;
                    sensors[i].posx= fromLeft;
                }
            }
        });

        return sensors;
    }

    this.createItem = function (name, lastName, city) {
        var topID = customers.length + 1;
        customers.push({
            id: topID,
            firstName: firstName,
            lastName: lastName,
            city: city
        });
    };

    this.deleteCustomer = function (id) {
        for (var i = customers.length - 1; i >= 0; i--) {
            if (customers[i].id === id) {
                customers.splice(i, 1);
                break;
            }
        }
    };

    this.getDefaultByTyp = function (val) {
       
    }

    this.getCustomer = function (id) {
        for (var i = 0; i < customers.length; i++) {
            if (customers[i].id === id) {
                return customers[i];
            }
        }
        return null;
    };

    this.getDefaults = function () {
        return defaultItems;
    };

     var rooms = [
        {
            id: 1, name: 'VR Energy Lab', 
            items: []
        }
    ]

    var sensor_values = {"controllable":[{"sensor_id":"10","name":"T\u00fcr","data_id":"2","continuous":"0","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, controllable by user"},{"sensor_id":"11","name":"Licht","data_id":"3","continuous":"1","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, controllable by user"},{"sensor_id":"12","name":"Fenster","data_id":"2","continuous":"0","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, controllable by user"},{"sensor_id":"13","name":"Tischlampe","data_id":"3","continuous":"1","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, controllable by user"},{"sensor_id":"14","name":"PC","data_id":"2","continuous":"0","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, controllable by user"},{"sensor_id":"15","name":"Ventilator","data_id":"3","continuous":"1","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, controllable by user"},{"sensor_id":"16","name":"Heizung","data_id":"3","continuous":"1","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, controllable by user"},{"sensor_id":"17","name":"Jalousie","data_id":"2","continuous":"0","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, controllable by user"}],"not_controllable":[{"sensor_id":"1","name":"Innentemperatur [C\u00b0]","data_id":"5","continuous":"1","is_float":"1","vlow":"-100","vhigh":"100","symbol":"C","data_comment":"used for temperature in Celsius"},{"sensor_id":"2","name":"Innenluftfeuchtigkeit [%]","data_id":"6","continuous":"1","is_float":"0","vlow":"0","vhigh":"100","symbol":"%","data_comment":"used for humidity in %"},{"sensor_id":"3","name":"Au\u00dfentemperatur [C\u00b0]","data_id":"5","continuous":"1","is_float":"1","vlow":"-100","vhigh":"100","symbol":"C","data_comment":"used for temperature in Celsius"},{"sensor_id":"4","name":"Au\u00dfenluftfeuchtigkeit [%]","data_id":"6","continuous":"1","is_float":"0","vlow":"0","vhigh":"100","symbol":"%","data_comment":"used for humidity in %"},{"sensor_id":"5","name":"Helligkeitssensor","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"},{"sensor_id":"6","name":"Bewegungssensor","data_id":"1","continuous":"0","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, not controllable by user"},{"sensor_id":"7","name":"Stuhl","data_id":"1","continuous":"0","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, not controllable by user"},{"sensor_id":"8","name":"CO2 Sensor","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"},{"sensor_id":"9","name":"Anzahl Personen","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"},{"sensor_id":"18","name":"Rauchmelder","data_id":"1","continuous":"0","is_float":"0","vlow":"0","vhigh":"100","symbol":"NA","data_comment":"boolean values for simply on\/of, not controllable by user"},{"sensor_id":"19","name":"PC Monitor [W]","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"},{"sensor_id":"20","name":"Tischlampe [W]","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"},{"sensor_id":"21","name":"Deckenlampe [W]","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"},{"sensor_id":"22","name":"Ventilator [W]","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"},{"sensor_id":"1000","name":"Linker Lichtschalter","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"},{"sensor_id":"1001","name":"Rechter Lichtschalter","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"},{"sensor_id":"5000","name":"day\/night","data_id":"100","continuous":"0","is_float":"0","vlow":"0","vhigh":"0","symbol":"0","data_comment":"dummy used for unknown"}],"success":1}

});




app.directive('mynav', function(){
    return {
        restrict: 'E',
        templateUrl: 'app/partials/nav.html',
        controller: 'NavbarController'
    };
});

app.factory('menuService', ['$rootScope', function ($rootScope) {
    var service = {
        view: 'app/partials/nav.html',

        MenuItemClicked: function (data) {
            $rootScope.$broadcast('menuitemclicked', data);
        }
    };
    return service;
}]);