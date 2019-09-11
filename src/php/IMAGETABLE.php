<?php
  $db_server = "localhost";
  $db_id = "id";
  $db_password = "pwd";
  $db_name = "name";

  $con = mysqli_connect($db_server, $db_id , $db_password, $db_name);

  $query1 = "SELECT * FROM IMAGE WHERE imageID NOT IN (SELECT TOP 252 imageID FROM IMAGE ORDER BY imageID) ORDER BY imageID";
  $result = mysqli_query($con, $query1);

  while ($row = mysqli_fetch_array($result)) {
    $query2 = "INSERT INTO BOARD(UserID, boardDate, imagePath, imageTags, tempID) VALUES('Admin'".",now(),'".$row['imagePath']."','".$row['imageTags']."',".$row['imageTempID'].")";
    echo "<p>".$query2."<p>";
    $result2 = mysqli_query($con, $query2);

    if(!mysqli_fetch_array($result))
              echo"<p>error<p>";
  }

  mysqli_close($con);
 ?>
