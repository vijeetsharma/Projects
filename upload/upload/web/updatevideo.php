<?php
include('connection.php');
if(isset($_POST['update']))
{
	$l=$_POST['name'];
	$i=$_GET['vid'];
	$qry="UPDATE `video` SET `link`='$l' WHERE `id`=".$i;
	echo $qry;
	$res=$cn->query($qry);
	
	if($res)
	{
		header("location:viewvideos.php");
	}
	
	else
	{
		echo "Not updated...!!!";
	}
}

?>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Charli Events-Update Videos</title>
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
														<h2 class="inner-tittle"><center>Add Video</center></h2>
															<div class="graph-form">
																	<div class="validation-form">
																				<!---->
																					<?php 
																					$id=$_GET['vid'];
																					$sel = 'SELECT * FROM video where id='.$id;
																					$qer=$cn->query($sel);
																					
																					while($qe=mysqli_fetch_array($qer))	
																					{				?>
																					<form method="post" enctype="multipart/form-data">
																						<div class="vali-form">
																						<div class="col-md-12 form-group1">
																						  <label class="control-label">Link:</label>
																						  <input type="text" placeholder="Link of video" required="" style="background-color: #fff;" name="name" value="<?php echo $qe['link'];?>">
																						</div>
																						
																						
																					 
																						<div class="col-md-12 form-group button-2"><center>
																						  <button type="submit" class="btn btn-primary" name="update" onClick="return addbtn();"><i class="fa fa-user"></i>&nbsp;Update</button>
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
