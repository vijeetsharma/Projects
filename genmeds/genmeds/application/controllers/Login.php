<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Login extends CI_Controller 
{
	function __construct()
	{
		parent::__construct();
		$this->load->model('Crude_model');
	}
	
	public function checklogin()
	{
		$em ='';
		$pwd ='';

		if($this->input->post('email'))
		{
			$em = $this->input->post('email');
		}

		else if($this->input->post('mobile'))
		{
			$em=$this->input->post('mobile');
		}

		else 
		{
			$user['status'] = '404';
			$user['message'] = 'Error: please Enter  Email Id Or Mobile No';
			echo json_encode($user);exit();
		}

		if($this->input->post('password'))
		{
			$pwd = $this->input->post('password');
		}
		else
		{
			$user['status'] = '404';
			$user['message'] = 'Error:please Enter password';
			echo json_encode($user);exit();
		}
		
		 if(!$this->Crude_model->select_login('user_information',$em,$pwd))
		 {

		 	$user['status'] = '404';
			$user['message'] = 'Error:please check data';
			echo json_encode($user);
		 } 
		 else
		 {
			$data = $this->Crude_model->select_login('user_information',$em,$pwd);
	 	     $j = 0;
			foreach ($data as $key)
			{
				$user['user_data'][$j]['id'] = $key->user_id;
				$user['user_data'][$j]['username'] = $key->user_name;
				$user['user_data'][$j]['phone'] = $key->contact_number;
				$user['user_data'][$j]['email'] = $key->user_email;
				$user['user_data'][$j]['password'] = $key->password;
				if($key->image=='')
						{
							$user['user_data'][$j]['photo']='http://genmeds.webmasterssurat.com/uploads/user_images/default.png';
						}
						else
						{
						$user['user_data'][$j]['photo']='http://genmeds.webmasterssurat.com/uploads/user_images/'.$key->image;
						}
						
                               
				$j++;
			}
			$user['status'] = '200';
			$user['message'] = 'Login SuccessFully';
			echo json_encode($user); 	

		 }
	}
}
