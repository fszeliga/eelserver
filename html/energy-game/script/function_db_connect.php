<?php

function db_connect(){

	$host = 'localhost';
        $user = 'energygame';
        $pass = 'EG2014';
        $db = 'energygame';

// Create connection
$connect = mysql_connect($host, $user, $pass);

// Check connection
if ($connect->connect_error) 
{
    die("Connection failed: " . $connect->connect_error);
}
//echo "Connected successfully";

//Datenbank auswaehlen
        $db_select = mysql_select_db($db, $connect);
        if (!$db_select) 
	{
            die ("<br> Kann Datenbank energygame nicht benutzen : " . mysql_error());
        }
//echo "<br>Datenbank ausgewaehlt<br>";

}
?>

