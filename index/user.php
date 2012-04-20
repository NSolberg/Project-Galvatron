<?
$userID=$_COOKIE["KEY"];
$con = mysql_connect('localhost', 'root', '498');
$wins = "";
$loses = "";
$userName = "";
$email = "";
$dataBaseName = "legacy";
$tblName = "user";
 $con = mysql_connect('localhost', 'root', '498');
if (!$con) {
	die('Could not connect: ' . mysql_error());
}

mysql_select_db($dataBaseName, $con);

//post data from forms
$sql = "SELECT * FROM $tblName WHERE userKey = $userID";


$data = mysql_query($sql);
$result = mysql_fetch_array($data);

$wins = $result['wins'];
$loses = $result['loses'];
$email = $result['email'];
$userName = $result['userName'];
?>




<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>user</title>
			<meta name="Nick Solberg" content="Legacy User" />
		<style type="text/css">
			.text { font-size: 30px; font-family: courier; border:none; position:absolute; top: 47px; left: 610px;}
			.text2 { font-size: 30px; font-family: courier; border:none; position:absolute; top: 47px; left: 750px;}
			.text3 { font-size: 87px; font-family: arial; position:absolute; top: 30px; left: 300px; font-weight: bold}
		</style>
		<!-- Date: 2012-04-01 -->
	</head>
	<body>
		<p class = "text3">
		<?
		echo "<br>".$userName;
		?>
		</p>
		<img src ="userPage.png"  alt="Background" />
		<a href="login.html" class="text"> Login  </a>
		<a href="homepage.html" class="text2"> Home </a>
		<p class="text3"> </p>
		
	</body>
</html>
