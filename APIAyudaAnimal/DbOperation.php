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

    #region operaciones de usuario
    //adding a record to database
    public function insertUser($Usuario, $Contraseña)
    {
        $stmt = $this->con->prepare("INSERT INTO Usuarios(Nombres, Apellidos, Edad, Telefono, Dirección, Usuario, Contrasena) VALUES (?, ?, ?, ?, ?, ?, ?)");
        $stmt->bind_param("ss", $Usuario, $Contraseña);
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
        $this->sql = "select * from usuarios where Usuario = '" . $username . "'";
        $result = mysqli_query($this->con, $this->sql);
        $row = mysqli_fetch_assoc($result);
        if (mysqli_num_rows($result) != 0) {
            $dbusername = $row['Usuario'];
            $dbpassword = $row['Contrasena'];
            if ($dbusername == $username && $password === $dbpassword) {
                $login = true;
            } else {
                $login = false;
            }
        } else {
            $login = false;
        }
        return $login;
    }
     #endregion



   #region operaciones de ordenes

    public function ListarOrdenes(){
		$stmt = $this->con->prepare("SELECT Id, usuario,coordenadaX , coordenadaY ,fechaActual,descripcion,estado	Start FROM ordenes");
		$stmt->execute();
		$stmt->bind_result($Id, $usuario, $coordenadaX, $coordenadaY, $fechaActual, $descripcion, $estado);
		$artists = array();
		
		while($stmt->fetch()){
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
    public function nuevoOrden(){
		$stmt = $this->con->prepare("INSERT INTO ordenes(usuario, 	tipoAnimal, coordenadaX, coordenadaY,fechaActual,descripcion,estado) VALUES (?, ?, ?, ?,?, ?, ?)");
		$stmt->bind_param("ssssss", $usuario, $tipoAnimal, $coordenadaX, $coordenadaY, $fechaActual, $descripcion, $estado);
		if($stmt->execute())
			return true; 
		return false; 
    }

   #endregion


   #region operaciones de resultado
   public function ListarResultado(){
    $stmt = $this->con->prepare("SELECT Id, usuario,coordenadaX , coordenadaY ,fechaActual,descripcion,estado	Start FROM ordenes");
    $stmt->execute();
    $stmt->bind_result($Id, $usuario, $coordenadaX, $coordenadaY, $fechaActual, $descripcion, $estado);
    $artists = array();
    
    while($stmt->fetch()){
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
public function NuevoResultado(){
    $stmt = $this->con->prepare("INSERT INTO resultado(fkordern, 	descripcion1, descripcion2	, descripcion3,foto) VALUES (?, ?, ?, ?,?)");
    $stmt->bind_param("ssssss", $fkordern, $descripcion1, $descripcion2, $descripcion3, $foto);
    if($stmt->execute())
        return true; 
    return false; 
}
   #endregion


   #region operaciones de gparam
   public function gparam($grupo){
    $stmt = $this->con->prepare("SELECT id, displayname,valor	Start FROM gparam WHERE grupo='" . $grupo . "'");
    $stmt->execute();
    $stmt->bind_result($id, $displayname, $valor, );
    $artists = array();
    
    while($stmt->fetch()){
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
