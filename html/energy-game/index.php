<html>
<head>
<title>Energy Game</title>

<?php
//Verbinden mit DB
include 'script/function_db_connect.php';
db_connect();

//Auslesen der X-Koordinate und der Y-Koordinate
$xcoordinate = "";
$ycoordinate = "";

	$positionAbfrage = "SELECT * FROM position";
	$ergebnisP = mysql_query($positionAbfrage) or die(mysql_error());


	while($row = mysql_fetch_object($ergebnisP))
   	{
		//Anpassen des Ursprungs des Spiels an den Screenshot(Mitte des Map) + Auslesen der Koordinaten aus der DB
		$xcoordinate ="$row->xf" + 1510;
		$ycoordinate ="$row->zf" + 1200;
	}
?>

<style type="text/css">
	.crop 	{
		width: 760px;
		height: 540px;
		overflow: hidden;
		border-radius: 15px;
		}

	.crop img 	{
			width: 3158px;
			height: 2600px;
			margin: -1510px 0px 0px -1725px;
			}

	.img-a  {
		top: 270px;
		left: 380px;
		position: absolute;
		width: 25x;
		height: 25px;
		border-radius: 15px  
		}

</style>
</head>
<body>
<body bgcolor="#898989" text="#008d00" link="#FF9966" vlink="#FF9900" alink="#FFFFFF">


<table border="0">
  <tr>
	<th>
	<?php
	//include 'script/function_get_floor.php';
	//get_floor();
	echo "$xcoordinate<br>" ;
	echo $ycoordinate; 
	?>
	<div class="crop">
    	<img src='images/Map.png'><br>
	</div>
	<div class="warp">
	<img class="img-a" src='images/pos.png'>
	</div>
	</th>

	<th>
	<?php
	include 'script/function_get_temp.php';
	get_temp();
	?>
	</th>
  </tr>
</table>

<?php
//Trennen
include 'script/function_db_disconnect.php';
db_disconnect();

?>
</body>
</html>