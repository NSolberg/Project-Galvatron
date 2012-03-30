<?php

  function error($msg) {
    ?>
    <html>
    <head>
    <script language="JavaScript">
    <!--
        alert("<?=$msg?>");
        history.back();
    //-->
    </script>
    </head>
    <body>
    </body>
    </html>
    <?
    exit;
}
function redirect() {
    ?>
    <html>
    <head>
    <script language="JavaScript">
    <!--
		
        alert("WELCOME!");
		window.location = "../login.html";
		
    //-->
    </script>
    </head>
    <body>
    </body>
    </html>
    <?
    exit;
}
function check_email_address($email) {
  // First, we check that there's one @ symbol, and that the lengths are right
  if (!ereg("^[^@]{1,64}@[^@]{1,255}$", $email)) {
    // Email invalid because wrong number of characters in one section, or wrong number of @ symbols.
    return false;
  }
  // Split it into sections to make life easier
  $email_array = explode("@", $email);
  $local_array = explode(".", $email_array[0]);
  for ($i = 0; $i < sizeof($local_array); $i++) {
     if (!ereg("^(([A-Za-z0-9!#$%&'*+/=?^_`{|}~-][A-Za-z0-9!#$%&'*+/=?^_`{|}~\.-]{0,63})|(\"[^(\\|\")]{0,62}\"))$", $local_array[$i])) {
      return false;
    }
  }  
  if (!ereg("^\[?[0-9\.]+\]?$", $email_array[1])) { // Check if domain is IP. If not, it should be valid domain name
    $domain_array = explode(".", $email_array[1]);
    if (sizeof($domain_array) < 2) {
        return false; // Not enough parts to domain
    }
    for ($i = 0; $i < sizeof($domain_array); $i++) {
      if (!ereg("^(([A-Za-z0-9][A-Za-z0-9-]{0,61}[A-Za-z0-9])|([A-Za-z0-9]+))$", $domain_array[$i])) {
        return false;
      }
    }
  }
  return true;
}
 /** require_once('recaptchalib.php');
  $privatekey = "6Ldkos4SAAAAANpaTUsl_KDUAXvNtLuK8xfQmLG0";
  $resp = recaptcha_check_answer ($privatekey,
                                $_SERVER["REMOTE_ADDR"],
                                $_POST["recaptcha_challenge_field"],
                                $_POST["recaptcha_response_field"]);

  if (!$resp->is_valid) {
    // What happens when the CAPTCHA was entered incorrectly
	error('Captcha wasnt entered correctly');
  }
  else{**/
 	
	$con = mysql_connect('localhost','root','498');
	if (!$con){
	die('Could not connect: ' . mysql_error());
	}
		mysql_select_db("legacy", $con);
		$myEmail=$_POST['email']; 
		//$myEmailCheck=$_POST['myEmailCheck'];
		$myPassword=$_POST['password'];
		$myPasswordCheck=$_POST['repassword'];
	
		if(!check_email_address($myEmail)){
			error('Invalid Email Address');
		}
		if($myPassword!=$myPasswordCheck){
			error('Passwords Do Not Match');
		}
		if($myEmail==""){
			error('We need an Email. Please Enter an Email'); 
		}
		if($myPassword==""){
			error('Password Field is blank');
		}
		else{
			$result = mysql_query("SELECT * FROM userName WHERE email='".$myEmail."'");
			$count=mysql_num_rows($result);
			if($count == 0){
					$sql="INSERT INTO user (userName, password, email)
				VALUES
				('$_POST[userName]','$_POST[password]','$_POST[email]')";
				if (!mysql_query($sql,$con))
				{
				die('Error: ' . mysql_error());
				}
				else{
					redirect();
					//echo "User Added! <br> <a href=\"../login.html\"> Back to Login?</a>";
				}
			}
			else{
				error('User already exists!');
				//echo "User already exists! <a href=\"../newUserLogin.html\">Be original</a>, or login in <a href=\"../login.html\"> here!</a>";
			}
		}
//}
mysql_close($con);
?>