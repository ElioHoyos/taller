-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 21-10-2025 a las 00:35:28
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `taller`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `article`
--

CREATE TABLE `article` (
  `id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `code` varchar(13) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `amount` int(11) NOT NULL,
  `purchase_price` decimal(10,2) NOT NULL,
  `sale_price` decimal(10,2) NOT NULL,
  `expiration_date` date NOT NULL,
  `state` bit(1) NOT NULL,
  `date_modified` date NOT NULL,
  `date_created` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `article`
--

INSERT INTO `article` (`id`, `category_id`, `code`, `name`, `description`, `amount`, `purchase_price`, `sale_price`, `expiration_date`, `state`, `date_modified`, `date_created`) VALUES
(8, 2, '2510171700305', 'Leche Gloria Light Lata 390 g', 'Leche Gloria Light Lata 390 g', 100, 3.50, 4.70, '2025-11-05', b'1', '2025-10-17', '2025-10-17'),
(9, 7, '2510172303246', 'Queso Cheddar LAIVE Paquete 170g', '', 10, 5.50, 8.50, '2025-11-07', b'1', '2025-10-17', '2025-10-17'),
(10, 8, '2510180001042', 'ARIEL SKU: K0000008219 Pack Detergente Líquido ARIEL Pro Cuidado 3L x 2un', '', 5, 50.45, 78.90, '2026-12-31', b'1', '2025-10-18', '2025-10-18'),
(11, 37, '2510180106549', 'Oppo A40 256GB Vino', 'Tamaño. 6.67, Relación de Pantalla. 89.9%, Almacenamiento Capacidad de RAM y ROM  4 GB + 256 GB  Tipo de RAM  LPDDR4X a 2133 MHz, 2 × 16 bits', 5, 400.00, 750.00, '2026-12-31', b'1', '2025-10-18', '2025-10-18'),
(12, 37, '2510180113790', 'Honor X8C 256GB 5G Gris', 'Color: Gris, Capacidad: 256GB, Cámara principal: 108MP + 5MP, Cámara frontal: 50MP, Memoria interna: 256GB\nMemoria RAM: 8GB RAM + 8GB RAM Turbo', 10, 390.00, 690.00, '2026-12-31', b'1', '2025-10-18', '2025-10-18'),
(13, 38, '2510180949481', 'Audífonos Bluetooth Huawei Freebuds Se 2', 'Marca: Huawei, Modelo: ULC-CT010, Tipo: Audífonos, bluetooth, Peso: 0.03, Auricular: Auriculares in ear\nAudio HQ: Si, Color Blanco, Modelo ULC-CT010', 10, 35.00, 50.00, '2026-01-30', b'1', '2025-10-18', '2025-10-18'),
(14, 38, '2510181203407', 'JVC Audífonos Bluetooth JVC HA-A4T', 'Marca: Jvc, Modelo: HA-A4T-B-U,Tipo: Audífonos earbuds\nLargo del cable: No aplica,Color básico	Negro,Modelo	HA-A4T-B-U', 10, 20.00, 45.00, '2026-01-19', b'1', '2025-10-18', '2025-10-18');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `state` bit(1) NOT NULL,
  `date_created` date DEFAULT NULL,
  `date_modified` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `category`
--

INSERT INTO `category` (`id`, `name`, `state`, `date_created`, `date_modified`) VALUES
(2, 'Leche', b'1', '2024-12-30', '2024-12-30'),
(3, 'Aceite', b'1', '2024-12-30', '2025-10-18'),
(5, 'Mantequilla', b'1', '2025-02-05', '2025-02-05'),
(7, 'Queso', b'1', '2025-06-03', '2025-06-03'),
(8, 'Detergente', b'1', '2025-06-03', '2025-06-03'),
(9, 'Shampo', b'1', '2025-06-03', '2025-06-11'),
(13, 'Arroz', b'1', '2025-06-11', '2025-06-11'),
(15, 'Jabón', b'1', '2025-06-11', '2025-06-11'),
(20, 'Gaseosa', b'1', '2025-06-11', '2025-06-11'),
(37, 'Celular', b'1', '2025-10-18', '2025-10-18'),
(38, 'Audifono', b'1', '2025-10-18', '2025-10-18');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `customer`
--

CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `type_person` enum('Cliente','Proveedor') NOT NULL,
  `name` varchar(300) NOT NULL,
  `document_type` enum('DNI','RUC') NOT NULL,
  `document_number` varchar(20) NOT NULL,
  `cellphone` varchar(20) NOT NULL,
  `email` text DEFAULT NULL,
  `address` text DEFAULT NULL,
  `state` bit(1) NOT NULL,
  `date_modified` date NOT NULL,
  `date_created` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `customer`
--

INSERT INTO `customer` (`id`, `type_person`, `name`, `document_type`, `document_number`, `cellphone`, `email`, `address`, `state`, `date_modified`, `date_created`) VALUES
(1, 'Cliente', 'FRANKOYCI RAMIREZ CHUQUIPIONDO', 'DNI', '77809451', '985454992', NULL, NULL, b'1', '2025-10-20', '2025-10-20');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `date_modified` date NOT NULL,
  `date_created` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`id`, `name`, `description`, `date_modified`, `date_created`) VALUES
(1, 'Asistente', 'Asistente administrativo', '2025-01-27', '2025-01-27'),
(2, 'Cajero', 'Personal que atiende en caja', '2025-01-27', '2025-01-27');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sale`
--

CREATE TABLE `sale` (
  `id` int(11) NOT NULL,
  `people_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `voucher_type` enum('Boleta','Ruc') NOT NULL,
  `voucher_series` varchar(20) NOT NULL,
  `voucher_number` varchar(20) NOT NULL,
  `sale_date` date NOT NULL,
  `igv` decimal(10,2) NOT NULL,
  `total` decimal(10,2) NOT NULL,
  `state` bit(1) NOT NULL,
  `date_modified` date NOT NULL,
  `date_created` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `sale`
--

INSERT INTO `sale` (`id`, `people_id`, `user_id`, `voucher_type`, `voucher_series`, `voucher_number`, `sale_date`, `igv`, `total`, `state`, `date_modified`, `date_created`) VALUES
(1, 1, 1, 'Boleta', 'B001', 'B001', '2025-02-03', 0.18, 200.00, b'1', '2025-02-03', '2025-02-03'),
(2, 3, 1, 'Boleta', 'B002', 'B002', '2025-02-03', 0.18, 180.00, b'1', '2025-02-03', '2025-02-03'),
(3, 3, 1, 'Boleta', 'B002', 'B002', '2025-10-07', 0.18, 180.00, b'1', '2025-10-07', '2025-10-07');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sale_detail`
--

CREATE TABLE `sale_detail` (
  `id` int(11) NOT NULL,
  `sale_id` int(11) NOT NULL,
  `article_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `discount` decimal(10,2) NOT NULL,
  `date_modified` date NOT NULL,
  `date_created` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `rol_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `document_type` enum('DNI','RUC') NOT NULL,
  `document_number` varchar(20) NOT NULL,
  `address` text DEFAULT NULL,
  `cellphone` varchar(20) DEFAULT NULL,
  `email` text DEFAULT NULL,
  `state` bit(1) NOT NULL,
  `date_modified` date NOT NULL,
  `date_created` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`id`, `rol_id`, `name`, `document_type`, `document_number`, `address`, `cellphone`, `email`, `state`, `date_modified`, `date_created`) VALUES
(1, 1, 'Elio Hoyos', 'DNI', '76439016', 'jiron los girasolez mz7 lte25', '985454992', 'elio18david@gmail.com', b'1', '2025-01-31', '2025-01-31'),
(2, 1, 'Elio Hoyos', 'DNI', '76439016', 'jiron los girasolez mz7 lte25', '985454992', 'elio18david@gmail.com', b'1', '2025-10-07', '2025-10-07');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `article`
--
ALTER TABLE `article`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `code_UNIQUE` (`code`),
  ADD UNIQUE KEY `uk_article_code` (`code`),
  ADD KEY `category_id_idx` (`category_id`),
  ADD KEY `idx_article_code` (`code`);

--
-- Indices de la tabla `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uq_customer_doc` (`document_type`,`document_number`),
  ADD KEY `idx_customer_doc` (`document_type`,`document_number`),
  ADD KEY `idx_customer_cell` (`cellphone`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `sale`
--
ALTER TABLE `sale`
  ADD PRIMARY KEY (`id`),
  ADD KEY `people_id_idx` (`people_id`),
  ADD KEY `user_id_idx` (`user_id`);

--
-- Indices de la tabla `sale_detail`
--
ALTER TABLE `sale_detail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sale_id_idx` (`sale_id`),
  ADD KEY `article_id_idx` (`article_id`);

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `rol_id_idx` (`rol_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `article`
--
ALTER TABLE `article`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT de la tabla `customer`
--
ALTER TABLE `customer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `rol`
--
ALTER TABLE `rol`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `sale`
--
ALTER TABLE `sale`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `sale_detail`
--
ALTER TABLE `sale_detail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `article`
--
ALTER TABLE `article`
  ADD CONSTRAINT `category_id` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `sale`
--
ALTER TABLE `sale`
  ADD CONSTRAINT `people_id` FOREIGN KEY (`people_id`) REFERENCES `customer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `sale_detail`
--
ALTER TABLE `sale_detail`
  ADD CONSTRAINT `article_id` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `sale_id` FOREIGN KEY (`sale_id`) REFERENCES `sale` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `rol_id` FOREIGN KEY (`rol_id`) REFERENCES `rol` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
