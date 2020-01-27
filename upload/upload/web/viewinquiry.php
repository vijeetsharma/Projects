<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Charli Events-View Inquiry</title>
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
												 		<h3 class="inner-tittle two">Customers Inquiry</h3>
													<div class="graph">
															<div class="tables" style="overflow:scroll; height:60%;">
															
																<table class="table table-hover"> 
																<thead> 
																<tr> 
																<th>Sr. No</th> 
																<th>Name</th> 
																<th>Email</th>
																<th>Message</th> 
																</tr> 
																</thead> 
																<tbody>
																<?php 
																include('connection.php');

																$sel = 'SELECT * FROM inq';
																		$qer=$cn->query($sel);
																		$r=0;
																		while($qe=mysqli_fetch_array($qer))	
																		{	
																		$r++;													
																	 ?> 
																<tr> 
																<th scope="row"><?php echo $r; ?></th> 
																<td><?php echo $qe['f_nm']." ".$qe['l_nm'];?></td> 
																<td><?php echo $qe['email'];?></td>
																
																<td><?php echo $qe['msg'];?></td>
																</tr> 
																<?php } ?>
																</tbody> 
																</table>
															</div>
												
													</div>
												 
												 
													</div><!--row closed-->
												</div>
					</div>	
					<!--outter-wp closed-->
		<?php include('footer.php'); ?>
	</div> <!--left-wp closed-->
</div> <!--page container closed-->
</body>
</html>
