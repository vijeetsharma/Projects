<?php
include('connection.php');
if(isset($_POST['update']))
{
	$n=$_POST['name'];
	$id=$_GET['cid'];
	$folder='../images/';
	$i=$_FILES['image']['name'];
	$pic=$folder.$i;
//	print_r($_FILES);
	
	$i_type=$_FILES['image']['type'];
	if($i_type=="image/jpeg")
	{
		move_uploaded_file($_FILES['image']['tmp_name'],$pic);
		$qry="UPDATE `category` SET `name`='$n',`image`='$i' WHERE `id`=".$id;
	//	echo $qry;
		$res=$cn->query($qry);
		
		if($res)
		{
					echo '<script language="javascript">';
					echo 'alert("Inserted Successfully...!!!")';
					echo '</script>';
					header("location:viewcategory.php");
		}
		
		else
		{
					echo '<script language="javascript">';
					echo 'alert("Not updated")';
					echo '</script>';
		}
	}
	
	else
	{
		echo '<script language="javascript">';
		echo 'alert("select jpeg format of your image.")';
		echo '</script>';
	}
	
}
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Charli Events-Update Category</title>
</head>

<body>
   <div class="page-container">
 
	<div class="left-content">
	   <div class="inner-content">
				<?php 
				include('header.php');
				?>
		</div>
		<!--inner-content closed-->
		
				<div class="outter-wp">
								
										<div class="forms-main">
														<h2 class="inner-tittle"><center>Add Category</center></h2>
															<div class="graph-form">
																	<div class="validation-form">
																				<!---->
																				<?php 
																				$id=$_GET['cid'];
																				$sel="select * from `category` where `id`=".$id;
																				$res=$cn->query($sel);
																				while($rr=mysqli_fetch_array($res))
																				{
																				?>
																					
																					<form method="post" enctype="multipart/form-data">
																						<div class="vali-form">
																						<div class="col-md-6 form-group1">
																						  <label class="control-label">Category Name:</label>
																						  <input type="text" placeholder="Category Name" required="" style="background-color: #fff;" name="name" value="<?php echo $rr['name'];?>">
																						</div>
																						<div class="col-md-6 form-group1 form-last">
																						  <label class="control-label">Image:</label>
																						  <input type="file" name="image" required="" style="background-color: #fff;">
																						</div>
																						
																						
																					  
																						<div class="col-md-12 form-group button-2"><center>
																						  <button type="submit" class="btn btn-primary" name="update"><i class="fa fa-user"></i>&nbsp;Update</button>
																						  <button type="reset" class="btn btn-default">Reset</button></center>
																						</div>
																					  <div class="clearfix"> </div>
																					</form>
																				<?php
																				}
																				?>
																				<!---->
																			 </div>

																		</div>
																</div> 
															</div>
												
					</div>	
					<!--outter-wp closed-->
		<?php include('footer.php'); ?>
	</div> <!--left-wp closed-->
</div> <!--page container closed-->
</body>
</html>
