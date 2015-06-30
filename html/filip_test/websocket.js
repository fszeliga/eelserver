//var wsUri = "ws://" + document.location.host + document.location.pathname + "els";
var wsUri = "ws://IMI-elab1.imi.kit.edu:8025/websockets/els";
var websocket = new WebSocket(wsUri);
websocket.binaryType = "arraybuffer";
websocket.onopen = function(evt) { onOpen(evt) };
websocket.onerror = function(evt) { onError(evt) };
websocket.onmessage = function(evt) { onMessage(evt) };


function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
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
    var int8View = new Int8Array(evt.data);
    //writeToScreen(int8View[2].toString(16));
    console.log("received: " + evt.data);
    var data = [];
    data[0] = int8View[3];
    data[1] = int8View[4];

    var id = [];
    id[0] = int8View[1];
    id[1] = int8View[2];
    
    if(intFromBytes(data)==100){
        document.getElementById(intFromBytes(id)).checked = true;
        writeToScreen("on");
    } else{
        document.getElementById(intFromBytes(id)).checked = false;
        writeToScreen("off");
    }
}

function toggleCheckbox(e){
    //writeToScreen(document.getElementById(e).checked);
  
    if(document.getElementById(e).checked){ //if true the turn it off
        sendEvent(e, 0x00);
    } else {
        sendEvent(e, 0x64);
    }
    
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

function onOpen() {
    writeToScreen("Connected to " + wsUri);
    //do work with php and then add all sensors to table
}

var output = document.getElementById("output");

function writeToScreen(message) {
    output.innerHTML = message;
}

function intFromBytes( x ){
    var val = 0;
    for (var i = 0; i < x.length; ++i) {        
        val += x[i];        
        if (i < x.length-1) {
            val = val << 8;
        }
    }
    return val;
}
