<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Notification extends CI_Controller 
{
	function __construct()
	{
		parent::__construct();
		$this->load->model('Crude_model');
	}

	public function ndata($limit='',$offset='')
	{
		
		$userdata=$this->Crude_model->select('notification');
			if($userdata)
			{
						$j=0;
					foreach ($userdata as $key)
					{
						$arr['notification_data'][$j]['id']=$key->id;
						$arr['notification_data'][$j]['user_id']=$key->user_id;
						$arr['notification_data'][$j]['title']=$key->title;
						$arr['notification_data'][$j]['description']=$key->description;
						$arr['notification_data'][$j]['time']=$key->time;
						$j++;
					}
				$arr['status']='200';
				$arr['message'] ='Data Successfully retrived Of Notification...';
			}
			else
			{
				$arr['status']='404';
				$arr['message'] ='Error : Check your Data...';

			}
		echo json_encode($arr);
	}
}
