<?php

  $db_server = "localhost";
  $db_id = "id";
  $db_password = "pwd";
  $db_name = "name";

	$db = mysqli_connect($db_server, $db_id , $db_password, $db_name);
	$msg = "";

	if (isset($_POST['upload'])) {
		$target = "images/".basename($_FILES['image']['name']);

		$image = "http://csy9608.cafe24.com/".$target;
		$image_text = mysqli_real_escape_string($db, $_POST['image_text']);
    $image_temper = $_POST['temper'];

		$sql = "INSERT INTO IMAGE(imagePath, imageTags, imageTempID) VALUES ('$image', '$image_text', $image_temper)";
		mysqli_query($db, $sql);

		if (move_uploaded_file($_FILES['image']['tmp_name'], $target)) {
			$msg = "Image uploaded successfully";
		}else{
			$msg = "Failed to upload image";
		}
	}

	$result = mysqli_query($db, "SELECT * FROM IMAGE WHERE imagePath='$image'");
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
			height: 140px;
		}
	</style>
</head>
<body>
<div id="content">
<?php
	while ($row = mysqli_fetch_array($result)) {
    echo "Upload Success";
		echo "<div id='img_div'>";
    echo "<p><img src='".$row['imagePath']."' alt='image_load_failed' height='42' width='42'></p>";
		echo "<p>Tags: ".$row['imageTags']."</p>";
    echo "<p>TempID: ".$row['imageTempID']."</p>";
  	echo "</div>";
	}
?>

	<form method="POST" action="imageUpload.php" enctype="multipart/form-data">
		<input type="hidden" name="size" value="1000000">
		<div>
			<input type="file" name="image">
		</div>
		<div>
			<textarea id="text" cols="40" rows="4" name="image_text" placeholder="Write Tags connect with +"></textarea>
    </div>
    <div>Temperature Range</div>
    <div>
      <input type="radio" name="temper" value="1">-5 ~ 0</input>
      <input type="radio" name="temper" value="2">0 ~ 5</input>
      <input type="radio" name="temper" value="3">5 ~ 10</input>
      <input type="radio" name="temper" value="4">10 ~ 15</input>
    </div>
		<div>
			<button type="submit" name="upload">POST</button>
		</div>
	</form>
</div>
</body>
</html>
