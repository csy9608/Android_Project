<?php

  $db_server = "localhost";
  $db_id = "id";
  $db_password = "pwd";
  $db_name = "name";

	$db = mysqli_connect($db_server, $db_id , $db_password, $db_name);
	$msg = "";

  if(isset($_POST['search'])){
    $temper = $_POST['temper'];
    $result = mysqli_query($db, "SELECT imagePath, imageTags, tempID FROM BOARD WHERE tempID=$temper");
  }
?>

<!DOCTYPE html>
<html>
<head>
	<title>Image Upload</title>
	<style type="text/css">
		#content{
			width: 50%;
			margin: 20px auto;
			border: 1px solid #cbcbcb;
		}
		form{
			width: 50%;
			margin: 20px auto;
		}
		form div{
			margin-top: 5px;
		}
		#img_div{
			width: 80%;
			padding: 5px;
			margin: 15px auto;
			border: 1px solid #cbcbcb;
		}
		#img_div:after{
			content: "";
			display: block;
			clear: both;
		}
		img{
			float: left;
			margin: 5px;
      width: 300px;
			height: 400px;
		}
	</style>
</head>
<body>
<div id="content">
<?php
	while ($row = mysqli_fetch_array($result)) {
  	echo "<div id='img_div'>";
    echo "<p><img src='".$row['imagePath']."' alt='image_load_failed'></p>";
		echo "<p>Tags: ".$row['imageTags']."</p>";
    echo "<p>TempID: ".$row['tempID']."</p>";
  	echo "</div>";
	}
?>

	<form method="POST" action="SearchImage.php" enctype="multipart/form-data">
		<input type="hidden" name="size" value="1000000">
    <div>Temperature Range</div>
    <div>
      <input type="radio" name="temper" value="1">-5 ~ 0</input>
      <input type="radio" name="temper" value="2">0 ~ 5</input>
      <input type="radio" name="temper" value="3">5 ~ 10</input>
      <input type="radio" name="temper" value="4">10 ~ 15</input>
    </div>
		<div>
			<button type="submit" name="search">SEARCH</button>
		</div>
	</form>
</div>
</body>
</html>
