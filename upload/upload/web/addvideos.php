<?php
include('connection.php');
if(isset($_POST['add']))
{
	
	$qry='INSERT INTO `video`(`link`) VALUES ("'.$_POST['name'].'")';

	$res=$cn->query($qry);
}

?>
<script language="javascript">

function addbtn()
{
	swal("Done", "Record Inserted...!!!", "success")
}
</script>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Charli Events-Add Videos</title>
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
																					
																					<form method="post" enctype="multipart/form-data">
																						<div class="vali-form">
																						<div class="col-md-12 form-group1">
																						  <label class="control-label">Link:</label>
																						  <input type="text" placeholder="Link of video" required="" style="background-color: #fff;" name="name">
																						</div>
																						
																						
																					  
																						<div class="col-md-12 form-group button-2"><center>
																						  <button type="submit" class="btn btn-primary" name="add" onClick="return addbtn();">Add</button>
																						  <button type="reset" class="btn btn-default">Reset</button></center>
																						</div>
																					  <div class="clearfix"> </div>
																					</form>
																				
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
