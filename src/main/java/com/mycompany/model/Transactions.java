package com.mycompany.model;

import com.mycompany.model.Sales;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Encja JPA reprezentująca transakcję sprzedaży w systemie MyParts. Mapuje
 * tabelę {@code transactions} w bazie danych MySQL.
 *
 * <p>
 * Transakcja grupuje wiele pozycji sprzedaży ({@link Sales}) zarejestrowanych w
 * ramach jednej operacji kasowej. Każda transakcja posiada datę i godzinę
 * rejestracji oraz łączną wartość sprzedanych produktów.</p>
 *
 * <p>
 * Relacja {@code @OneToMany} łączy transakcję z kolekcją pozycji sprzedaży.
 * Kolekcja oznaczona jest {@code @JsonIgnore} aby zapobiec cyklicznej
 * serializacji JSON.</p>
 *
 * <p>
 * Dostępne nazwane zapytania:</p>
 * <ul>
 * <li>{@code Transactions.findAll} — pobiera wszystkie transakcje</li>
 * <li>{@code Transactions.findByTransactionId} — wyszukuje transakcję po
 * ID</li>
 * <li>{@code Transactions.findByDate} — wyszukuje transakcje po dacie</li>
 * <li>{@code Transactions.findByTransactionSum} — wyszukuje transakcje po
 * wartości</li>
 * </ul>
 *
 * @author Radosław
 */
@Entity
@Table(name = "transactions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transactions.findAll", query = "SELECT t FROM Transactions t"),
    @NamedQuery(name = "Transactions.findByTransactionId", query = "SELECT t FROM Transactions t WHERE t.transactionId = :transactionId"),
    @NamedQuery(name = "Transactions.findByDate", query = "SELECT t FROM Transactions t WHERE t.date = :date"),
    @NamedQuery(name = "Transactions.findByTransactionSum", query = "SELECT t FROM Transactions t WHERE t.transactionSum = :transactionSum")})
public class Transactions implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Unikalny identyfikator transakcji generowany automatycznie przez bazę
     * danych. Mapuje kolumnę {@code transaction_id} (klucz główny).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "transaction_id")
    private Integer transactionId;

    /**
     * Data i godzina zarejestrowania transakcji. Pole wymagane. Przechowywane
     * jako znacznik czasu ({@code TIMESTAMP}) w bazie danych. Mapuje kolumnę
     * {@code date}.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    /**
     * Łączna wartość transakcji wyrażona w groszach lub pełnych złotych. Pole
     * wymagane. Mapuje kolumnę {@code transaction_sum}.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "transaction_sum")
    private int transactionSum;

    /**
     * Kolekcja pozycji sprzedaży wchodzących w skład tej transakcji. Relacja
     * {@code @OneToMany} — jedna transakcja może zawierać wiele pozycji
     * sprzedaży. Oznaczona {@code @JsonIgnore} aby zapobiec cyklicznej
     * serializacji JSON.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transactionId")
    @JsonIgnore
    private Collection<Sales> salesCollection;

    /**
     * Konstruktor bezargumentowy wymagany przez specyfikację JPA.
     */
    public Transactions() {
    }

    /**
     * Konstruktor tworzący transakcję z podanym identyfikatorem.
     *
     * @param transactionId Identyfikator transakcji.
     */
    public Transactions(Integer transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Konstruktor tworzący transakcję z podanymi podstawowymi danymi.
     *
     * @param transactionId Identyfikator transakcji.
     * @param date Data i godzina zarejestrowania transakcji.
     * @param transactionSum Łączna wartość transakcji.
     */
    public Transactions(Integer transactionId, Date date, int transactionSum) {
        this.transactionId = transactionId;
        this.date = date;
        this.transactionSum = transactionSum;
    }

    /**
     * Zwraca identyfikator transakcji.
     *
     * @return Identyfikator transakcji lub {@code null} jeśli nie został
     * jeszcze przypisany.
     */
    public Integer getTransactionId() {
        return transactionId;
    }

    /**
     * Ustawia identyfikator transakcji.
     *
     * @param transactionId Nowy identyfikator transakcji.
     */
    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Zwraca datę i godzinę zarejestrowania transakcji.
     *
     * @return Data i godzina transakcji jako obiekt {@link Date}.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Ustawia datę i godzinę zarejestrowania transakcji.
     *
     * @param date Nowa data i godzina transakcji.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Zwraca łączną wartość transakcji.
     *
     * @return Łączna wartość transakcji.
     */
    public int getTransactionSum() {
        return transactionSum;
    }

    /**
     * Ustawia łączną wartość transakcji.
     *
     * @param transactionSum Nowa łączna wartość transakcji. Powinna być
     * wartością nieujemną.
     */
    public void setTransactionSum(int transactionSum) {
        this.transactionSum = transactionSum;
    }

    /**
     * Zwraca kolekcję pozycji sprzedaży wchodzących w skład tej transakcji.
     * Metoda oznaczona {@code @XmlTransient} — nie jest uwzględniana podczas
     * serializacji XML.
     *
     * @return Kolekcja obiektów {@link Sales} należących do tej transakcji.
     */
    @XmlTransient
    public Collection<Sales> getSalesCollection() {
        return salesCollection;
    }

    /**
     * Ustawia kolekcję pozycji sprzedaży wchodzących w skład tej transakcji.
     *
     * @param salesCollection Nowa kolekcja pozycji sprzedaży.
     */
    public void setSalesCollection(Collection<Sales> salesCollection) {
        this.salesCollection = salesCollection;
    }

    /**
     * Zwraca hash kod obiektu oparty na identyfikatorze transakcji.
     *
     * @return Hash kod oparty na polu {@code transactionId}.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionId != null ? transactionId.hashCode() : 0);
        return hash;
    }

    /**
     * Porównuje dwa obiekty {@code Transactions} na podstawie identyfikatora
     * transakcji. Dwie transakcje są równe jeśli posiadają ten sam
     * {@code transactionId}.
     *
     * @param object Obiekt do porównania.
     * @return {@code true} jeśli obiekty mają ten sam identyfikator,
     * {@code false} w przeciwnym razie.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Transactions)) {
            return false;
        }
        Transactions other = (Transactions) object;
        if ((this.transactionId == null && other.transactionId != null) || (this.transactionId != null && !this.transactionId.equals(other.transactionId))) {
            return false;
        }
        return true;
    }

    /**
     * Zwraca tekstową reprezentację obiektu zawierającą identyfikator
     * transakcji.
     *
     * @return Łańcuch tekstowy w formacie
     * {@code Transactions[ transactionId=X ]}.
     */
    @Override
    public String toString() {
        return "com.mycompany.servlets.servlets.Transactions[ transactionId=" + transactionId + " ]";
    }
}
