<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Search extends CI_Controller 
{
	function __construct()
	{
		parent::__construct();
		$this->load->model('Crude_model');
	}
	public function searchdata()
	{
                $directory = "http://genmeds.webmasterssurat.com/admin/";
		if($this->input->post('category'))
		{
			$category = $this->input->post('category');
			if (!$this->Crude_model->category_search($category))
			{ 
				$arr['status']='404';
				$arr['message'] ='Error:please check data';
			}
			else
			{
				$categorydata= $this->Crude_model->category_search($category);
				if($categorydata)
				{
				$i=0;
                                $j=0;
				foreach ($categorydata as $keycat)
					{
						$arr['product_data'][$j]['category_id']=$keycat->category_id;



                                               $userdata = $this->db->get_where('product_detail',array('category_id'=>$keycat->category_id))->result();
				if($userdata)
				{
				$j=0;
				foreach ($userdata as $key)
					{
						$arr['product_data'][$j]['product_id']=$key->product_id;
						$arr['product_data'][$j]['p_id']=$key->p_id;
						$arr['product_data'][$j]['product_name']=$key->product_name;
						$arr['product_data'][$j]['selling_price']=$key->selling_price;
						$arr['product_data'][$j]['product_price']=$key->product_price;
						$arr['product_data'][$j]['about_product']=$key->about_product;
						$arr['product_data'][$j]['febric']=$key->febric;
						$arr['product_data'][$j]['shipping_charge']=$key->shipping_charge;
						$arr['product_data'][$j]['work']=$key->work;
						$arr['product_data'][$j]['product_quantity']=$key->product_quantity;
						$arr['product_data'][$j]['category_id']=$key->category_id;
						$arr['product_data'][$j]['product_description']=$key->product_description;
                                                $arr['product_data'][$j]['discount']=0;
						$arr['product_data'][$j]['product_specification']=$key->product_specification;
						$arr['product_data'][$j]['product_status']=$key->product_status;
						


	                                       $imagedata=$this->Crude_model->select('product_image',array('product_id'=>$key->product_id));
						foreach ($imagedata as $keyimage)
						{
							$productimage=$keyimage->image;
							$productcolorid=$keyimage->color_id;
						}
						$arr['product_data'][$j]['product_image']=$directory.$productimage;
						$colordata=$this->Crude_model->select('product_color',array('color_id'=>$productcolorid));
						foreach ($colordata as $keycolor)
						{
							$productcolor=$keycolor->color_code;
							
						}
						$arr['product_data'][$j]['product_color']=$productcolor;

						$j++;
			 		}

					}	
						

						$i++;
					}

				$arr['status']='200';
				$arr['Message']='Message:Data retrived Successfully....';
			}
				else
			{
				$arr['status']='404';
				$arr['message'] ='Error : Check your Data...';

			}

			}
		}
		else
		{
			$product = $this->input->post('product_detail');
			$userdata = $this->Crude_model->product_search($product);
				if($userdata)
				{
				$j=0;
				foreach ($userdata as $key)
					{
						$arr['product_data'][$j]['product_id']=$key->product_id;
						$arr['product_data'][$j]['p_id']=$key->p_id;
						$arr['product_data'][$j]['product_name']=$key->product_name;
						$arr['product_data'][$j]['selling_price']=$key->selling_price;
						$arr['product_data'][$j]['product_price']=$key->product_price;
						$arr['product_data'][$j]['about_product']=$key->about_product;
						$arr['product_data'][$j]['febric']=$key->febric;
						$arr['product_data'][$j]['shipping_charge']=$key->shipping_charge;
						$arr['product_data'][$j]['work']=$key->work;
						$arr['product_data'][$j]['product_quantity']=$key->product_quantity;
						$arr['product_data'][$j]['category_id']=$key->category_id;
						$arr['product_data'][$j]['product_description']=$key->product_description;
                                                $arr['product_data'][$j]['discount']=0;
						$arr['product_data'][$j]['product_specification']=$key->product_specification;
						$arr['product_data'][$j]['product_status']=$key->product_status;
						


	                                       $imagedata=$this->Crude_model->select('product_image',array('product_id'=>$key->product_id));
						foreach ($imagedata as $keyimage)
						{
							$productimage=$keyimage->image;
							$productcolorid=$keyimage->color_id;
						}
						$arr['product_data'][$j]['product_image']=$directory.$productimage;
						$colordata=$this->Crude_model->select('product_color',array('color_id'=>$productcolorid));
						foreach ($colordata as $keycolor)
						{
							$productcolor=$keycolor->color_code;
							
						}
						$arr['product_data'][$j]['product_color']=$productcolor;

						$j++;
			 		}
				$arr['status']='200';
				$arr['message'] ='Data Successfully retrived...';
			}
			else
			{
				$arr['status']='404';
				$arr['message'] ='Error : Check your Data...';

			}

		}
		echo json_encode($arr);
			
		
	}
	
}
