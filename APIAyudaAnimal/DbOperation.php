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
}
