package com.mycompany.model;

import com.mycompany.model.Categories;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Encja JPA reprezentująca produkt dostępny w magazynie sklepu motoryzacyjnego
 * MyParts. Mapuje tabelę {@code products} w bazie danych MySQL.
 *
 * <p>
 * Każdy produkt posiada unikalny identyfikator, nazwę, markę, cenę oraz ilość
 * dostępnych sztuk. Relacja {@code @ManyToOne} łączy produkt z kategorią
 * ({@link Categories}), a relacja {@code @OneToMany} z kolekcją transakcji
 * sprzedaży ({@link Sales}). Kolekcja sprzedaży oznaczona jest
 * {@code @JsonIgnore} aby zapobiec cyklicznej serializacji JSON.</p>
 *
 * <p>
 * Dostępne nazwane zapytania:</p>
 * <ul>
 * <li>{@code Products.findAll} — pobiera wszystkie produkty</li>
 * <li>{@code Products.findByProductId} — wyszukuje produkt po ID</li>
 * <li>{@code Products.findByName} — wyszukuje produkty po nazwie</li>
 * <li>{@code Products.findByBrand} — wyszukuje produkty po marce</li>
 * <li>{@code Products.findByPrice} — wyszukuje produkty po cenie</li>
 * <li>{@code Products.findByQuantity} — wyszukuje produkty po ilości</li>
 * </ul>
 *
 * @author Radosław
 */
@Entity
@Table(name = "products")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Products.findAll", query = "SELECT p FROM Products p"),
    @NamedQuery(name = "Products.findByProductId", query = "SELECT p FROM Products p WHERE p.productId = :productId"),
    @NamedQuery(name = "Products.findByName", query = "SELECT p FROM Products p WHERE p.name = :name"),
    @NamedQuery(name = "Products.findByBrand", query = "SELECT p FROM Products p WHERE p.brand = :brand"),
    @NamedQuery(name = "Products.findByPrice", query = "SELECT p FROM Products p WHERE p.price = :price"),
    @NamedQuery(name = "Products.findByQuantity", query = "SELECT p FROM Products p WHERE p.quantity = :quantity")})
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Unikalny identyfikator produktu generowany automatycznie przez bazę
     * danych. Mapuje kolumnę {@code product_id} (klucz główny).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "product_id")
    private Integer productId;

    /**
     * Nazwa produktu. Pole wymagane, długość od 1 do 30 znaków. Mapuje kolumnę
     * {@code name}.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "name")
    private String name;

    /**
     * Marka produktu. Pole wymagane, długość od 1 do 40 znaków. Mapuje kolumnę
     * {@code brand}.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "brand")
    private String brand;

    /**
     * Cena jednostkowa produktu. Pole wymagane, wartość nieujemna. Mapuje
     * kolumnę {@code price}.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private float price;

    /**
     * Aktualna ilość produktu dostępna w magazynie. Pole wymagane. Wartość jest
     * automatycznie zmniejszana przy sprzedaży i zwiększana przy przyjęciu
     * zamówienia. Mapuje kolumnę {@code quantity}.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity")
    private int quantity;

    /**
     * Kolekcja transakcji sprzedaży powiązanych z tym produktem. Relacja
     * {@code @OneToMany} — jeden produkt może wystąpić w wielu transakcjach.
     * Oznaczona {@code @JsonIgnore} aby zapobiec cyklicznej serializacji JSON.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    @JsonIgnore
    private Collection<Sales> salesCollection;

    /**
     * Kategoria do której należy produkt. Relacja {@code @ManyToOne} — wiele
     * produktów może należeć do jednej kategorii. Mapuje kolumnę
     * {@code category_id} (klucz obcy do tabeli {@code categories}).
     */
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    @ManyToOne(optional = false)
    private Categories categoryId;

    /**
     * Konstruktor bezargumentowy wymagany przez specyfikację JPA.
     */
    public Products() {
    }

    /**
     * Konstruktor tworzący produkt z podanym identyfikatorem.
     *
     * @param productId Identyfikator produktu.
     */
    public Products(Integer productId) {
        this.productId = productId;
    }

    /**
     * Konstruktor tworzący produkt z pełnym zestawem podstawowych danych.
     *
     * @param productId Identyfikator produktu.
     * @param name Nazwa produktu.
     * @param brand Marka produktu.
     * @param price Cena jednostkowa produktu.
     * @param quantity Ilość dostępna w magazynie.
     */
    public Products(Integer productId, String name, String brand, float price, int quantity) {
        this.productId = productId;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Zwraca identyfikator produktu.
     *
     * @return Identyfikator produktu lub {@code null} jeśli nie został jeszcze
     * przypisany.
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * Ustawia identyfikator produktu.
     *
     * @param productId Nowy identyfikator produktu.
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * Zwraca nazwę produktu.
     *
     * @return Nazwa produktu.
     */
    public String getName() {
        return name;
    }

    /**
     * Ustawia nazwę produktu.
     *
     * @param name Nowa nazwa produktu. Musi mieć od 1 do 30 znaków.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Zwraca markę produktu.
     *
     * @return Marka produktu.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Ustawia markę produktu.
     *
     * @param brand Nowa marka produktu. Musi mieć od 1 do 40 znaków.
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Zwraca cenę jednostkową produktu.
     *
     * @return Cena produktu jako wartość zmiennoprzecinkowa.
     */
    public float getPrice() {
        return price;
    }

    /**
     * Ustawia cenę jednostkową produktu.
     *
     * @param price Nowa cena produktu. Powinna być wartością nieujemną.
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Zwraca aktualną ilość produktu dostępną w magazynie.
     *
     * @return Ilość produktu w magazynie.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Ustawia ilość produktu dostępną w magazynie.
     *
     * @param quantity Nowa ilość produktu. Powinna być wartością nieujemną.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Zwraca kolekcję transakcji sprzedaży powiązanych z tym produktem. Metoda
     * oznaczona {@code @XmlTransient} i {@code @JsonIgnore} — nie jest
     * uwzględniana podczas serializacji XML ani JSON.
     *
     * @return Kolekcja obiektów {@link Sales} powiązanych z tym produktem.
     */
    @XmlTransient
    @JsonIgnore
    public Collection<Sales> getSalesCollection() {
        return salesCollection;
    }

    /**
     * Ustawia kolekcję transakcji sprzedaży powiązanych z tym produktem.
     *
     * @param salesCollection Nowa kolekcja transakcji sprzedaży.
     */
    public void setSalesCollection(Collection<Sales> salesCollection) {
        this.salesCollection = salesCollection;
    }

    /**
     * Zwraca kategorię do której należy produkt.
     *
     * @return Obiekt {@link Categories} reprezentujący kategorię produktu.
     */
    public Categories getCategoryId() {
        return categoryId;
    }

    /**
     * Ustawia kategorię produktu.
     *
     * @param categoryId Obiekt {@link Categories} reprezentujący nową kategorię
     * produktu.
     */
    public void setCategoryId(Categories categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Zwraca hash kod obiektu oparty na identyfikatorze produktu.
     *
     * @return Hash kod oparty na polu {@code productId}.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    /**
     * Porównuje dwa obiekty {@code Products} na podstawie identyfikatora
     * produktu. Dwa produkty są równe jeśli posiadają ten sam
     * {@code productId}.
     *
     * @param object Obiekt do porównania.
     * @return {@code true} jeśli obiekty mają ten sam identyfikator,
     * {@code false} w przeciwnym razie.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Products)) {
            return false;
        }
        Products other = (Products) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    /**
     * Zwraca tekstową reprezentację obiektu zawierającą identyfikator produktu.
     *
     * @return Łańcuch tekstowy w formacie {@code Products[ productId=X ]}.
     */
    @Override
    public String toString() {
        return "com.mycompany.servlets.servlets.Products[ productId=" + productId + " ]";
    }
}
