<?php 
	
	//adding dboperation file 
	require_once '../DbOperation.php';
	
	//response array 
   $response = array(); 
   
   //if it is getUser that means we are fetching the records
	$db = new DbOperation();
   if (isset($_POST['user']) && isset($_POST['password'])) {
      $datos= $db->logIn($_POST['user'], $_POST['password']);
      if ($datos['status']) {
         $response['error'] = false; 
         $response['message'] = 'Inicio de sesion correcto.';
         $response['id_user'] = $datos['user_id'];
      } else {
         $response['error'] = true; 
         $response['message'] = 'Credenciales incorrectas.';
      }
   } else {
      $response['error'] = true; 
      $response['message'] = 'Debe ingresar todos los campos.';
   }
   
	//displaying the data in json 
   echo json_encode($response);
?>