<?php 
	
	//adding dboperation file 
	require_once '../DbOperation.php';
	
	//response array 
	$response = array(); 
	
	if (empty($_POST['Nombre']) || empty($_POST['Apellido']) || empty($_POST['Direccion']) || empty($_POST['Contrasena']) || empty($_POST['Usuario']) || empty($_POST['Edad']) || empty($_POST['Telefono']) ) {
      $response['error'] = true; 
      $response['message'] = 'Debe incluir todos los datos';

   } else {
      if (isset($_POST['Nombre']) || isset($_POST['Apellido']) || isset($_POST['Direccion']) || isset($_POST['Contrasena']) || isset($_POST['Usuario']) || isset($_POST['Edad']) || isset($_POST['Telefono'])) {
         $db = new DbOperation(); 
         if($db->insertUser($_POST['Nombre'], $_POST['Apellido'], $_POST['Direccion'], $_POST['Contrasena'], $_POST['Usuario'], $_POST['Edad'], $_POST['Telefono'])){
            $response['error'] = false;
            $response['message'] = 'Usuario agregado con exito';
         }else{
            $response['error'] = true;
            $response['message'] = 'No se puede agregar el usuario';
         }	
      }else{
         $response['error'] = true; 
         $response['message'] = 'Invalido Request';
      }

   }
	
	//displaying the data in json 
   echo json_encode($response);
?>