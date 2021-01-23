<?php
require_once '../DbOperation.php';


switch($_POST['opcion'])
{
    case 'consultar':
        $db = new DbOperation();
        $data = array();
		$datos=$db->ListarOrdenes();
		echo json_encode($datos);
	break;
	
    case 'insertar':
        $db = new DbOperation();
        $datos['usuario'] = $_POST['usuario'];
        $datos['tipoAnimal'] = $_POST['tipoAnimal'];
        $datos['coordenadaX'] = $_POST['coordenadaX'];
        $datos['coordenadaY'] = $_POST['coordenadaY'];
        $datos['fechaActual'] = $_POST['fechaActual'];
        $datos['descripcion'] = $_POST['descripcion'];
        $datos['estado'] = $_POST['estado'];
        
        
			if($db->nuevoOrden($datos)){
				echo json_encode("Registro ingresado");
			}else{
				echo json_encode("Error al registrar");
            }
		
		break;
}


?>