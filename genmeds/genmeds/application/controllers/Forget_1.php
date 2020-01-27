<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Forget_1 extends CI_Controller 
{
	function __construct()
	{
		parent::__construct();
		$this->load->model('Crude_model');
		$this->load->helper('sendsms_helper');
	}
	
	public function fdata()
	{
		if(!$this->input->post('mobile_no'))
		{
						$user['status']='404';
						$user['message'] ='Error: Mobile No Must Add.';
						echo json_encode($user);
						exit();
		}  
		$u_mob=$this->input->post('mobile_no');
		     if($this->Crude_model->checkmobile($u_mob))
				{
					
					$otp=mt_rand(1111,9999);

					 $msg="Your OTP code for VIATAXIS.COM IS $otp do not share with anyone for security purpose";
					 sendsms($u_mob,$msg);

					 $this->db->where('contact_number',$u_mob);

					 if($this->db->update('user_information',array('otp'=>$otp)))
					 {
					 	           $user['otp']=$otp;
									$user['status']='200';
									$user['message'] ='Send OTP SuccessFully';
									echo json_encode($user);
					 }
					 else
					 {
					       	$user['status']='404';
							$user['message'] ='Error: Check Your Data.';
							echo json_encode($user);

					 }

					              
				}
				else
				{
					       $user['status']='404';
							$user['message'] ='Error: Your Contact Number is not Exists.';
							echo json_encode($user);

				}

		
		 
	}
	
}
