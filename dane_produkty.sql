-- =========================================
-- MyParts - Seed danych początkowych
-- Autor: Mateusz Gojny
-- =========================================

--  wybór bazy (nie jest wymagany)
-- USE motorized_shop;

-- =========================================
-- CZYSZCZENIE (opcjonalne - odkomentuj jeśli masz już jakieś dane w tabela kategorii i produktów)
-- =========================================
-- DELETE FROM products;
-- DELETE FROM categories;

-- =========================================
-- KATEGORIE
-- =========================================
INSERT INTO categories (name) VALUES 
('Silniki i osprzęt'),
('Układ hamulcowy'),
('Układ zawieszenia'),
('Układ kierowniczy'),
('Układ wydechowy'),
('Układ chłodzenia'),
('Układ paliwowy'),
('Filtry'),
('Oleje i płyny eksploatacyjne'),
('Akumulatory'),
('Oświetlenie'),
('Elektronika samochodowa'),
('Czujniki i sterowniki'),
('Układ zapłonowy'),
('Nadwozie'),
('Wnętrze pojazdu'),
('Koła i opony'),
('Klimatyzacja'),
('Wyposażenie warsztatu'),
('Akcesoria samochodowe');

-- =========================================
-- PRODUKTY
-- =========================================
INSERT INTO products (name, brand, price, quantity, category_id) VALUES
('Turbosprężarka', 'Garrett', 1200.00, 5, 1),
('Pompa oleju', 'Bosch', 350.00, 10, 1),
('Uszczelka głowicy', 'Elring', 120.00, 15, 1),
('Rozrząd kompletny', 'INA', 800.00, 7, 1),
('Klocki hamulcowe', 'Brembo', 200.00, 20, 2),
('Tarcze hamulcowe', 'ATE', 400.00, 12, 2),
('Płyn hamulcowy DOT4', 'Castrol', 50.00, 30, 2),
('Amortyzator przód', 'Monroe', 300.00, 10, 3),
('Sprężyna zawieszenia', 'KYB', 150.00, 15, 3),
('Wahacz', 'Lemforder', 220.00, 8, 3),
('Drążek kierowniczy', 'TRW', 100.00, 14, 4),
('Końcówka drążka', 'Febi', 60.00, 18, 4),
('Tłumik końcowy', 'Walker', 250.00, 6, 5),
('Katalizator', 'Bosal', 900.00, 4, 5),
('Chłodnica', 'Valeo', 500.00, 5, 6),
('Termostat', 'Mahle', 80.00, 20, 6),
('Wentylator chłodnicy', 'Denso', 300.00, 7, 6),
('Pompa paliwa', 'Bosch', 400.00, 9, 7),
('Wtryskiwacz', 'Delphi', 600.00, 6, 7),
('Filtr oleju', 'Mann', 30.00, 40, 8),
('Filtr powietrza', 'Filtron', 40.00, 35, 8),
('Filtr kabinowy', 'Bosch', 35.00, 25, 8),
('Olej silnikowy 5W30', 'Castrol', 120.00, 50, 9),
('Płyn chłodniczy', 'Prestone', 60.00, 30, 9),
('Akumulator 12V 60Ah', 'Varta', 350.00, 10, 10),
('Akumulator 12V 74Ah', 'Bosch', 420.00, 8, 10),
('Żarówka H7', 'Philips', 25.00, 60, 11),
('Reflektor przedni', 'Valeo', 700.00, 5, 11),
('Radio samochodowe', 'Pioneer', 500.00, 6, 12),
('Nawigacja GPS', 'Garmin', 800.00, 4, 12),
('Czujnik ABS', 'Bosch', 150.00, 12, 13),
('Czujnik temperatury', 'Delphi', 70.00, 20, 13),
('Świece zapłonowe', 'NGK', 80.00, 30, 14),
('Cewka zapłonowa', 'Bosch', 200.00, 10, 14),
('Zderzak przedni', 'OEM', 600.00, 3, 15),
('Maska silnika', 'OEM', 900.00, 2, 15),
('Dywaniki gumowe', 'Frogum', 100.00, 20, 16),
('Pokrowce na siedzenia', 'Sparco', 250.00, 10, 16),
('Opona letnia 205/55R16', 'Michelin', 400.00, 25, 17),
('Felga aluminiowa 16"', 'Alutec', 500.00, 12, 17),
('Sprężarka klimatyzacji', 'Denso', 1200.00, 3, 18),
('Filtr klimatyzacji', 'Bosch', 45.00, 20, 18),
('Podnośnik samochodowy', 'Yato', 300.00, 6, 19),
('Klucz dynamometryczny', 'Neo', 200.00, 10, 19),
('Uchwyt na telefon', 'Baseus', 50.00, 30, 20),
('Ładowarka samochodowa', 'Xiaomi', 40.00, 35, 20);