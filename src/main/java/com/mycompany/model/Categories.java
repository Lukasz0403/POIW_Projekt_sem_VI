package com.mycompany.model;

import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Encja JPA reprezentująca kategorię produktów w systemie MyParts. Mapuje
 * tabelę {@code categories} w bazie danych MySQL.
 *
 * <p>
 * Każda kategoria posiada unikalny identyfikator oraz nazwę. Relacja
 * {@code @OneToMany} łączy kategorię z kolekcją przypisanych do niej produktów
 * ({@link Products}). Kolekcja produktów jest oznaczona {@code @JsonIgnore} aby
 * zapobiec cyklicznej serializacji JSON.
 * </p>
 *

 *
 * @author Radosław
 */
@Entity
@Table(name = "categories")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categories.findAll", query = "SELECT c FROM Categories c"),
    @NamedQuery(name = "Categories.findByCategoryId", query = "SELECT c FROM Categories c WHERE c.categoryId = :categoryId"),
    @NamedQuery(name = "Categories.findByName", query = "SELECT c FROM Categories c WHERE c.name = :name")})
public class Categories implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Unikalny identyfikator kategorii generowany automatycznie przez bazę
     * danych. Mapuje kolumnę {@code category_id} (klucz główny).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "category_id")
    private Integer categoryId;

    /**
     * Nazwa kategorii. Pole wymagane, długość od 1 do 30 znaków. Mapuje kolumnę
     * {@code name}.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "name")
    private String name;

    /**
     * Kolekcja produktów przypisanych do tej kategorii. Relacja
     * {@code @OneToMany} — jedna kategoria może zawierać wiele produktów.
     * Oznaczona {@code @JsonIgnore} aby zapobiec cyklicznej serializacji JSON.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoryId")
    @JsonIgnore
    private Collection<Products> productsCollection;

    /**
     * Konstruktor bezargumentowy wymagany przez specyfikację JPA.
     */
    public Categories() {
    }

    /**
     * Konstruktor tworzący kategorię z podanym identyfikatorem.
     *
     * @param categoryId Identyfikator kategorii.
     */
    public Categories(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Konstruktor tworzący kategorię z podanym identyfikatorem i nazwą.
     *
     * @param categoryId Identyfikator kategorii.
     * @param name Nazwa kategorii.
     */
    public Categories(Integer categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    /**
     * Zwraca identyfikator kategorii.
     *
     * @return Identyfikator kategorii lub {@code null} jeśli nie został jeszcze
     * przypisany.
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * Ustawia identyfikator kategorii.
     *
     * @param categoryId Nowy identyfikator kategorii.
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Zwraca nazwę kategorii.
     *
     * @return Nazwa kategorii.
     */
    public String getName() {
        return name;
    }

    /**
     * Ustawia nazwę kategorii.
     *
     * @param name Nowa nazwa kategorii. Musi mieć od 1 do 30 znaków.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Zwraca kolekcję produktów przypisanych do tej kategorii. Metoda oznaczona
     * {@code @XmlTransient} i {@code @JsonIgnore} — nie jest uwzględniana
     * podczas serializacji XML ani JSON.
     *
     * @return Kolekcja obiektów {@link Products} należących do tej kategorii.
     */
    @XmlTransient
    @JsonIgnore
    public Collection<Products> getProductsCollection() {
        return productsCollection;
    }

    /**
     * Ustawia kolekcję produktów przypisanych do tej kategorii.
     *
     * @param productsCollection Nowa kolekcja produktów.
     */
    public void setProductsCollection(Collection<Products> productsCollection) {
        this.productsCollection = productsCollection;
    }

    /**
     * Zwraca hash kod obiektu oparty na identyfikatorze kategorii.
     *
     * @return Hash kod oparty na polu {@code categoryId}.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categoryId != null ? categoryId.hashCode() : 0);
        return hash;
    }

    /**
     * Porównuje dwa obiekty {@code Categories} na podstawie identyfikatora
     * kategorii. Dwie kategorie są równe jeśli posiadają ten sam
     * {@code categoryId}.
     *
     * @param object Obiekt do porównania.
     * @return {@code true} jeśli obiekty mają ten sam identyfikator,
     * {@code false} w przeciwnym razie.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Categories)) {
            return false;
        }
        Categories other = (Categories) object;
        if ((this.categoryId == null && other.categoryId != null) || (this.categoryId != null && !this.categoryId.equals(other.categoryId))) {
            return false;
        }
        return true;
    }

    /**
     * Zwraca tekstową reprezentację obiektu zawierającą identyfikator
     * kategorii.
     *
     * @return Łańcuch tekstowy w formacie {@code Categories[ categoryId=X ]}.
     */
    @Override
    public String toString() {
        return "com.mycompany.servlets.servlets.Categories[ categoryId=" + categoryId + " ]";
    }
}
