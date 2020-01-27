<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Forget_2 extends CI_Controller 
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
		if(!$this->input->post('otp'))
		{
						$user['status']='404';
						$user['message'] ='Error: OTP Must Add.';
						echo json_encode($user);
						exit();

		} 
			if(!$this->input->post('password'))
		{
						$user['status']='404';
						$user['message'] ='Error: Password Must Add.';
						echo json_encode($user);
						exit();

		} 

		    $u_mob=$this->input->post('mobile_no');
		    $otp=$this->input->post('otp');
		    $pwd=$this->input->post('password');
		     // $uid = $this->input->post('user_id');

		    $kk = array ('contact_number'=>$u_mob,'otp'=>$otp);
		   
		    
		    // $this->db->where($kk, $uid);

		    $data = $this->db->where('contact_number',$u_mob);

		    if($data)
		    { 
		    	$ko = array('password'=>md5($pwd));
		    	
			    $lm = $this->db->update('user_information',$ko);
			    if($lm)
			    {
			    	$user['status']='200';
					$user['message'] ='Password Change SuccessFully';
					echo json_encode($user);
			    }
			    else
			    {
	    	        $user['status']='404';
					$user['message'] ='Error: Check Your data Forst.';
					echo json_encode($user);

			    }
		    }
		    else
		    {
		    	$user['status']='404';
				$user['message'] ='Error: Check Your Mobile no OR OTP no.';
				echo json_encode($user);
		    }
		   
		
		 
	}
	
}
