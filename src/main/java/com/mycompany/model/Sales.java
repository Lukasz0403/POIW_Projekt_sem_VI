package com.mycompany.model;

import com.mycompany.model.Products;
import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Encja JPA reprezentująca pojedynczą pozycję sprzedaży w systemie MyParts.
 * Mapuje tabelę {@code sales} w bazie danych MySQL.
 *
 * <p>
 * Każdy rekord sprzedaży rejestruje sprzedaż określonej ilości produktu przez
 * konkretnego użytkownika w ramach transakcji. Relacje {@code @ManyToOne} łączą
 * pozycję sprzedaży z produktem ({@link Products}), użytkownikiem
 * ({@link Users}) oraz transakcją ({@link Transactions}).</p>
 *
 * <p>
 * Dostępne nazwane zapytania:</p>
 * <ul>
 * <li>{@code Sales.findAll} — pobiera wszystkie pozycje sprzedaży</li>
 * <li>{@code Sales.findById} — wyszukuje pozycję sprzedaży po ID</li>
 * <li>{@code Sales.findByQuantity} — wyszukuje pozycje po ilości</li>
 * <li>{@code Sales.findBySaleDate} — wyszukuje pozycje po dacie sprzedaży</li>
 * </ul>
 *
 * @author Radosław
 */
@Entity
@Table(name = "sales")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sales.findAll", query = "SELECT s FROM Sales s"),
    @NamedQuery(name = "Sales.findById", query = "SELECT s FROM Sales s WHERE s.id = :id"),
    @NamedQuery(name = "Sales.findByQuantity", query = "SELECT s FROM Sales s WHERE s.quantity = :quantity"),
    @NamedQuery(name = "Sales.findBySaleDate", query = "SELECT s FROM Sales s WHERE s.saleDate = :saleDate")})
public class Sales implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Unikalny identyfikator pozycji sprzedaży generowany automatycznie przez
     * bazę danych. Mapuje kolumnę {@code id} (klucz główny).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    /**
     * Ilość sprzedanych sztuk produktu w ramach tej pozycji. Pole wymagane.
     * Mapuje kolumnę {@code quantity}.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity")
    private int quantity;

    /**
     * Data i godzina zarejestrowania sprzedaży. Pole wymagane. Przechowywane
     * jako znacznik czasu ({@code TIMESTAMP}) w bazie danych. Mapuje kolumnę
     * {@code sale_date}.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "sale_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date saleDate;

    /**
     * Produkt który został sprzedany w ramach tej pozycji. Relacja
     * {@code @ManyToOne} — wiele pozycji sprzedaży może dotyczyć tego samego
     * produktu. Mapuje kolumnę {@code product_id} (klucz obcy do tabeli
     * {@code products}).
     */
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    @ManyToOne(optional = false)
    private Products productId;

    /**
     * Użytkownik który zarejestrował sprzedaż. Relacja {@code @ManyToOne} —
     * jeden użytkownik może zarejestrować wiele pozycji sprzedaży. Mapuje
     * kolumnę {@code user_id} (klucz obcy do tabeli {@code users}).
     */
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private Users userId;

    /**
     * Transakcja do której należy ta pozycja sprzedaży. Relacja
     * {@code @ManyToOne} — jedna transakcja może zawierać wiele pozycji
     * sprzedaży. Mapuje kolumnę {@code transaction_id} (klucz obcy do tabeli
     * {@code transactions}).
     */
    @JoinColumn(name = "transaction_id", referencedColumnName = "transaction_id")
    @ManyToOne(optional = false)
    private Transactions transactionId;

    /**
     * Konstruktor bezargumentowy wymagany przez specyfikację JPA.
     */
    public Sales() {
    }

    /**
     * Konstruktor tworzący pozycję sprzedaży z podanym identyfikatorem.
     *
     * @param id Identyfikator pozycji sprzedaży.
     */
    public Sales(Integer id) {
        this.id = id;
    }

    /**
     * Konstruktor tworzący pozycję sprzedaży z podanymi podstawowymi danymi.
     *
     * @param id Identyfikator pozycji sprzedaży.
     * @param quantity Ilość sprzedanych sztuk.
     * @param saleDate Data i godzina sprzedaży.
     */
    public Sales(Integer id, int quantity, Date saleDate) {
        this.id = id;
        this.quantity = quantity;
        this.saleDate = saleDate;
    }

    /**
     * Zwraca identyfikator pozycji sprzedaży.
     *
     * @return Identyfikator pozycji sprzedaży lub {@code null} jeśli nie został
     * jeszcze przypisany.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Ustawia identyfikator pozycji sprzedaży.
     *
     * @param id Nowy identyfikator pozycji sprzedaży.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Zwraca ilość sprzedanych sztuk produktu.
     *
     * @return Ilość sprzedanych sztuk.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Ustawia ilość sprzedanych sztuk produktu.
     *
     * @param quantity Nowa ilość sprzedanych sztuk. Powinna być wartością
     * dodatnią.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Zwraca datę i godzinę zarejestrowania sprzedaży.
     *
     * @return Data i godzina sprzedaży jako obiekt {@link Date}.
     */
    public Date getSaleDate() {
        return saleDate;
    }

    /**
     * Ustawia datę i godzinę zarejestrowania sprzedaży.
     *
     * @param saleDate Nowa data i godzina sprzedaży.
     */
    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    /**
     * Zwraca produkt powiązany z tą pozycją sprzedaży.
     *
     * @return Obiekt {@link Products} reprezentujący sprzedany produkt.
     */
    public Products getProductId() {
        return productId;
    }

    /**
     * Ustawia produkt powiązany z tą pozycją sprzedaży.
     *
     * @param productId Obiekt {@link Products} reprezentujący sprzedany
     * produkt.
     */
    public void setProductId(Products productId) {
        this.productId = productId;
    }

    /**
     * Zwraca użytkownika który zarejestrował sprzedaż.
     *
     * @return Obiekt {@link Users} reprezentujący sprzedawcę.
     */
    public Users getUserId() {
        return userId;
    }

    /**
     * Ustawia użytkownika który zarejestrował sprzedaż.
     *
     * @param userId Obiekt {@link Users} reprezentujący sprzedawcę.
     */
    public void setUserId(Users userId) {
        this.userId = userId;
    }

    /**
     * Zwraca transakcję do której należy ta pozycja sprzedaży.
     *
     * @return Obiekt {@link Transactions} reprezentujący transakcję nadrzędną.
     */
    public Transactions getTransactionId() {
        return transactionId;
    }

    /**
     * Ustawia transakcję do której należy ta pozycja sprzedaży.
     *
     * @param transactionId Obiekt {@link Transactions} reprezentujący
     * transakcję nadrzędną.
     */
    public void setTransactionId(Transactions transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Zwraca hash kod obiektu oparty na identyfikatorze pozycji sprzedaży.
     *
     * @return Hash kod oparty na polu {@code id}.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Porównuje dwa obiekty {@code Sales} na podstawie identyfikatora pozycji
     * sprzedaży. Dwie pozycje są równe jeśli posiadają ten sam {@code id}.
     *
     * @param object Obiekt do porównania.
     * @return {@code true} jeśli obiekty mają ten sam identyfikator,
     * {@code false} w przeciwnym razie.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Sales)) {
            return false;
        }
        Sales other = (Sales) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Zwraca tekstową reprezentację obiektu zawierającą identyfikator pozycji
     * sprzedaży.
     *
     * @return Łańcuch tekstowy w formacie {@code Sales[ id=X ]}.
     */
    @Override
    public String toString() {
        return "com.mycompany.servlets.servlets.Sales[ id=" + id + " ]";
    }
}
