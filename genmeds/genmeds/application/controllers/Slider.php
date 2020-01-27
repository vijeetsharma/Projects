<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Slider extends CI_Controller 
{
	function __construct()
	{
		parent::__construct();
		$this->load->model('Crude_model');
	}

	public function sliderdata($param='',$idd='')
	{

		$directory = "http://rentav.webmasterssurat.com/admin/";
			
		$slides=$this->Crude_model->select('slider','','');
		$k=0;
		foreach ($slides as $key)
		{
			
			$arr['slider_data'][$k]['img_id']=$key->img_id;										
			$arr['slider_data'][$k]['product_id']=$key->product_id;
			$arr['slider_data'][$k]['product_discription']=$key->product_discription;

			$kid=$key->image;
			$jk=$directory.$kid;
			
			$arr['slider_data'][$k]['images']=$jk;	
			
			$k++;
			
		}
		$arr['status']='200';
		$arr['message'] ='Data Successfully retrived...';
		echo json_encode($arr);

		
	}
	
	
}
