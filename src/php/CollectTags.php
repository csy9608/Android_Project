<?php

  $db_server = "localhost";
  $db_id = "id";
  $db_password = "pwd";
  $db_name = "name";

  if(isset($_POST['toText'])){
  	$db = mysqli_connect($db_server, $db_id , $db_password, $db_name);

    $result = mysqli_query($db, "SELECT imageTags FROM IMAGE;");
    $tags = "";
    while ($row = mysqli_fetch_array($result)) {
        $tags = $tags."+".$row['imageTags'];
    }

    echo $tags;

    $FileName = $_POST['Filename'].".txt";
    $myfile = fopen($FileName, "w") or die("Unable to open file!");
    fwrite($myfile, $tags);
    fclose($myfile);
  }
 ?>

 <!DOCTYPE html>
 <html>
   <head>
     <meta charset="utf-8">
     <title>
     </title>
   </head>
   <body>
     <form action="CollectTags.php" method="post">
       <input type="text" name="Filename" placeholder="Enter TextFile Name">
       <input type="submit" name="toText" value="CreateTextFile">
     </form>
   </body>
 </html>
