<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Myorder extends CI_Controller 
{
	function __construct()
	{
		parent::__construct();
		$this->load->model('Crude_model');
	}

	public function odata($limit='',$offset='')
	{

		if($this->input->post('user_id'))
		{
			$uid=$this->input->post('user_id');
                        $this->db->order_by("booking_id", "desc");

			$userdata=$this->db->get_where('booking',array('user_id'=>$uid))->result();
			if($userdata)
			{
					$j=0;
					foreach ($userdata as $key)
					{
						$arr['booking_data'][$j]['user_id']=$key->user_id;
						$arr['booking_data'][$j]['order_id']=$key->order_id;
						$arr['booking_data'][$j]['address']=$key->address;
						$arr['booking_data'][$j]['offer_apply']=$key->offer_apply;
						$arr['booking_data'][$j]['offer_code']=$key->offer_code;
						$arr['booking_data'][$j]['total_amount']=$key->total_amount;
						$arr['booking_data'][$j]['status']=$key->status;
						$arr['booking_data'][$j]['created_date']=$key->created_date;


						$cart = $this->db->get_where('cart',array('order_id'=>$key->order_id))->result();
						$count=sizeof($cart);
						unset($jk);
						$k=1;
						$b=[];
						$c=[];
						 $directory = "http://genmeds.webmasterssurat.com/admin/";
						foreach ($cart as $key2) 
						{
							$pname='';
							$b['product_id']=$key2->product_id;
							$kmkm = $this->db->get_where('product_detail',array('product_id'=>$key2->product_id))->result();

							foreach ($kmkm as $keym) 
							{    
								$pname = $keym->product_name; 
                                                                $imagedata=$this->Crude_model->select('product_image',array('product_id'=>$key2->product_id));
						                foreach ($imagedata as $keyimage)
						                {
							            $productimage=$keyimage->image;
						                }
						                $product_image=$directory.$productimage; 
							}

						        $b['product_name'] = $pname;
                                                        $b['product_image'] = $product_image;
							$b['quantity'] = $key2->quantity;
							$b['price'] = $key2->price;
							$k++;
							$c[]=$b;
						}

						//$arr['booking_data'][$j]['total_cate']=$count;
						@$arr['booking_data'][$j]['cart_data']=$c;

						$j++;
					}
				$arr['status']='200';
				$arr['message'] ='Data Successfully retrived...';		
		       echo json_encode($arr);

		       }
			else
			{
				$arr['status']='404';
				$arr['message'] ='Error : Check your Data...';

			}

		}
		else
		{
			 $arr['status']='404';
		 	 $arr['message'] ='Must Enter user ID';
		 	 echo json_encode($arr);
		}
}
}
