/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Products;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mateu
 */
public class Product {
    
    private String category;
    private String name;
    private double price;
    private int amount;

    public Product(String category, String name, double price, int amount) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public Product() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Product{" + "category=" + category + ", name=" + name + ", price=" + price + ", amount=" + amount + '}';
    }
    
    public List<Product> createSampleListOfProduct(){
        
        List<Product> lista = new ArrayList<>();
        
        lista.add(new Product("Olej silnikowy", "Castrol EDGE 5W-30 4L", 189.99, 25));
        lista.add(new Product("Olej silnikowy", "Mobil 1 ESP 5W-30 5L", 229.99, 30));
        lista.add(new Product("Filtry", "Filtr oleju Bosch P3312", 29.99, 100));
        lista.add(new Product("Filtry", "Filtr powietrza Mann C 30 005", 49.99, 80));
        lista.add(new Product("Filtry", "Filtr kabinowy Knecht LAK 182", 39.99, 60));
        lista.add(new Product("Hamulce", "Klocki hamulcowe Brembo P85020", 159.99, 40));
        lista.add(new Product("Hamulce", "Tarcze hamulcowe ATE 24.0125-0156.1", 299.99, 20));
        lista.add(new Product("Zawieszenie", "Amortyzator Sachs 312 123", 249.99, 15));
        lista.add(new Product("Zawieszenie", "Sprężyna zawieszenia Lesjöfors 42 123 45", 139.99, 20));
        lista.add(new Product("Układ kierowniczy", "Końcówka drążka Lemforder 12345", 89.99, 35));
        lista.add(new Product("Akumulator", "Bosch S5 77Ah", 449.99, 10));
        lista.add(new Product("Akumulator", "Varta Silver Dynamic 74Ah", 429.99, 12));
        lista.add(new Product("Oświetlenie", "Żarówka H7 Philips X-tremeVision", 59.99, 70));
        lista.add(new Product("Oświetlenie", "Żarówka H4 Osram Night Breaker", 49.99, 75));
        lista.add(new Product("Wyposażenie", "Apteczka samochodowa DIN 13164", 34.99, 50));
        lista.add(new Product("Wyposażenie", "Gaśnica 1kg ABC", 39.99, 60));
        lista.add(new Product("Wyposażenie", "Trójkąt ostrzegawczy", 24.99, 55));
        lista.add(new Product("Chemia", "Płyn do chłodnic Borygo 5L", 49.99, 45));
        lista.add(new Product("Chemia", "Płyn hamulcowy DOT4 1L", 29.99, 65));
        lista.add(new Product("Chemia", "Płyn do spryskiwaczy zimowy -20C 5L", 19.99, 100));
        lista.add(new Product("Opony", "Michelin Alpin 6 205/55R16", 399.99, 16));
        lista.add(new Product("Opony", "Continental PremiumContact 6 225/45R17", 459.99, 14));
        lista.add(new Product("Felgi", "Felga aluminiowa 16\" DEZENT TD", 349.99, 12));
        lista.add(new Product("Felgi", "Felga stalowa 15\"", 199.99, 20));
        lista.add(new Product("Silnik", "Świeca zapłonowa NGK BKR6E", 19.99, 120));
        lista.add(new Product("Silnik", "Cewka zapłonowa Delphi GN10234", 129.99, 25));
        lista.add(new Product("Napęd", "Sprzęgło Valeo 826317", 599.99, 10));
        lista.add(new Product("Napęd", "Pasek rozrządu Gates 5671XS", 149.99, 30));
        lista.add(new Product("Napęd", "Pompa wody SKF VKPC 81269", 199.99, 18));
        lista.add(new Product("Układ wydechowy", "Tłumik końcowy Bosal 190-123", 249.99, 10));
        lista.add(new Product("Układ wydechowy", "Katalizator uniwersalny", 599.99, 8));
        lista.add(new Product("Elektronika", "Czujnik ABS Bosch 0265007881", 99.99, 20));
        lista.add(new Product("Elektronika", "Przepływomierz powietrza Pierburg", 249.99, 15));
        lista.add(new Product("Elektronika", "Akumulator AGM 70Ah", 599.99, 8));
        lista.add(new Product("Akcesoria", "Dywaniki gumowe komplet", 79.99, 40));
        lista.add(new Product("Akcesoria", "Pokrowce na siedzenia", 129.99, 25));
        lista.add(new Product("Akcesoria", "Uchwyt na telefon do auta", 39.99, 60));
        lista.add(new Product("Akcesoria", "Ładowarka samochodowa USB", 29.99, 70));
        lista.add(new Product("Detailing", "Szampon samochodowy K2 1L", 24.99, 50));
        lista.add(new Product("Detailing", "Wosk samochodowy Turtle Wax", 49.99, 35));
        lista.add(new Product("Detailing", "Mikrofibra do polerowania", 14.99, 100));
        lista.add(new Product("Detailing", "Płyn do czyszczenia felg", 34.99, 40));
        lista.add(new Product("Narzędzia", "Podnośnik samochodowy 2T", 149.99, 20));
        lista.add(new Product("Narzędzia", "Klucz do kół krzyżakowy", 39.99, 45));
        lista.add(new Product("Narzędzia", "Zestaw kluczy nasadowych 108 el.", 199.99, 15));
        lista.add(new Product("Narzędzia", "Kompresor samochodowy 12V", 129.99, 25));
        lista.add(new Product("Sezonowe", "Skrobaczka do szyb", 9.99, 120));
        lista.add(new Product("Sezonowe", "Odmrażacz do szyb 500ml", 14.99, 90));
        lista.add(new Product("Sezonowe", "Osłona przeciwsłoneczna na szybę", 29.99, 60));
        lista.add(new Product("Sezonowe", "Łańcuchy śniegowe KN100", 199.99, 12));
        
        return lista;
    }
            
    
}
