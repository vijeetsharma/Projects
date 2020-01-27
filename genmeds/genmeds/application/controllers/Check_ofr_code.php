<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Check_ofr_code extends CI_Controller 
{
	function __construct()
	{
		parent::__construct();
		$this->load->model('Crude_model');
	}

	public function odata($limit='',$offset='')
	{

		if($this->input->post('offer_code'))
		{
			$oid=$this->input->post('offer_code');
			$ofr=$this->db->get_where('offers',array('name'=>$oid))->result();

			if(sizeof($ofr)>0)
			{
				foreach ($ofr as $keyo) 
				{
					$arr['amount']=$keyo->amount;
				}
				$arr['status']='200';
			 	 $arr['message'] ='Data Successfully retrived Of Offers data...';
			 	 echo json_encode($arr);
			}
			else
			{
				 $arr['status']='404';
			 	 $arr['message'] ='Not Exist This Offer Code';
			 	 echo json_encode($arr);
			}
		}
		else
		{
			 $arr['status']='404';
		 	 $arr['message'] ='Must Enter Offer Code';
		 	 echo json_encode($arr);
		}
}
}
