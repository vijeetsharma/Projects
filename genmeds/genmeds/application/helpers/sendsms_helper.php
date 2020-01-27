<?php
/**
 * CodeIgniter
 *
 * An open source application development framework for PHP
 *
 * This content is released under the MIT License (MIT)
 *
 * Copyright (c) 2014 - 2015, British Columbia Institute of Technology
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software ish
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @package    CodeIgniter
 * @author    EllisLab Dev Team
 * @copyright    Copyright (c) 2008 - 2014, EllisLab, Inc. (http://ellislab.com/)
 * @copyright    Copyright (c) 2014 - 2015, British Columbia Institute of Technology (http://bcit.ca/)
 * @license    http://opensource.org/licenses/MIT    MIT License
 * @link    http://codeigniter.com
 * @since    Version 1.0.0
 * @filesource
 */
defined('BASEPATH') OR exit('No direct script access allowed');
if ( ! function_exists('sendsms'))
{

 function sendsms($number, $message_body, $return = '0') {
         $encoded_message = urlencode($message_body);

              $smsgatewaydata="http://login.arihantsms.com/vendorsms/pushsms.aspx?user=viataxi&password=viataxiadmin&msisdn=91$number&sid=VIATXI&msg=$encoded_message&fl=0&gwid=2";
              $url = $smsgatewaydata;  
              $ch = curl_init();
              curl_setopt($ch, CURLOPT_POST, false);
              curl_setopt($ch, CURLOPT_URL, $url);
              curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); $output = curl_exec($ch);
              curl_close($ch);
              $opciones = array(
              'http'=>array(
              'method'=>"GET",
              'header'=>"Accept-language: en\r\n" .
                        "Cookie: foo=bar\r\n"
            )
          );
//$contexto = stream_context_create($opciones);
    if(!$output)
      {  
        $output = file_get_contents($smsgatewaydata,false);        }
        $result=json_decode($output,true);

        if ($result['ErrorCode']=='000')
        {
           return json_encode($result); 
        }
        else
        {
           return $json='{"ErrorCode":"13","ErrorMessage":"Invalid mobile numbers"}';
        }
        // if () {
        //   # code...
        // }
          //   if($return == '1')
          //   { 
          //     return $output; 
          //   }
          //   else
          //   {
          //      echo $output; 
          //   }
          }
}