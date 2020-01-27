<?php 
include('web\connection.php');
if (isset($_REQUEST['submit'])) {
    $fnm = $_REQUEST['fnm'];
    $lnm = $_REQUEST['lnm'];
    $email = $_REQUEST['email'];
    $txt = $_REQUEST['txt'];


                $res=$cn->query("INSERT INTO `inq`(`f_nm`, `l_nm`,`email`,`msg`) VALUES ('$fnm','$lnm','$email','$txt')");
                }
?>
<!DOCTYPE html> 
<html lang="en">
   
<!-- Mirrored from preciousthemes.com/demo/business-edge/contact.html by HTTrack Website Copier/3.x [XR&CO'2014], Sat, 17 Feb 2018 14:15:35 GMT -->
<head>
    <meta name="description" content="Contact Us" />
    <link rel="profile" href="http://gmpg.org/xfn/11">
    <meta name="keywords" content="contact-us" />
    <meta name="author" content="Precious Themes" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <meta charset="utf-8">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
      
      <title>Business Edge &#8211; Responsive Business Template</title>
      
        <!-- favicon -->
        <link rel="icon" type="icon/png" href="images/fav-icon.png">
        
         <!-- Mean Menu Styles -->
        <link rel="stylesheet" type="text/css" href="css/meanmenu.css">

         <!-- Jquery ui Styles -->
        <link rel="stylesheet" type="text/css" href="css/jquery-ui.min.css">

        <!-- Slick slider Styles -->
        <link rel="stylesheet" type="text/css" href="css/slick.css">

        <!-- Icon Styles -->
        <link rel="stylesheet" type="text/css" href="css/lightgallery.min.css">

        <!-- Lightgallery Css -->
        <link rel="stylesheet" type="text/css" href="css/icons.css">

        <!-- Primary(Cyan) Color Css -->
        <link rel="stylesheet" type="text/css" id="color" href="css/colors/color-cyan.css">

        <!-- Styles -->
        <link rel="stylesheet" type="text/css" href="style.css">

        <!-- Color Switcher Styles -->
        <link rel="stylesheet" type="text/css" href="css/color-switcher.css">

        <!-- FontAwesome Styles -->
        <link rel="stylesheet" type="text/css" href="css/font-awesome.css">
        
        <link href="https://fonts.googleapis.com/css?family=Poppins:300,300i,400,400i,500,500i,600,600i,700,700i,800,800i" rel="stylesheet">

        <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i,800,800i" rel="stylesheet">

   </head>

    <body class="inner-page">

    <?php include('header.php');?>
            <div id="banner-inner" class="inner-page-banner overlay" style="background:url(images/contact-bg.jpg) top center; background-size: cover;">
                
                <div class="container">
                    <h1 class="page-title">Contact <span>Us</span></h1>

                    <div class="breadcrumb">
                        <ul>
                            <li><a href="index.php">Home</a></li>
                            <li class="active"><a href="contact.php">Contact Us</a></li>
                        </ul>
                    </div> <!-- .breadcrumb -->

                </div>

            </div> <!-- .inner-page-banner -->

            <div id="content" class="site-content full-width-page">

                <div class="container">
                    
                    <div id="primary" class="content-area">
                        
                        <section id="contact-page-contents" class="contact-page-section">

                            <div class="container">

                                <div id="contact-us" class="contact-us-wrapper">
                                    
                                    <div class="inner-wrapper">

                                        <div class="map-container">
                                        <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2347815.107519013!2d-80.41891969113277!3d35.64024425298417!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x88541fc4fc381a81%3A0xad3f30f5e922ae19!2sNorth+Carolina%2C+USA!5e0!3m2!1sen!2snp!4v1507625571694" width="690" height="560" style="border:0" allowfullscreen></iframe>
                                        </div> <!-- .map-holder -->

                                        <div class="contact-form-container">

                                            <div class="section-title">
                                                <h2>Send Us <span>Email</span></h2>
                                                <p>You have feedback for us? We would be glad to help. Please tell us about it.</p>
                                            </div> <!-- .section-title -->
                                            <form method="post">
                                                <label>First Name*</label>
                                                <input type="text" required="required" name="fnm">

                                                <label>Last Name*</label>
                                                <input type="text" required="required" name="lnm">

                                                <label>Your Email*</label>
                                                <input type="email" required="required" name="email">

                                                <label>Message Goes Here</label>
                                                <textarea name="txt"></textarea>

                                                <input type="submit" name="submit" value="Submit Message">
                                            </form>

                                        </div> <!-- .contact-form-container -->

                                    </div> <!-- .inner-wrapper -->

                                </div> <!-- .contact-us-wrapper -->

                            </div>

                        </section> <!-- .contact-page-section -->

                    </div> <!-- #primary -->

                </div> <!-- container -->

            </div> <!-- #content -->

            <div id="footer-widgets" class="widget-area">

              <div class="container">
                  <div class="inner-wrapper">

                      <div class="widget-column footer-active-3">

                          <aside id="about-us" class="widget widget_about_us">

                                <h2 class="widget-title">About Us</h2>
                                
                                <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam</p>

                                <div class="social-widgets">
                                    <ul>
                                      <li><a target="_blank" href="http://facebook.com/">Facebook</a></li>
                                      <li><a target="_blank" href="http://twitter.com/">Twitter</a></li>
                                      <li><a target="_blank" href="https://plus.google.com/">Linkedln</a></li>
                                    </ul> 
                                 </div>

                          </aside>

                      </div>

                      <div class="widget-column footer-active-3">

                          <aside id="recent-posts" class="widget widget_recent_posts">

                                <h2 class="widget-title">Recent News & Blogs</h2>
                                
                                <ul>

                                    <li><a href="#">Father Son Precious Nap Together</a></li>
                                    <li><a href="#">Virtual Games Vs Real Games</a></li>
                                    <li><a href="#">Latest News & Blogs</a></li>
                                    <li><a href="#">Planning, Goals and Execution</a></li>
                                    <li><a href="#">The Experienced Skateboard Kid</a></li>
                                    
                                </ul>

                          </aside>

                      </div>

                      <div class="widget-column footer-active-3">

                          <aside id="popular-products" class="widget widget_text">

                                <h2 class="widget-title">Contact Info</h2>
                                
                                <ul class="product-listing">

                                    <li>
                                        <i class="fa fa-map-marker" aria-hidden="true"></i> 5689 B Street, Vincent City, USA
                                    </li>

                                    <li>
                                        <i class="fa fa-phone" aria-hidden="true"></i> <a href="#">+4568-5895-6321</a>
                                    </li>

                                    <li>
                                        <i class="fa fa-envelope-o" aria-hidden="true"></i> <a href="#">info@businessedge.com
</a>
                                    </li>

                                    <li>
                                        <i class="fa fa-fax" aria-hidden="true"></i> <a href="#">+4568-5895-6321</a>
                                    </li>

                                    <li>
                                        <i class="fa fa-globe" aria-hidden="true"></i> <a href="https://www.preciousthemes.com/">preciousthemes.com</a>
                                    </li>

                                </ul>

                          </aside>
                          
                      </div>

                  </div><!-- .inner-wrapper -->
              </div><!-- .container -->

              <a href="#page" class="gotop" id="btn-gotop" style="display: inline;"><i class="fa fa-angle-double-up" aria-hidden="true"></i></a>

            </div><!-- #footer-widgets -->

            <footer id="colophon" class="site-footer">

                <div class="container">
                    <div class="site-info">
                        Copyright © All Rights Reserved <span class="sep"> | </span>Business Edge by <a href="https://www.preciousthemes.com/">Precious Themes</a>
                        
                    </div><!-- .site-info -->
                </div> <!-- .container -->

            </footer><!-- #colophon -->

        </div> <!-- #page -->

        <!--js Library  -->
        <script src="js/jquery.js"></script>
        
        <!-- Slick Js -->
        <script src="js/slick.js"></script>
       
        <!-- Mean-Menu -->
        <script src="js/jquery.meanmenu.js"></script>

        <!-- Jquery Ui -->
        <script src="js/jquery-ui.min.js"></script>

        <!-- waypoints -->
       <script src="js/waypoints.min.js"></script>

        <!-- Counter Up -->
        <script src="js/jquery.counterup.min.js"></script>

        <!-- Lightgallery Js -->
        <script src="js/lightgallery.min.js"></script>

        <!-- Isotop, Mixitup -->
        <script src="js/jquery.mixitup.min.js"></script>

        <!-- Custom js -->
        <script src="js/custom.js"></script>

        <!-- Color Switcher -->
        <script src="js/color-switcher.js"></script>

    </body>
