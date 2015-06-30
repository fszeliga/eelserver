<?php
function get_temp()
{
$ausTAbfrage = "SELECT * FROM scenarios";
$ergebnisTa = mysql_query($ausTAbfrage) or die(mysql_error());

while($row = mysql_fetch_object($ergebnisTa))
   {
	
	echo "<img style='border-radius: 15px; width: 60px; height: 60px;' src='images/temp.png'><br>";
	echo "<br>Temperatur au&szlig;en $row->world_temperature<br>";
   }
}
?>
