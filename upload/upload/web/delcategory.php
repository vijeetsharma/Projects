<?php
include('connection.php');
$id=$_GET['cid'];
$del="DELETE FROM `category` WHERE `id`=".$id;
$res=$cn->query($del);
if($res)
{
	header("location:viewcategory.php");
}
else
{
	echo "Not Deleted...!!!";
}
?>