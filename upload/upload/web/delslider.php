<?php
include('connection.php');
$id=$_GET['sid'];
$del="DELETE FROM `slider` WHERE `id`=".$id;
$res=$cn->query($del);
if($res)
{
	header("location:viewslider.php");
}
else
{
	echo "Not Deleted...!!!";
}
?>