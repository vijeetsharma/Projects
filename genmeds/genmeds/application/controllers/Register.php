<?php
defined('BASEPATH') OR exit('No direct script access allowed');
class Register extends CI_Controller 
{
	function __construct()
	{
		parent::__construct();
		$this->load->model('Crude_model');
		
	}
	public function registerdata()
	{           
		$acc ='';
		$fname ='';
		$mob ='';
		$email ='';
		 
		
		if(!$this->input->post('username'))
		{
						$user['status'] = '404';
						$user['message'] = 'Error: Username Must Add.';
						echo json_encode($user);
						exit();
		} 
		if(!$this->input->post('email'))
		{
						$user['status'] = '404';
						$user['message'] = 'Error: Email Must Add.';
						echo json_encode($user);
						//echo $user;
						exit();

		}  
		if(!$this->input->post('mobile'))
		{
						$user['status'] = '404';
						$user['message'] = 'Error: Mobile Number Must Add.';
						echo json_encode($user);
						exit();
		}  
		
		if(!$this->input->post('password'))
		{
						$user['status'] = '404';
						$user['message'] = 'Error: password Must Add.';
						echo json_encode($user);
						exit();
		} 
		$em=$this->input->post('email');
		$mob=$this->input->post('mobile');
		
				if($this->Crude_model->check_email($em))
				{
							$user['status'] = '404';
							$user['message'] = 'Error: Email ID Already Exists.';
							echo json_encode($user);
				}
				else if($this->Crude_model->checkmobile($mob))
				{
							$user['status'] = '404';
							$user['message'] = 'Error: Contact Number Already Exists.';
							echo json_encode($user);
				}
				else
				{
							$fname = $this->input->post('username');
						    $email = $this->input->post('email');
						    $mob = $this->input->post('mobile');
						  	$pwd = $this->input->post('password');

							$datax=array('contact_number'   =>$mob,
							           	 'user_name'=>$fname,
							           	 'user_email'   =>$email,
							           	 'password'=>md5($pwd));
							
							if (!$this->Crude_model->insert_data('user_information',$datax))
								{
									$user['status'] = '404';
									$user['message'] = 'Error:please check data';	
								}
								else
								{
									$user['user_id'] = $this->db->insert_id();
									$user['status'] = '200';
									$user['message'] = 'Register SuccessFully';
								}
								echo json_encode($user);	
			    }
		
	}
	
}
