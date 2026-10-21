-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 21, 2026 at 08:37 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `farmacie_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `clienti`
--

CREATE TABLE `clienti` (
  `id` int(11) NOT NULL,
  `nume` varchar(50) NOT NULL,
  `prenume` varchar(50) NOT NULL,
  `telefon` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `clienti`
--

INSERT INTO `clienti` (`id`, `nume`, `prenume`, `telefon`) VALUES
(1, 'Popescu', 'Ion', '0722111222'),
(2, 'Ionescu', 'Andrei', '0752314513');

-- --------------------------------------------------------

--
-- Table structure for table `detalii_vanzari`
--

CREATE TABLE `detalii_vanzari` (
  `id` int(11) NOT NULL,
  `id_vanzare` int(11) DEFAULT NULL,
  `id_medicament` int(11) DEFAULT NULL,
  `cantitate` int(11) NOT NULL,
  `pret_la_vanzare` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `detalii_vanzari`
--

INSERT INTO `detalii_vanzari` (`id`, `id_vanzare`, `id_medicament`, `cantitate`, `pret_la_vanzare`) VALUES
(1, 3, 2, 10, 12.50),
(2, 4, 3, 40, 24.90),
(3, 5, 3, 10, 24.90),
(4, 6, 6, 10, 15.00);

-- --------------------------------------------------------

--
-- Table structure for table `intrari_marfa`
--

CREATE TABLE `intrari_marfa` (
  `id` int(11) NOT NULL,
  `id_medicament` int(11) DEFAULT NULL,
  `nume_furnizor` varchar(100) NOT NULL,
  `cantitate` int(11) NOT NULL,
  `data_intrare` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `medicamente`
--

CREATE TABLE `medicamente` (
  `id` int(11) NOT NULL,
  `denumire` varchar(100) NOT NULL,
  `categorie` varchar(50) DEFAULT NULL,
  `pret` decimal(10,2) NOT NULL,
  `stoc` int(11) NOT NULL,
  `data_expirare` date DEFAULT NULL,
  `necesita_reteta` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `medicamente`
--

INSERT INTO `medicamente` (`id`, `denumire`, `categorie`, `pret`, `stoc`, `data_expirare`, `necesita_reteta`) VALUES
(2, 'Paracetamol 500mg', 'Analgezice', 12.50, 110, '2027-10-15', 0),
(3, 'Nurofen Express 200mg', 'Antiinflamatoare', 24.90, 45, '2026-11-20', 0),
(4, 'Augmentin 1000mg', 'Antibiotice', 48.30, 30, '2026-04-05', 1),
(5, 'No-Spa 40mg', 'Antispastice', 18.20, 0, '2027-02-10', 0),
(6, 'Aspenter 100mg', 'Cardiovasculare', 15.00, 190, '2028-01-01', 0),
(7, 'Amoxicilină 500mg', 'Antibiotice', 22.00, 45, '2026-09-30', 1),
(8, 'Vitamina C 1000mg', 'Suplimente', 19.50, 150, '2028-05-12', 0),
(9, 'Claritin 10mg', 'Antihistaminice', 29.90, 60, '2027-08-18', 0),
(10, 'Xanax 0.5mg', 'Sistem Nervos', 35.00, 15, '2026-12-01', 1),
(11, 'Linex Forte', 'Probiotice', 42.00, 70, '2027-03-22', 0);

-- --------------------------------------------------------

--
-- Table structure for table `utilizatori`
--

CREATE TABLE `utilizatori` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `parola` varchar(255) NOT NULL,
  `rol` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `utilizatori`
--

INSERT INTO `utilizatori` (`id`, `username`, `parola`, `rol`) VALUES
(2, 'admin', 'admin123', 'ADMIN'),
(3, 'farmacist', 'farma123', 'FARMACIST');

-- --------------------------------------------------------

--
-- Table structure for table `vanzari`
--

CREATE TABLE `vanzari` (
  `id` int(11) NOT NULL,
  `data_vanzare` datetime DEFAULT current_timestamp(),
  `id_utilizator` int(11) DEFAULT NULL,
  `id_client` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `vanzari`
--

INSERT INTO `vanzari` (`id`, `data_vanzare`, `id_utilizator`, `id_client`) VALUES
(3, '2026-07-10 12:14:05', 2, NULL),
(4, '2026-07-10 12:15:11', 2, NULL),
(5, '2026-07-10 12:23:35', 2, NULL),
(6, '2026-07-17 09:08:47', 2, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `clienti`
--
ALTER TABLE `clienti`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `detalii_vanzari`
--
ALTER TABLE `detalii_vanzari`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_vanzare` (`id_vanzare`),
  ADD KEY `id_medicament` (`id_medicament`);

--
-- Indexes for table `intrari_marfa`
--
ALTER TABLE `intrari_marfa`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_medicament` (`id_medicament`);

--
-- Indexes for table `medicamente`
--
ALTER TABLE `medicamente`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `utilizatori`
--
ALTER TABLE `utilizatori`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `vanzari`
--
ALTER TABLE `vanzari`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_utilizator` (`id_utilizator`),
  ADD KEY `fk_client_vanzare` (`id_client`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `clienti`
--
ALTER TABLE `clienti`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `detalii_vanzari`
--
ALTER TABLE `detalii_vanzari`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `intrari_marfa`
--
ALTER TABLE `intrari_marfa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `medicamente`
--
ALTER TABLE `medicamente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `utilizatori`
--
ALTER TABLE `utilizatori`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `vanzari`
--
ALTER TABLE `vanzari`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `detalii_vanzari`
--
ALTER TABLE `detalii_vanzari`
  ADD CONSTRAINT `detalii_vanzari_ibfk_1` FOREIGN KEY (`id_vanzare`) REFERENCES `vanzari` (`id`),
  ADD CONSTRAINT `detalii_vanzari_ibfk_2` FOREIGN KEY (`id_medicament`) REFERENCES `medicamente` (`id`);

--
-- Constraints for table `intrari_marfa`
--
ALTER TABLE `intrari_marfa`
  ADD CONSTRAINT `intrari_marfa_ibfk_1` FOREIGN KEY (`id_medicament`) REFERENCES `medicamente` (`id`);

--
-- Constraints for table `vanzari`
--
ALTER TABLE `vanzari`
  ADD CONSTRAINT `fk_client_vanzare` FOREIGN KEY (`id_client`) REFERENCES `clienti` (`id`) ON DELETE SET NULL,
  ADD CONSTRAINT `vanzari_ibfk_1` FOREIGN KEY (`id_utilizator`) REFERENCES `utilizatori` (`id`),
  ADD CONSTRAINT `vanzari_ibfk_2` FOREIGN KEY (`id_client`) REFERENCES `clienti` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
