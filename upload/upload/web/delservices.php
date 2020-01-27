<?php
include('connection.php');
$id=$_GET['sid'];

$del="DELETE FROM `service` WHERE `id`=".$id;
$res=$cn->query($del);
if($res)
{
	header("location:viewservices.php");
}
else
{
	echo "Not Deleted...!!!";
}
?>