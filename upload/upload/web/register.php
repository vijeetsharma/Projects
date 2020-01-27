<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Dashboard</title>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
</head>

<body>
   <div class="page-container">
 

	   <div class="inner-content">
				<?php  
				include('header.php');
				?>
		</div>
		<!--inner-content closed-->
				<div class="outter-wp col-md-3"></div>
				<div class="outter-wp col-md-6">
											
													<div class="forms-main">
														<h2 class="inner-tittle"><center>Register here</center></h2>
															<div class="graph-form">
																	<div class="validation-form">
																				<!---->
																					
																					<form>
																						<div class="vali-form">
																						<div class="col-md-6 form-group1">
																						 
																						  <input type="text" placeholder="Firstname" required="" style="background-color:#FFFFFF;">
																						</div>
																						<div class="col-md-6 form-group1 form-last">
																						 
																						  <input type="text" placeholder="Lastname" required="" style="background-color:#FFFFFF;">
																						</div>
																						<div class="clearfix"> </div>
																						</div>
																						
																						<div class="col-md-12 form-group1 group-mail">
																						
																						  <input type="text" placeholder="Email" required="" style="background-color:#FFFFFF;">
																						</div>
																						 <div class="clearfix"> </div>
																		
																						<div class="vali-form">
																						<div class="col-md-12 form-group1">
																						 
																						  <input type="text" placeholder="Phone Number" required="" style="background-color:#FFFFFF;">
																					
																						<div class="clearfix"> </div>
																						</div>
																						 <div class="vali-form vali-form1">
																						<div class="col-md-6 form-group1">
																						
																						  <input type="password" placeholder="Create a password" required="" style="background-color:#FFFFFF;">
																						</div>
																						<div class="col-md-6 form-group1 form-last">
																						  <input type="password" placeholder="Repeated password" required="" style="background-color:#FFFFFF;">
																						</div>
																						<div class="clearfix"> </div>
																						</div>
																						 
																						
																					  <div class="col-md-3 form-group button-2"></div>
																						<div class="col-md-6 form-group button-2">
																						  <button type="submit" class="btn btn-primary">Submit</button>
																						  <button type="reset" class="btn btn-default">Reset</button>
																						</div>
																					  <div class="clearfix"> </div>
																					</form>
																				
																				<!---->
																			 </div>

																		</div>
																</div> 
														<!--//forms-->											   
												</div>
					<!--outter-wp closed-->
		
										<footer>
										   <p>&copy 2016 Augment . All Rights Reserved | Design by <a href="https://w3layouts.com/" target="_blank">W3layouts.</a></p>
										</footer>
									<!--footer section end-->
							
							</div>
		
							<script>
							var toggle = true;
										
							$(".sidebar-icon").click(function() {                
							  if (toggle)
							  {
								$(".page-container").addClass("sidebar-collapsed").removeClass("sidebar-collapsed-back");
								$("#menu span").css({"position":"absolute"});
							  }
							  else
							  {
								$(".page-container").removeClass("sidebar-collapsed").addClass("sidebar-collapsed-back");
								setTimeout(function() {
								  $("#menu span").css({"position":"relative"});
								}, 400);
							  }
											
											toggle = !toggle;
										});
							</script>
<!--js -->
<link rel="stylesheet" href="css/vroom.css">
<script type="text/javascript" src="js/vroom.js"></script>
<script type="text/javascript" src="js/TweenLite.min.js"></script>
<script type="text/javascript" src="js/CSSPlugin.min.js"></script>
<script src="js/jquery.nicescroll.js"></script>
<script src="js/scripts.js"></script>

<!-- Bootstrap Core JavaScript -->
   <script src="js/bootstrap.min.js"></script>

	</div> <!--left-wp closed-->
</div> <!--page container closed-->
</body>
</html>
