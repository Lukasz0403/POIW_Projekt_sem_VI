-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Host: db
-- Generation Time: Maj 11, 2026 at 06:41 PM
-- Wersja serwera: 9.6.0
-- Wersja PHP: 8.3.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `motorized_shop`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `categories`
--

CREATE TABLE `categories` (
  `category_id` int NOT NULL,
  `name` varchar(30) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Zrzut danych tabeli `categories`
--

INSERT INTO `categories` (`category_id`, `name`) VALUES
(1, 'Silniki i osprzęt'),
(2, 'Układ hamulcowy'),
(3, 'Układ zawieszenia'),
(4, 'Układ kierowniczy'),
(5, 'Układ wydechowy'),
(6, 'Układ chłodzenia'),
(7, 'Układ paliwowy'),
(8, 'Filtry'),
(9, 'Oleje i płyny eksploatacyjne'),
(10, 'Akumulatory'),
(11, 'Oświetlenie'),
(12, 'Elektronika samochodowa'),
(13, 'Czujniki i sterowniki'),
(14, 'Układ zapłonowy'),
(15, 'Nadwozie'),
(16, 'Wnętrze pojazdu'),
(17, 'Koła i opony'),
(18, 'Klimatyzacja'),
(19, 'Wyposażenie warsztatu'),
(20, 'Akcesoria samochodowe');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `products`
--

CREATE TABLE `products` (
  `product_id` int NOT NULL,
  `name` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `brand` varchar(40) COLLATE utf8mb4_general_ci NOT NULL,
  `category_id` int NOT NULL,
  `price` float UNSIGNED NOT NULL,
  `quantity` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Zrzut danych tabeli `products`
--

INSERT INTO `products` (`product_id`, `name`, `brand`, `category_id`, `price`, `quantity`) VALUES
(2, 'Pompa oleju', 'Bosch', 1, 350, 9),
(3, 'Uszczelka głowicy', 'Elring', 1, 120, 15),
(4, 'Rozrząd kompletny', 'INA', 1, 800, 2),
(5, 'Klocki hamulcowe', 'Brembo', 2, 200, 19),
(6, 'Tarcze hamulcowe', 'ATE', 2, 400, 11),
(7, 'Płyn hamulcowy DOT4', 'Castrol', 2, 50, 26),
(8, 'Amortyzator przód', 'Monroe', 3, 300, 10),
(9, 'Sprężyna zawieszenia', 'KYB', 3, 150, 15),
(10, 'Wahacz', 'Lemforder', 3, 220, 8),
(11, 'Drążek kierowniczy', 'TRW', 4, 100, 14),
(12, 'Końcówka drążka', 'Febi', 4, 60, 18),
(13, 'Tłumik końcowy', 'Walker', 5, 250, 6),
(15, 'Chłodnica', 'Valeo', 6, 500, 5),
(16, 'Termostat', 'Mahle', 6, 80, 20),
(17, 'Wentylator chłodnicy', 'Denso', 6, 300, 7),
(18, 'Pompa paliwa', 'Bosch', 7, 400, 9),
(19, 'Wtryskiwacz', 'Delphi', 7, 600, 6),
(20, 'Filtr oleju', 'Mann', 8, 30, 39),
(21, 'Filtr powietrza', 'Filtron', 8, 40, 35),
(22, 'Filtr kabinowy', 'Bosch', 8, 35, 22),
(23, 'Olej silnikowy 5W30', 'Castrol', 9, 120, 50),
(24, 'Płyn chłodniczy', 'Prestone', 9, 60, 30),
(25, 'Akumulator 12V 60Ah', 'Varta', 10, 350, 10),
(26, 'Akumulator 12V 74Ah', 'Bosch', 10, 420, 8),
(27, 'Żarówka H7', 'Philips', 11, 25, 60),
(28, 'Reflektor przedni', 'Valeo', 11, 700, 5),
(29, 'Radio samochodowe', 'Pioneer', 12, 500, 6),
(30, 'Nawigacja GPS', 'Garmin', 12, 800, 4),
(31, 'Czujnik ABS', 'Bosch', 13, 150, 12),
(32, 'Czujnik temperatury', 'Delphi', 13, 70, 20),
(33, 'Świece zapłonowe', 'NGK', 14, 80, 29),
(34, 'Cewka zapłonowa', 'Bosch', 14, 200, 10),
(35, 'Zderzak przedni', 'OEM', 15, 600, 3),
(36, 'Maska silnika', 'OEM', 15, 900, 2),
(37, 'Dywaniki gumowe', 'Frogum', 16, 100, 20),
(38, 'Pokrowce na siedzenia', 'Sparco', 16, 250, 10),
(39, 'Opona letnia 205/55R16', 'Michelin', 17, 400, 25),
(40, 'Felga aluminiowa 16\"', 'Alutec', 17, 500, 12),
(41, 'Sprężarka klimatyzacji', 'Denso', 18, 1200, 3),
(42, 'Filtr klimatyzacji', 'Bosch', 18, 45, 20),
(43, 'Podnośnik samochodowy', 'Yato', 19, 300, 6),
(44, 'Klucz dynamometryczny', 'Neo', 19, 200, 10),
(45, 'Uchwyt na telefon', 'Baseus', 20, 50, 30),
(46, 'Ładowarka samochodowa', 'Xiaomi', 20, 40, 35),
(47, 'frffrc', 'cd cdcd', 13, 341, 0),
(48, 'retgre', 'regesgredg', 8, 0.05, 343);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `roles`
--

CREATE TABLE `roles` (
  `role_id` int NOT NULL,
  `role_name` varchar(30) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Zrzut danych tabeli `roles`
--

INSERT INTO `roles` (`role_id`, `role_name`) VALUES
(1, 'pracownik'),
(2, 'kierownik'),
(3, 'administrator');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `sales`
--

CREATE TABLE `sales` (
  `id` int NOT NULL,
  `product_id` int NOT NULL,
  `user_id` int NOT NULL,
  `quantity` int UNSIGNED NOT NULL,
  `sale_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `transaction_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `transactions`
--

CREATE TABLE `transactions` (
  `transaction_id` int NOT NULL,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `transaction_sum` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `user_id` int NOT NULL,
  `username` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Zrzut danych tabeli `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `role`) VALUES
(1, 'radek', '$2b$12$Op0okScp7g580NVvE0D8o.nbtdWt1Rv8uW12dUcUodzbaJBnObvye', 3),
(2, 'mietek', '$2b$12$0mnxUt0PoXr5MYGSwanfpuNMTv1EyEpATmMJ6E6gx8O6296iPwkGW', 1),
(3, 'jacek', '$2b$12$6/I/Er7xRxBqZoOOVjscVOClHSM7pnGaIh1NmAIOtK8k406VB0Lum', 2),
(4, 'bolek', '1234', 1),
(5, 'maniek', '$2b$12$LuihNkSgd/W7YH.ePzEpAu6bVpep6DU/KsENXsYrQT.mVsLkC5jAK', 2),
(6, 'dsfasadf', 'dsafadsf', 1),
(7, 'gdfsvd', 'dsfdsfsdf', 1),
(8, 'gdfsvd', 'dsfdsfsdf', 1),
(9, 'gdfsvd', 'dsfdsfsdf', 1),
(10, 'gdfsvd', 'dsfdsfsdf', 1),
(11, 'gdfsvd', 'dsfdsfsdf', 1),
(12, 'gdfsvd', 'dsfdsfsdf', 1),
(13, 'gdfsvd', 'dsfdsfsdf', 1),
(14, 'fdgvdf', 'dsfvdsfdsf', 1),
(15, '34543534', 'dsfvdsfdsf', 1),
(16, 'fdgdfs', '34234', 1),
(17, 'qaz', '1234', 1),
(18, 'wsx', '1234', 1),
(19, 'edc', '1234', 1),
(20, 'edc', '1234', 1),
(21, 'edc', '1234', 1),
(22, 'asx', '1234', 1),
(23, 'wefsa', '1234', 1),
(24, 'wefsa', '1234', 1),
(25, 'wefsa', '1234', 1),
(26, 'zzz', '1234', 1),
(29, 'radek2', '$2b$12$i/V6IoGkWN4fofZlfI2llugm7s.JpqbqgCiFIvNZ19ur5Btwc1jdW', 3),
(31, 'rfrfdds', '$2b$12$9VGj3Eavze/J2z9FyxMZHelUCkJ7UKFi8fCyrry.7kyErrLFz.0V6', 1),
(33, 'gfbtrbtr', '$2b$12$DppiniJuOtzCKiZPYP01D.9OT45yPeA2hNekrOJ6i08KxX2fdz87K', 1),
(34, 'radek34', '$2b$12$Cazhkg1Mx.omcWlwVZTWrumjwU0oZANnketKH8OoZ6xQzk6FjsP2K', 2),
(35, '1234', '$2b$12$1TP2rx4tb45/T3.0Spc/WeeoGJGsumPogau6YPY0Pid9hSqX0xaya', 1),
(36, '1234', '$2b$12$DpxhgxhSp.oRyQXZ/s8w3Oklh.u7gXu3bD.RWM2/7Xa9KCyzlksr2', 2),
(40, 'wsdafcsd', '$2b$12$GEoM7IYcp/KKV/ojSyjjteYft06B/Gg/FwNorarR1qOESx2sgCspS', 1);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`category_id`);

--
-- Indeksy dla tabeli `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`product_id`),
  ADD KEY `FK_CATEGORY_ID` (`category_id`);

--
-- Indeksy dla tabeli `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`role_id`);

--
-- Indeksy dla tabeli `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_PRODUCT_ID` (`product_id`),
  ADD KEY `FK_USER_ID` (`user_id`),
  ADD KEY `sales_ibfk_3` (`transaction_id`);

--
-- Indeksy dla tabeli `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`transaction_id`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD KEY `FK_ROLE_ID` (`role`);

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `categories`
--
ALTER TABLE `categories`
  MODIFY `category_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT dla tabeli `products`
--
ALTER TABLE `products`
  MODIFY `product_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT dla tabeli `roles`
--
ALTER TABLE `roles`
  MODIFY `role_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT dla tabeli `sales`
--
ALTER TABLE `sales`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT dla tabeli `transactions`
--
ALTER TABLE `transactions`
  MODIFY `transaction_id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Ograniczenia dla tabeli `sales`
--
ALTER TABLE `sales`
  ADD CONSTRAINT `sales_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `sales_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `sales_ibfk_3` FOREIGN KEY (`transaction_id`) REFERENCES `transactions` (`transaction_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Ograniczenia dla tabeli `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role`) REFERENCES `roles` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
