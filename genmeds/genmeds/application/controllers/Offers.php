<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Offers extends CI_Controller 
{
	function __construct()
	{
		parent::__construct();
		$this->load->model('Crude_model');
	}

	public function ofr($limit='',$offset='')
	{
		$userdata = $this->Crude_model->select('offers');
		if($userdata)
		{
					$j=0;
					foreach ($userdata as $key)
					{
						$arr['offer_data'][$j]['id']=$key->id;
						$arr['offer_data'][$j]['name']=$key->name;
						$arr['offer_data'][$j]['description']=$key->description;
						$arr['offer_data'][$j]['amount']=$key->amount;
						$arr['offer_data'][$j]['status']=$key->status;
						// $arr['offer_data'][$j]['description']=$key->description;
						$j++;
					}
				$arr['status']='200';
				$arr['message'] ='Data Successfully retrived Of Offors...';
			}

				else
			{
				$arr['status']='404';
				$arr['message'] ='Error : Check your Data...';

			}
		echo json_encode($arr);
	}
}
