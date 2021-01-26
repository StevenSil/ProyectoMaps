<?php
require_once '../DbOperation.php';

switch ($_POST['opcion']) {
    case 'consultar':
        $db = new DbOperation();
        $datos=$db->ListarAnuncios();
        echo json_encode($datos);
    break;
    
    case 'insertar':
        $db = new DbOperation();

        $response = array(); 

        if (empty($_POST['Id_usuario']) || empty($_POST['Descripcion']) || empty($_POST['Foto']) || empty($_POST['Titulo']) || empty($_POST['Direccion']) || empty($_POST['Tipo_anuncio']) || empty($_POST['Longitud']) || empty($_POST['Latitud']) || empty($_POST['Fecha'])) {
            $response['error'] = true; 
            $response['message'] = 'Debe incluir todos los datos';
      
        } else {
            if (isset($_POST['Id_usuario']) || isset($_POST['Titulo']) || isset($_POST['Descripcion']) || empty($_POST['Foto']) || isset($_POST['Direccion']) || isset($_POST['Tipo_anuncio']) || isset($_FILES['Foto']) || isset($_POST['Longitud']) || isset($_POST['Latitud']) || isset($_POST['Fecha'])) {
               $db = new DbOperation(); 
               if($db->nuevaAnuncio($_POST['Id_usuario'], $_POST['Titulo'], $_POST['Descripcion'], $_POST['Direccion'], $_POST['Tipo_anuncio'], $_POST['Foto'], $_POST['Longitud'], $_POST['Latitud'], $_POST['Fecha'])){
                  $response['error'] = false;
                  $response['message'] = 'Anuncio agregado con exito';
               }else{
                  $response['error'] = true;
                  $response['message'] = 'No se pudo agregar el anuncio';
               }	
            }else{
               $response['error'] = true; 
               $response['message'] = 'Invalido Request';
            }
      
        }
         echo json_encode($response);
        
    break;

}
