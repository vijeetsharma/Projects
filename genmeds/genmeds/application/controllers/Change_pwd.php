<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Change_pwd extends CI_Controller 
{
	function __construct()
	{
		parent::__construct();
		$this->load->model('Crude_model');
	}
	
	public function pdata()
	{
		if(!$this->input->post('user_id'))
		{
						$user['status'] = '404';
						$user['message'] = 'Error: User ID Must Add.';
						echo json_encode($user);
						exit();
		}  
		if(!$this->input->post('old_password'))
		{
						$user['status'] = '404';
						$user['message'] = 'Error: Old Password Must Add.';
						echo json_encode($user);
						exit();
		}  
		if(!$this->input->post('new_password'))
		{
						$user['status'] = '404';
						$user['message'] = 'Error: New Password Must Add.';
						echo json_encode($user);
						exit();
		} 
		
	     $old_pwd = $this->input->post('old_password');
	     $uid = $this->input->post('user_id');

		if(!$this->Crude_model->checkpwd($old_pwd,$uid))
		{
			$user['status'] = '404';
			$user['message'] = 'Error:  Password is not Exists.';
			echo json_encode($user);
		}

		else 
		{
			$new_pwd = $this->input->post('new_password');

			{
				$this->db->where('user_id',$uid);
				
				if($this->db->update('user_information',array('password'=>md5($new_pwd))))
				{
		         	$user['status'] = '200';
					$user['message'] = 'success:  Successfully Set Your Password.';
					echo json_encode($user);

				}
				else
				{
			        $user['status'] = '404';
					$user['message'] = 'Error:  Check Your Data.';
					echo json_encode($user);
				}
			}
		}
	}		
}
