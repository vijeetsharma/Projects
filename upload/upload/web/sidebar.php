     	<div class="sidebar-menu">
					<header class="logo">
					<a href="#" class="sidebar-icon"> <span class="fa fa-bars"></span> </a> <a href="index.php"> <span id="logo"> <h1>Admin</h1></span> 
				  </a> 
				</header>
			<div style="border-top:1px solid rgba(69, 74, 84, 0.7)"></div>
			<!--/down-->
							<div class="down">	
									  <a href="index.php"><img src="images/admin.jpg"></a>
									  <a href="index.php"><span class=" name-caret"></span></a>
									 <p>System Administrator in Company</p>
									<!--<ul>
									<li><a class="tooltips" href="index.php"><span>Profile</span><i class="lnr lnr-user"></i></a></li>
										<li><a class="tooltips" href="index.php"><span>Settings</span><i class="lnr lnr-cog"></i></a></li>
										<li><a class="tooltips" href="index.php"><span>Log out</span><i class="lnr lnr-power-switch"></i></a></li>
										</ul>-->
									</div>
							   <!--//down-->
                           <div class="menu">
									<ul id="menu" >
										<li><a href="dashboard.php"><i class="fa fa-tachometer"></i> <span>Dashboard</span></a></li>
										 <li id="menu-academico" ><a href="#"><i class="fa fa-spinner"></i> <span> Slider</span> <span class="fa fa-angle-right" style="float: right"></span></a>
										   <ul id="menu-academico-sub" >
											<li id="menu-academico-avaliacoes" ><a href="addslider.php"><i class="fa fa-plus-circle"></i> Add Slider</a></li>
				
											<li id="menu-academico-boletim" ><a href="viewslider.php"><i class="fa fa-expand"></i>View Slider</a></li>
										  </ul>
										</li>
										 <li id="menu-academico" ><a href="#"><i class="fa fa-info-circle"></i> <span>Services</span> <span class="fa fa-angle-right" style="float: right"></span></a>
											 <ul id="menu-academico-sub" >
												<li id="menu-academico-avaliacoes" ><a href="addservices.php"><i class="fa fa-plus-circle"></i> Add Services</a></li>
												
												<li id="menu-academico-boletim" ><a href="viewservices.php"><i class="fa fa-expand"></i> View Services</a></li>
											  </ul>
										 </li>
									<li id="menu-academico" ><a href="#"><i class="fa fa-camera-retro"></i> <span>Gallery</span> <span class="fa fa-angle-right" style="float: right"></span></a>
										  <ul id="menu-academico-sub" >
										    <li id="menu-academico-avaliacoes" ><a href="addcategory.php"><i class="fa fa-plus-circle"></i> Add Category</a></li>
				
										    <li id="menu-academico-boletim" ><a href="viewcategory.php"><i class="fa fa-expand"></i> View Category</a></li>
										  </ul>
									 </li>
									 <li><a href="#"><i class="fa fa-video-camera"></i> <span>Videos</span><span class="fa fa-angle-right" style="float: right"></span></a>
									   <ul>
										<li><a href="addvideos.php"><i class="fa fa-plus-circle"></i> Add Videos</a></li>
										<li><a href="viewvideos.php"><i class="fa fa-expand"></i> View Videos</a></li>
									   </ul>
									</li>
									<li><a href="#"><i class="fa fa-user"></i> <span>Customer</span><span class="fa fa-angle-right" style="float: right"></span></a>
									   <ul>
										<li><a href="viewinquiry.php"><i class="fa fa-exclamation-circle"></i>&nbsp;Customer Inquiry</a></li>
										<li><a href="viewstory.php"><i class="fa fa-envelope-o"></i>&nbsp;Customer Story</a></li>
									   </ul>
									</li>
									
								  </ul>
								</div>
							  </div>
							  <div class="clearfix"></div>		
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
