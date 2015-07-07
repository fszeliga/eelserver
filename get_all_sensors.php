<?php
 //error_reporting(E_ALL & ~E_NOTICE);
 //ini_set('display_errors', '1');
 
// array for JSON response
$response = array();
 
// include db connect class
//require_once __DIR__ . '/db_connect.php';
// connecting to db
//$db = new DB_CONNECT();

require_once __DIR__ . '/db_config.php';

// Connecting to mysql database
$db = new mysqli(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE);
// Check connection
if ($db->connect_error) {
	die("Connection failed: " . $db->connect_error);
}

//SELECT sensor_code_map.code code,sensor_code_map.id sensor_id, sensor_code_map.name, df.id data_id, df.controllable controllable, df.continuous continuous, df.is_float is_float, df.v_low vlow, df.v_high vhigh, df.symbol symbol, df.comment data_comment
//FROM sensor_code_map
//LEFT JOIN data_format df ON sensor_code_map.data_id = df.id;

//$result = mysql_query("SELECT * FROM striche ORDER BY striche DESC") or die(mysql_error());
$result = $db->query("SELECT sensor_code_map.code code,
sensor_code_map.id sensor_id, 
sensor_code_map.name name,
df.id data_id,
df.controllable controllable, 
df.continuous continuous, 
df.is_float is_float, 
df.v_low vlow, 
df.v_high vhigh, 
df.symbol symbol, 
df.comment data_comment
FROM sensor_code_map
LEFT JOIN data_format df ON sensor_code_map.data_id = df.id;") or die(mysql_error());

// check for empty result
if (mysqli_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["controllable"] = array();
    $response["not_controllable"] = array();
 
    while ($row = $result->fetch_array(MYSQLI_ASSOC)) {
	//controllable
		if($row["controllable"] == "1"){
		
			// temp user array 
			$sensor = array();
			$sensor["sensor_id"] = $row["sensor_id"];
			$sensor["name"] = $row["name"];
			$sensor["name"] = utf8_encode($sensor["name"]);
			$sensor["data_id"] = $row["data_id"];
			$sensor["continuous"] = $row["continuous"];
			$sensor["is_float"] = $row["is_float"];
			$sensor["vlow"] = $row["vlow"];
			$sensor["vhigh"] = $row["vhigh"];
			$sensor["symbol"] = utf8_encode($row["symbol"]);
			$sensor["data_comment"] = $row["data_comment"];		
		
			array_push($response["controllable"], $sensor);
		} else if($row["controllable"] == "0"){ //not controllable
			// temp user array 
			$sensor = array();
			$sensor["sensor_id"] = $row["sensor_id"];
			$sensor["name"] = $row["name"];
			$sensor["name"] = utf8_encode($sensor["name"]);
			$sensor["data_id"] = $row["data_id"];
			$sensor["continuous"] = $row["continuous"];
			$sensor["is_float"] = $row["is_float"];
			$sensor["vlow"] = $row["vlow"];
			$sensor["vhigh"] = $row["vhigh"];
			$sensor["symbol"] = utf8_encode($row["symbol"]);
			$sensor["data_comment"] = $row["data_comment"];		

			array_push($response["not_controllable"], $sensor);
		}
    }
    // success
    $response["success"] = 1;
    header('Content-Type: application/json');
    echo json_encode($response, JSON_PRETTY_PRINT);
    
	switch (json_last_error()) {
	case JSON_ERROR_NONE:
		//echo ' - No errors';
		;
	break;
	case JSON_ERROR_DEPTH:
		echo ' - Maximum stack depth exceeded';
	break;
	case JSON_ERROR_STATE_MISMATCH:
		echo ' - Underflow or the modes mismatch';
	break;
	case JSON_ERROR_CTRL_CHAR:
		echo ' - Unexpected control character found';
	break;
	case JSON_ERROR_SYNTAX:
		echo ' - Syntax error, malformed JSON';
	break;
	case JSON_ERROR_UTF8:
		echo ' - Malformed UTF-8 characters, possibly incorrectly encoded';
	break;
	default:
		; // no errors
	break;
	}


} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No data found";
 
    // echo no users JSON
    echo json_encode($response, JSON_PRETTY_PRINT);
}

/* will look like this

{
	"controllable": [
		{
            "id": "1",
            "name": "iPhone 4S",
            "striche": "300",
        },
	
	
	],
	"not_controllable": [
		{
            "id": "1",
            "name": "iPhone 4S",
            "striche": "300"
        },
	
	],
	"success" : 1
}


ODER


{
{
    "success": 0,
    "message": "No data found"
}
}
*/

?>
