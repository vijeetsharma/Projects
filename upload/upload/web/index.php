<?php
	include('connection.php');
	
	if(isset($_POST['login']))
	{
			$n=$_POST['name'];
			$e=$_POST['email'];
			$p=$_POST['password'];
			$sel="SELECT * from `admin` WHERE `email`='$e' AND `password`='$p'";

			$res=$cn->query($sel);
			
			$row=mysqli_fetch_array($res);
			$no=mysqli_num_rows($res);
			
			if($no==1)
			{
				header('location:dashboard.php');
			}
			else
			{
				echo 'Login failed.';
			}
	}
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Charli Events-Admin Login</title>
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
														<h2 class="inner-tittle"><center>Admin Login</center></h2>
															<div class="graph-form">
																	<div class="validation-form">
																				<!---->
																					
																					<form method="post">
																						<div class="vali-form">
																	
																						<div class="col-md-12 form-group1">
																						  <input type="text" placeholder="Email Id" required="" style="background-color: #fff;" name="email">
																						</div>
																						<div class="col-md-12 form-group1 form-last">
																						  <input type="password" required="" style="background-color: #fff;" name="password" placeholder="Password">
																						</div>
																						
																						
																					  
																						<div class="col-md-12 form-group button-2"><center>
																						  <button type="submit" class="btn btn-primary" name="login">Login</button>
																						  <button type="reset" class="btn btn-default">Reset</button></center>
																						</div>
																					  <div class="clearfix"> </div>
																					</form>
																				
																				<!---->
																			 </div>

																		</div>
																</div> 
								<!--
										<div class="custom-widgets">
												   <div class="row-one">
												 
												 
												 
													</div>row closed-->
					</div>						 <!--	</div> custom-widgets closed-->
												
					</div>	
					<!--outter-wp closed-->
		
										<footer>
										   <p></p>
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
