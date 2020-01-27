<?php
include('connection.php');
$id=$_GET['vid'];
$del="DELETE FROM `video` WHERE `id`=".$id;
$res=$cn->query($del);
if($res)
{
	header("location:viewvideos.php");

}
else
{
	echo "Not Deleted...!!!";
}
?>