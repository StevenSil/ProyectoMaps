-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-01-2021 a las 19:48:52
-- Versión del servidor: 10.4.14-MariaDB
-- Versión de PHP: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `ayudaanimaldb`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `gparam`
--

CREATE TABLE `gparam` (
  `id` int(11) NOT NULL,
  `grupo` varchar(150) NOT NULL,
  `displayname` varchar(150) NOT NULL,
  `valor` varchar(150) NOT NULL,
  `tipo` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ordenes`
--

CREATE TABLE `ordenes` (
  `Id` int(11) NOT NULL,
  `usuario` varchar(50) NOT NULL,
  `tipoAnimal` int(11) NOT NULL,
  `coordenadaX` varchar(50) NOT NULL,
  `coordenadaY` varchar(50) NOT NULL,
  `fechaActual` datetime NOT NULL,
  `descripcion` varchar(500) NOT NULL,
  `estado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `ordenes`
--

INSERT INTO `ordenes` (`Id`, `usuario`, `tipoAnimal`, `coordenadaX`, `coordenadaY`, `fechaActual`, `descripcion`, `estado`) VALUES
(1, 'kenny', 1, '75.213123123', '65.1231231', '2021-01-20 21:11:45', 'ewqeqeweadasdasdasd', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `resultado`
--

CREATE TABLE `resultado` (
  `id` int(11) NOT NULL,
  `fkordern` int(11) NOT NULL,
  `descripcion1` varchar(500) NOT NULL,
  `descripcion2` varchar(500) NOT NULL,
  `descripcion3` varchar(500) NOT NULL,
  `foto` mediumtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `Id` int(11) NOT NULL,
  `Nombre` varchar(50) NOT NULL,
  `Apellido` varchar(50) NOT NULL,
  `Edad` int(2) NOT NULL,
  `Telefono` varchar(10) NOT NULL,
  `Direccion` varchar(50) NOT NULL,
  `Usuario` varchar(50) NOT NULL,
  `Contrasena` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`Id`, `Nombre`, `Apellido`, `Edad`, `Telefono`, `Direccion`, `Usuario`, `Contrasena`) VALUES
(1, 'Steve', 'Silva', 20, '994', 'asd', 'ssilva', 'asd');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `gparam`
--
ALTER TABLE `gparam`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `ordenes`
--
ALTER TABLE `ordenes`
  ADD PRIMARY KEY (`Id`);

--
-- Indices de la tabla `resultado`
--
ALTER TABLE `resultado`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `gparam`
--
ALTER TABLE `gparam`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `ordenes`
--
ALTER TABLE `ordenes`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `resultado`
--
ALTER TABLE `resultado`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
