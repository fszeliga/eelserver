<?php
function get_position()
{
$positionAbfrage = "SELECT * FROM test";
$ergebnisP = mysql_query($positionAbfrage) or die(mysql_error());


while($row = mysql_fetch_object($ergebnisP))
   {
	$xcoordinate ="$row->xcoordinate<br>";
	echo "$row->xcoordinate<br>";
	echo "$row->ycoordinate<br>";
	echo "<img src='images/pos.png'><br>";	
   }
}
?>