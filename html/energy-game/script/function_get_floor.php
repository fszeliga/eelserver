<?php

function get_floor()
{
$floorAbfrage = "SELECT * FROM test";
$ergebnisF = mysql_query($floorAbfrage) or die(mysql_error());

while($row = mysql_fetch_object($ergebnisF))
   {
	$floor = "$row->floor";
	if ($floor < '1') 
		{ 
		echo "Erdgeschoss<br>";
		echo "<img src='images/test.png'><br>";
		}
	if ($floor > '1') 
		{ 
		echo "1 Stock<br>";
		}
   }
}

?>
