<?php
$con = mysql_connect('localhost', 'root', '498');
$userName = "";
$password = "";
$dataBaseName = "legacy";
$tblName = "user";

function redirect() {
    ?>
    <html>
    <head>
    <script language="JavaScript">
    <!--
		
		window.location = "../user.php";
		
    //-->
    </script>
    </head>
    <body>
    </body>
    </html>
    <?
    exit;
}

//Connect to le server
$con = mysql_connect('localhost', 'root', '498');
if (!$con) {
	die('Could not connect: ' . mysql_error());
}

//Connect to the database
mysql_select_db($dataBaseName, $con);

//post data from forms
$userName = $_POST['userName'];
$password = $_POST['password'];
$sql = "SELECT * FROM $tblName WHERE userName = '$userName' and password='$password'";
$result = mysql_query($sql);

//count and compare rows
$count = mysql_num_rows($result);

if ($count == 0) {
	echo "Invalid user try again";
}

$sql2 = "SELECT * FROM $tblName WHERE userName='$userName'";
$result = mysql_query($sql, $con);
$value = mysql_fetch_assoc($result);
$KEY = $value['userKey'];
setcookie("KEY", $KEY, time() + 3600);

if (isset($_COOKIE["KEY"])) {

	redirect();
}

?>