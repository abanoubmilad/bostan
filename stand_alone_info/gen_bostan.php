<?php
ini_set('max_execution_time', 6000);
header('Content-Type: text/html; charset=utf-8');

$db_host="localhost";
$db_name="bostan_db";
$tb_name="bostan_tb";
$username="root";
$password="";
$conn=mysql_connect($db_host,$username,$password)or die("cannot connect"); 
mysql_select_db($db_name)or die("cannot select DB");


$str=file_get_contents("./bostan_doc.txt");
$array_text=preg_split( "/[1-9]|[1-9][0-9]|[1-9][0-9][0-9]|1[0-2][0-2][0-6]/", $str );
$array_text=array_map('trim',$array_text);

$str2=file_get_contents("./bostan.txt");
$array_text2=preg_split( "/[1-9]|[1-9][0-9]|[1-9][0-9][0-9]|1[0-2][0-2][0-6]/", $str );
$array_text2=array_map('trim',$array_text);

echo"<html>";
echo "<p>done loading and filtering now adding data to sql :)</p>";

echo sizeof($array_text);
echo sizeof($array_text2);
	//mysql_query("INSERT INTO $tb_name(sec,secd) values ('$cur', '$itr')", $conn);

echo "<p>all done :)</p>";
echo"</html>";

?>