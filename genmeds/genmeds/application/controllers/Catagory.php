<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Catagory extends CI_Controller 
{
	function __construct()
	{
		parent::__construct();
		$this->load->model('Crude_model');
	}

	public function Catagorydata()
	{
	
                        $directory="http://genmeds.webmasterssurat.com/uploads/category_images/";
       $this->db->order_by('order_id','ASC');	
			$data=$this->db->get_where('category',array('parent_id'=>0))->result();
			if($data!=="")
			{
				$j=0;
					foreach ($data as $key)
					{
						$arr['Category_data'][$j]['category_id']=$key->category_id;
						$arr['Category_data'][$j]['category_name']=$key->category_name;
						$arr['Category_data'][$j]['images']=$directory.$key->image;	
                                              
						$tid=$key->category_id;
                                           
						$tvar=$this->db->get_where('category',array('parent_id'=>$tid))->result();
						
						$count=sizeof($tvar);
						unset($jk);
						$k=1;
						$b=[];
						$c=[];
						foreach ($tvar as $key2) 
						{
							$b['sub_category_id']=$key2->category_id;
							$b['sub_category_name']=$key2->category_name;
							$k++;
							$c[]=$b;
						}

						$arr['Category_data'][$j]['total_category']=$count;
						@$arr['Category_data'][$j]['sub_category']=$c;
						$j++;
					}
					$arr['status']='200';
					$arr['message'] ='Data Successfully retrived...';
					
			 }
			else
			{
				$arr['status']='404';
				$arr['message'] ='No data Found';
			}
			echo json_encode($arr);
			
		
	}
	
}




					