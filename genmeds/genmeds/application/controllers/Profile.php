<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Profile extends CI_Controller 
{
	function __construct()
	{
		parent::__construct();
		$this->load->model('Crude_model');
	}
	
	public function editprofile()
	{
		if($this->input->post('user_id'))
		{
			$uid = $this->input->post('user_id');
		}
		else
		{	
			  $user['status'] = '404';
		 	  $user['message'] = 'Must enter User Id';
		      echo json_encode($user);
		}
		if($this->input->post('name'))
		{
			$nm = $this->input->post('name');
			$dt = array('user_name'=>$nm);

			$this->db->where('user_id',$uid);

			if($this->db->update('user_information',$dt))
			{   	
				$user['status'] = '200';
				$user['message'] = 'Successfully Edit Your Name';  
			}
			else
			{
				$user['status'] = '404';
		 		$user['message'] = 'Can Not Update Username';
			}
			echo json_encode($user);
		}
		else if($this->input->post('email'))
		{
			$em = $this->input->post('email');

			if($this->Crude_model->check_email($em))
				{
					$user['status']='404';
					$user['message'] ='Error: Email ID Already Exists.';
					echo json_encode($user);
				}

			else
			{
				$dt1 = array('user_email'=> $em);

			$this->db->where('user_id',$uid);

			if($this->db->update('user_information',$dt1))
			{   	
				$user['status'] ='200';
				$user['message'] ='Successfully Edit Your Email';  
			}
			else
			{
				$user['status'] = '404';
		 		$user['message'] = 'Can Not Update Email ID';
			}
			echo json_encode($user);
			}
			
		}
		
		else if($this->input->post('photo'))
		{
			$image = $this->input->post('photo');
			$path = "uploads/user_images/user_".$uid.".jpg";
			file_put_contents($path,base64_decode($image));

			$temp = '';
			$temp = "user_".$uid.".jpg";

			
			$dt3 = array('photo'=>$temp);

			$this->db->where('user_id',$uid);

			if($this->db->update('api_user',$dt3))
			{   	
				$user['status'] = '200';
				$user['message'] = 'Successfully Edit Your Photo'; 
			}
			else
			{
				$user['status'] = '404';
		 		$user['message'] = 'Can Not Update Photo';
			}
			echo json_encode($user);
		}

		else
		{
			exit();

		}
	}	
}
