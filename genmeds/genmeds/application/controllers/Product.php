<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Product extends CI_Controller 
{
	function __construct()
	{
		parent::__construct();
		$this->load->model('Crude_model');
error_reporting(0);
	}

	public function productdata($limit='',$offset='')
	{
		
		if($this->input->post('product_id'))
		{
			if (!$this->Crude_model->select('product_detail',array('product_id'=>$this->input->post('product_id'))))
			{
				$arr['status']='404';
				$arr['message'] ='please check data';
			}
			else
			{
				$directory = "genmeds/uploads/product_image/";
				$image="product_";
				$pid=$this->input->post('product_id');
				$userdata=$this->Crude_model->select_product('product_detail',array('product_id'=>$this->input->post('product_id')));
					$j=0;
					foreach ($userdata as $key)
					{
						$arr['product_data'][$j]['product_id']=$key->product_id;
						$arr['product_data'][$j]['title']=$key->title;
						$arr['product_data'][$j]['sale_price']=$key->sale_price;
						$arr['product_data'][$j]['purchase_price']=$key->purchase_price;
						$arr['product_data'][$j]['num_of_imgs']=$key->num_of_imgs;
						$arr['product_data'][$j]['discount']=$key->discount;
						$arr['product_data'][$j]['number_of_view']=$key->number_of_view;

						$arr['product_data'][$j]['rating_num']=$key->rating_num;
						$arr['product_data'][$j]['rating_total']=$key->rating_total;
						$arr['product_data'][$j]['discount_type']=$key->discount_type;
						
						$arr['product_data'][$j]['unit']=$key->unit;
						$arr['product_data'][$j]['add_timestamp']=$key->add_timestamp;
						$col=$key->color;
						$temph=json_decode($col);
						if(is_array($temph))
						{
						$templ=implode(",",$temph);
						}
						else
						{
							$templ='';
						}
						$arr['product_data'][$j]['color']=$templ;

						$pid=$key->product_id;
						$count=$key->num_of_imgs;
						unset($jk);
						for($k=1;$k<=$count;$k++)
						{
							$jk[$k]=$directory."product_".$pid."_".$k."_thumb".".jpg";
						}
						$count=0;
						$arr['product_data'][$j]['images']=$jk;
						$j++;
					}
					

				$arr['status']='200';
				$arr['message'] ='Data Successfully retrived...';
			}
			echo json_encode($arr);
		}
		else{
				
			if($this->input->post('category_id'))
			{		
				$cc=$this->input->post('category_id');
		        
				$directory = "genmeds/uploads/product_image/";
				$userdata=$this->Crude_model->select('product_detail',array('category_id'=>$cc));
				
				if(empty($userdata))
				{
				$arr['status']='404';
				$arr['message'] ='Data can not Found...';
				}
				else
				{
					$j=0;
                    $directory = "http://genmeds.webmasterssurat.com/admin/";
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

	                    $imagedata=$this->Crude_model->select_where('product_image',array('product_id'=>$key->product_id));
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
			echo json_encode($arr);
		}
		else
		{
			$directory = "genmeds/uploads/product_image/";
			$userdata=$this->Crude_model->select('product_detail','',$limit,$offset);
				
			$j=0;
                        $directory = "http://genmeds.webmasterssurat.com/admin/";
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
				
                $imagedata=$this->Crude_model->select_where('product_image',array('product_id'=>$key->product_id));
				foreach ($imagedata as $keyimage)
				{
					$productimage=$keyimage->image;
					$productcolorid=$keyimage->color_id;
				}
				$arr['product_data'][$j]['product_image']=$directory.$productimage;
				$colordata=$this->Crude_model->select_where('product_color',array('color_id'=>$productcolorid));
				foreach ($colordata as $keycolor)
				{
					$productcolor=$keycolor->color_code;
					
				}
				$arr['product_data'][$j]['product_color']=$productcolor;
				$j++;
			}
			
			$arr['status']='200';
			$arr['message'] ='Data Successfully retrived...';
			echo json_encode($arr);

			}
		}
	}
}
