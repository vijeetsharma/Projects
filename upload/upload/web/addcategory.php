<?php
include('connection.php');
if(isset($_POST['add']))
{
		$n=$_POST['name'];
		$max_size = 800; // max image size in pixel
		$dtn_folder= 'images/';
		$watermark_fl = 'lg1.png'; // watermark image file
		
		$img_nm = $_FILES['image']['name']; // file name
		$img_size = $_FILES['image']['size']; // file size
		$img_tmp = $_FILES['image']['tmp_name']; // file temp
		$img_type = $_FILES['image']['type'];
		
		$fpath = $dtn_folder.$img_tmp;
		
		switch(strtolower($img_type))  // determine upload image file
		{
			// create new image from file
			case 'image/png':
				$img_resource = imagecreatefrompng($img_tmp);
				break;
			case 'image/gif':
				$img_resource = imagecreatefromgif($img_tmp);
				break;
			case 'image/jpeg': case 'image/jpg':
				$img_resource = imagecreatefromjpeg($img_tmp);
				break;
			default:
				$img_resource = false;

		}		

		if($img_resource)
		{
			// copy and resize part of an image with resampling
			list($img_width,$img_height) = getimagesize($img_tmp);

			// construct a proportional size of new image
			$img_scale = min($max_size / $img_width, $max_size / $img_height);
			$nw_img_width = ceil($img_scale * $img_width);
			$nw_img_height = ceil($img_scale * $img_height);
			$new_canvas = imagecreatetruecolor($nw_img_width, $nw_img_height);

			if(imagecopyresampled($new_canvas, $img_resource, 0, 0, 0, 0, $nw_img_width, $nw_img_height, $img_width, $img_height))
			{
				if(!is_dir($dtn_folder)) //check directory is exist or not.
				{
					mkdir($dtn_folder); // if not than create.
				}
				
				// calculation of center position.
				$wtrmrk_left = ($nw_img_width/2) - (800/2); // watermark from left side.
				$wtrmrk_btm = ($nw_img_height/2) - (40/2); // watermark from bottom side.
				$watermark = imagecreatefrompng($watermark_fl); // png file of watermark.

				imagecopy($new_canvas, $watermark, $wtrmrk_left, $wtrmrk_btm, 0, 0, 300, 100);  // merge image

				$pic=imagejpeg($new_canvas, $dtn_folder.'/'.$img_nm , 90);

				//header('Content-Type:image/jpeg');
				//imagejpeg($new_canvas,NULL,90);
				//move_uploaded_file($new_canvas, $dtn_folder);
				move_uploaded_file($_FILES['image']['tmp_name'],$pic);
				$qry="INSERT INTO `category`(`name`, `image`) VALUES ('$n','$img_nm')";
				$res=$cn->query($qry);
				
				if($res)
				{			
					header("location:viewcategory.php");
				}
		
				else
				{
					echo "Not inserted.";
				} 
				imagedestroy($new_canvas);
				imagedestroy($img_resource);
				die();

			}
		}
	
}
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Charli Events-Add Category</title>
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
																					
																					<form method="post" enctype="multipart/form-data">
																						<div class="vali-form">
																						<div class="col-md-6 form-group1">
																						  <label class="control-label">Select Category:</label>
																						  
													<select required="" style="background-color: #fff;border: 1px solid #E9E9E9;
    font-size: 0.9em;
    width: 100%;
    outline: none;
    padding: 0.7em 1em;
    color: #999;
    margin-top: 0.5em;
    font-family: 'Roboto', sans-serif;" name="name">
													    <option>Select Category</option>
													    <?php 	$sel="select * from service";
					$res=$cn->query($sel);
					while($rr=mysqli_fetch_array($res))
					{ ?>
					<option value="<?php echo $rr['id'] ?>"><?php echo $rr['name'] ?></option>
							<?php } ?>						    
													</select>									</div>
																						<div class="col-md-6 form-group1 form-last">
																						  <label class="control-label">Image:</label>
																						  <input type="file" name="image" required="" style="background-color: #fff;">
																						</div>
																						
																						
																					  
																						<div class="col-md-12 form-group button-2"><center>
																						  <button type="submit" class="btn btn-primary" name="add">Add</button>
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
