
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Charli Events-View Slider</title>
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
								
										<div class="custom-widgets">
												   <div class="row-one">
												 		<h3 class="inner-tittle two">Sliders </h3>
													<div class="graph">
															<div class="tables" style="overflow:scroll; height:60%;">
															
																<table class="table table-hover"> 
																<thead> 
																<tr> 
																<th>Sr. No</th> 
																<th>Slider Name</th> 
																<th>Images</th> 
																<th>Delete</th>
																<th>Edit</th>
																</tr> 
																</thead> 
																<tbody>
																<?php
																include('connection.php');
																$qry="select * from slider";
																$res=$cn->query($qry);
																$r=0;
																while($rr=mysqli_fetch_array($res))
																{	
																$r++;
																?> 
																<tr> 
																<th scope="row"><?php echo $r;?></th> 
																<td><?php echo $rr['name'];?></td> 
																<td><img src="images/<?php echo $rr['image'];?>" style="height:100px; width:100px" /></td> 
																<td><a href="delslider.php?sid=<?php echo $rr['id'];?>" style="text-decoration:underline;"><button type="submit" class="btn btn-primary" name="delete">Delete</button></a></td> 
																<td><a href="updateslider.php?sid=<?php echo $rr['id'];?>" style="text-decoration:underline;"><button type="submit" class="btn btn-default"><i class="fa fa-edit"></i>&nbsp;Edit</button></a></td> 
																</tr> 
																<?php  } ?>
																</tbody> 
																</table>
															</div>
												
													</div>
												 
												 
													</div><!--row closed-->
												</div>  <!--custom-widgets closed-->
												
					</div>	
					<!--outter-wp closed-->
		<?php include('footer.php'); ?>
	</div> <!--left-wp closed-->
</div> <!--page container closed-->
</body>
</html>
