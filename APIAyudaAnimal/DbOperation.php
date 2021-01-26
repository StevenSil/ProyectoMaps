<?php
 
class DbOperation
{
    private $con;

    public function __construct()
    {
        require_once dirname(__FILE__) . '/DbConnect.php';
        $db = new DbConnect();
        $this->con = $db->connect();
    }

    public function insertUser($Nombre, $Apellido, $Edad, $Telefono, $Direccion, $Usuario, $Contrasena)
    {
        $stmt = $this->con->prepare("INSERT INTO usuarios(Nombre, Apellido, Edad, Telefono, Direccion, Usuario, Contrasena) VALUES (?, ?, ?, ?, ?, ?, ?)");
        $stmt->bind_param("ssissss", $Nombre, $Apellido, $Edad, $Telefono, $Direccion, $Usuario, $Contrasena);
        if ($stmt->execute()) {
            return true;
        }
        return false;
    }

    //fetching all records from the database
    public function getUser()
    {
        $stmt = $this->con->prepare("SELECT Id, Nombres, Correo, Start FROM comment");
        $stmt->execute();
        $stmt->bind_result($Id, $Nombres, $Correo, $Start);
        $artists = array();
        
        while ($stmt->fetch()) {
            $temp = array();
            $temp['Id'] = $Id;
            $temp['name'] = $Nombres;
            $temp['comment'] = $Correo;
            $temp['start'] = $Start;
            array_push($artists, $temp);
        }
        return $artists;
    }
    #endregion



    #region seccion de login
    public function logIn($username, $password)
    {
        $login = array(); 
        $this->sql = "select * from usuarios where Usuario = '" . $username . "'";
        $result = mysqli_query($this->con, $this->sql);
        $row = mysqli_fetch_assoc($result);
        if (mysqli_num_rows($result) != 0) {
            $dbusername = $row['Usuario'];
            $dbpassword = $row['Contrasena'];
            $user_id = $row['Id'];
            if ($dbusername == $username && $password === $dbpassword) {
                $login['status'] = true; 
                $login['user_id'] = $user_id;
            } else {
                $login['status'] = false; 
                $login['user_id'] = "null";
            }
        } else {
            $login['status'] = false; 
            $login['user_id'] = "null";
        }
        return $login;
    }
    #endregion


    #region operaciones de ordenes

    public function ListarAnuncios()
    {
        $stmt = $this->con->prepare("SELECT id_usuario, titulo, descripcion, tipo_anuncio, direccion, foto, longitud, latitud, fecha Start FROM anuncio");
        $stmt->execute();
        $stmt->bind_result($id_usuario, $titulo, $descripcion, $tipo_anuncio, $direccion, $server_rute, $longitud, $latitud, $fecha);
        $anuncios = array();
      
        while ($stmt->fetch()) {
            $temp = array();
            $temp['id_usuario'] = $id_usuario;
            $temp['titulo'] = $titulo;
            $temp['descripcion'] = $descripcion;
            $temp['tipo_anuncio'] = $tipo_anuncio;
            $temp['direccion'] = $direccion;
            $temp['foto'] = $server_rute;
            $temp['longitud'] = $longitud;
            $temp['latitud'] = $latitud;
            $temp['fecha'] = $fecha;
      
            array_push($anuncios, $temp);
        }
        return $anuncios;
    }

    public function ListarAnunciosPorUsuario($id_usuario)
    {
        $stmt = $this->con->prepare("SELECT id_usuario, titulo, descripcion, tipo_anuncio, direccion, foto, longitud, latitud, fecha Start FROM anuncio WHERE id_usuario='" . $id_usuario . "'");
        $stmt->execute();
        $stmt->bind_result($id_usuario, $titulo, $descripcion, $tipo_anuncio, $direccion, $server_rute, $longitud, $latitud, $fecha);
        $anuncios = array();
      
        while ($stmt->fetch()) {
            $temp = array();
            $temp['id_usuario'] = $id_usuario;
            $temp['titulo'] = $titulo;
            $temp['descripcion'] = $descripcion;
            $temp['tipo_anuncio'] = $tipo_anuncio;
            $temp['direccion'] = $direccion;
            $temp['foto'] = $server_rute;
            $temp['longitud'] = $longitud;
            $temp['latitud'] = $latitud;
            $temp['fecha'] = $fecha;
      
            array_push($anuncios, $temp);
        }
        return $anuncios;
    }


    public function nuevaAnuncio($id_usuario, $titulo, $descripcion, $direccion, $tipo_anuncio, $foto, $longitud, $latitud, $fecha)
    {

        $nuevo_anuncio_status = 1;
    
        $id = uniqid();

        $server_rute = "images/" . $id . '.png';

        $save_rute = dirname(__FILE__) . "\images\\" . $id . '.png' ;

        file_put_contents($save_rute, base64_decode($foto)); 
   
        $stmt = $this->con->prepare("INSERT INTO anuncio(id_usuario, titulo, descripcion, tipo_anuncio, direccion, foto, longitud, latitud, fecha, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        $stmt->bind_param("ississsssi", $id_usuario, $titulo, $descripcion, $tipo_anuncio, $direccion, $server_rute, $longitud, $latitud, $fecha, $nuevo_anuncio_status);
        if ($stmt->execute()) {
            return true;
        }
        return false;
    }

    #endregion


    #region operaciones de resultado
    public function ListarResultado()
    {
        $stmt = $this->con->prepare("SELECT Id, usuario,coordenadaX , coordenadaY ,fechaActual,descripcion,estado	Start FROM ordenes");
        $stmt->execute();
        $stmt->bind_result($Id, $usuario, $coordenadaX, $coordenadaY, $fechaActual, $descripcion, $estado);
        $artists = array();
  
        while ($stmt->fetch()) {
            $temp = array();
            $temp['Id'] = $Id;
            $temp['usuario'] = $usuario;
            $temp['coordenadaX'] = $coordenadaX;
            $temp['coordenadaY'] = $coordenadaY;
            $temp['fechaActual'] = $fechaActual;
            $temp['descripcion'] = $descripcion;
            $temp['estado'] = $estado;
  
            array_push($artists, $temp);
        }
        return $artists;
    }
    public function NuevoResultado($datos)
    {
        $stmt = $this->con->prepare("INSERT INTO resultado(fkordern, 	descripcion1, descripcion2	, descripcion3,foto) VALUES (?, ?, ?, ?,?)");
        $stmt->bind_param("issss", $datos['fkordern'], $datos['descripcion1'], $datos['descripcion2'], $datos['descripcion3'], $datos['foto']);
        if ($stmt->execute()) {
            return true;
        }
        return false;
    }
    #endregion


    #region operaciones de gparam
    public function gparam($grupo)
    {
        $stmt = $this->con->prepare("SELECT id, displayname,valor	Start FROM gparam WHERE grupo='" . $grupo . "'");
        $stmt->execute();
        $stmt->bind_result($id, $displayname, $valor);
        $artists = array();
  
        while ($stmt->fetch()) {
            $temp = array();
            $temp['id'] = $id;
            $temp['displayname'] = $displayname;
            $temp['valor'] = $valor;
            array_push($artists, $temp);
        }
        return $artists;
    }
    #endregion
}
