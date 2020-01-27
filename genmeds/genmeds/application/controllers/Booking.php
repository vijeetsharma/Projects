<?php
defined('BASEPATH') OR exit('No direct script access allowed');
class Booking extends CI_Controller 
{
	function __construct()
	{
		parent::__construct();
		$this->load->model('Crude_model');
	}
	public function bdata()
	{           
		 
		if(!$this->input->get('user_id'))
		{
						$user['status']='404';
						$user['message'] ='Error: user_id Must Add.';
						echo json_encode($user);
						exit();
		}  
		// if(!$this->input->get('order_id'))
		// {
		// 				$user['status']='404';
		// 				$user['message'] ='Error: order_id Must Add.';
		// 				echo json_encode($user);
		// 				exit();
		// } 
		 if(!$this->input->get('address'))
		{
						$user['status']='404';
						$user['message'] ='Error: address Must Add.';
						echo json_encode($user);
						exit();
		}  
		if(!$this->input->get('total_amount'))
		{
						$user['status']='404';
						$user['message'] ='Error: total_amount Must Add.';
						echo json_encode($user);
						exit();
		}  
		// if(!$this->input->get('item_length'))
		// {
		// 				$user['status']='404';
		// 				$user['message'] ='Error: item_length Must Add.';
		// 				echo json_encode($user);
		// 				exit();
		// } 
		
		if($this->input->get('offer_apply'))
		{  $ofr_apy= $this->input->get('offer_apply'); }
	    else{  $ofr_apy= '';   }

	    	if($this->input->get('offer_code'))
		{  $ofr_code= $this->input->get('offer_code'); }
	    else{  $ofr_code= '';   }
	    
	    	// $bid = $this->input->get('booking_id');
			$uid=$this->input->get('user_id');
			$oid=$this->input->get('order_id');
			$address=$this->input->get('address');
			$final_tot=$this->input->get('total_amount');
			// $book_dt=$this->input->get('booking_date');
			// $book_tm=$this->input->get('booking_time');
			 $order_id=rand(11111,99999);
			
							$datax=array('user_id'=>$uid,
										 'order_id'=>$order_id,
							           	 'address'=>$address,
							           	 'offer_apply'=>$ofr_apy,
							           	 'offer_code'=>$ofr_code,
							           	 'total_amount'=>$final_tot,
							            );

						   	if (!$this->Crude_model->insert_data('booking',$datax))
								{
									$user['status']='404';
									$user['message'] ='Error:please check data';	
								}
								else
								{
								 	$len = $this->input->get('item_length');
								 	
									for($i=1;$i<=$len;$i++)
									{
																			 
									$arr = array('order_id'=>$order_id,
												  'user_id'=>$uid,
												  // 'booking_id'=>$bid,
												  'product_id'=>$this->input->get('product_id'.$i),
												  'quantity'=>$this->input->get('quantity'.$i),
												  'price' => $this->input->get('price'.$i)
												  );

										$this->db->insert('cart',$arr);
									}
									$user['status']='200';
									$user['order_id']=$order_id;
									$user['message'] ='Booking SuccessFully';
								}
								echo json_encode($user);	
			    
			
		
		
	}
	
}
