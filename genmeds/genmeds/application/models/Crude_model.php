<?php 
	/**
	* common model
	*/
	class Crude_model extends CI_Model
	{
		
		function __construct()
		{
			parent::__construct();
		}
                function product_search($product)
		{
			 $this->db->select('*');
			 $this->db->like('product_name',$product);                         
			 $query=$this->db->get('product_detail');
			
			return  $query->result_object();
		}
function category_search($product)
		{
			 $this->db->select('*');
			 $this->db->like('category_name',$product);                         
			 $query=$this->db->get('category');
			
			return  $query->result_object();
		}
		function select_where($table,$where='',$limit='',$offset='')
		{
			if ($where!=="")
			{
				return $this->db->get_where($table,$where,$limit,$offset)->result();
			}
			else
			{
				return $this->db->get($table,$limit,$offset)->result();	
			}
		}
		function select($table,$where='',$limit='',$offset='')
		{
			if ($where!=="")
			{
				return $this->db->get_where($table,$where,$limit,$offset)->result();
			}
			else
			{
				return $this->db->get($table,$limit,$offset)->result();	
			}
		}


		function select_login($table,$email,$pwd)
		{
	        $this->db->select('*');
	        $this->db->from($table);
	        $this->db->where('password',md5($pwd));
		    $this->db->where("(user_email = '$email' OR contact_number = '$email')");
		   $this ->db-> limit(1);
	        $query = $this->db->get();
			return $query->result_object();	    
		}

		function select_login1($table,$email,$pwd)
		{
	        $this->db->select('*');
	        $this->db->from($table);
	        $this->db->where('password',md5($pwd));
		    $this->db->where("(email = '$email' OR contact = '$email')");
		   $this ->db-> limit(1);
	        $query = $this->db->get();
			return $query->result_object();	    
		}

	
	 function update_where($table,$data='',$user_id)
		{
		    $field=$this->db->field_data($table);
			$tid=$field[0]->name;
			$datak=array($tid=>$user_id); 
			 $this->db->where($datak); 
			    $res=$this->db->update($table,$data);
					if($res)
					{ 
						return true;
					}
					else
					{
						return false;
					}
		}
		function update_pwd($table,$data='',$user_id)
		{
			 $this->db->where('user_id',$user_id); 
			    $res=$this->db->update($table,$data);
					if($res)
					{ 
						return true;
					}

					else
					{
						return false;
					}
		}
	function select1($table,$where='',$limit='',$offset='')
		{
			if ($where!=="")
			{
				 $this->db->select('*');
				  $this->db->order_by('product_id', 'DESC');  
				  $this->db->from($table);
				  $this->db->limit('5');
				  $this->db->where($where);
				  $query = $this->db->get();
				 return $query->result();

				//return $this->db->get_where($table,$where,$limit,$offset)->result();
			}
			else
			{
				return $this->db->get($table,$limit,$offset)->result();	
			}
		}



function select_otp($table,$mob,$otp)
		{
		    $this->db->where(array('otp'=>$otp));
            $this->db->update($table,array('status' => 'updated'));
	        $this->db->select('*');
	        $this->db->from($table);
		    $this->db->where("(mobile_no = '$mob' AND otp = '$otp')");
		   $this -> db -> limit(1);
	        $query = $this->db->get();
			return $query->result_object();	    
		}

		

		function delete_data($table,$where)
		{
			 return  $this->db->delete($table,$where);
		}

		// function select_login($table,$email,$pwd)
		// {
	 //        $this->db->select('*');
	 //        $this->db->from($table);
	 //        $this->db->where('password',md5($pwd));  
		//     $this->db->where('email',$email);
		//       $this->db->or_where('contact',$email);
	 //        $query = $this->db->get();
		// 	return $query->result_object();	    
		// }
		
	 
 

    function update_otp($otp,$mobile)
    {
        $this->db->where('phone',$mobile);
        $this->db->update('user',array('otp' => $otp));
       return ($this->db->affected_rows() > 0) ? TRUE : FALSE;  
        
    }

    function updatepwd($new_password,$otp)
    {
    	$password1=sha1($new_password);
		  $this->db
               ->where('otp', $otp)
               ->update("user", array('password' => $password1));
				return ($this->db->affected_rows() > 0) ? TRUE : FALSE; 

    }

    function insert_data($table,$data)
		{
			return $this->db->insert($table,$data);
		}

    function insert_where($table,$data,$uid)
		{

	        $this->db->where('user_id',$uid); 
			return $this->db->insert($table,$data);
		}
		function checkmobile($mobile)
		{

	        $this->db->select('*');
	        $this->db->from('user_information');
	        $this->db->where('contact_number',$mobile); 
	        $query=$this->db->get()->result_object();
	        if(count($query)>0)
	        {
	        	return true;
	        }
	        else
	        {
	        	return false;
	        }
		}

		function check_email($em)
		{

	        $this->db->select('*');
	        $this->db->from('user_information');
	        $this->db->where('user_email',$em); 
	        $query=$this->db->get()->result_object();
	        if(count($query)>0)
	        {
	        	return true;
	        }
	        else
	        {
	        	return false;
	        }
		}
		function checkpwd($pwd,$uid)
		{  
			$this->db->select('*');
	        $this->db->from('user_information');
	        $this->db->where('user_id',$uid);
	        $this->db->where('password',md5($pwd)); 
	        $query=$this->db->get()->result_object();
	        if(count($query)>0)
	        {
	        	return true;
	        }
	        else
	        {
	        	return false;
	        }
		}

		function check_order($oid)
		{  
			$this->db->select('*');
	        $this->db->from('booking');
	        $this->db->where('ref_id',$oid);
	        $query=$this->db->get()->result_object();
	        if(count($query)>0)
	        {
	        	return true;
	        }
	        else
	        {
	        	return false;
	        }
		}
		
		/*function product_search($product)
		{
			$this->db->select('*');
			 $this->db->like('name',$product);
			 $query=$this->db->get('sub_services');
			
			return  $query->result_object();
		}*/
		function checkpassword($pwd)
		{

	        $this->db->select('*');
	        $this->db->from('user');
	        $this->db->where('password',$pwd); 
	        $query=$this->db->get()->result_object();
	        if(count($query)>0)
	        {
	        	return true;
	        }
	        else
	        {
	        	return false;
	        }
		}
	}

 ?>