package com.mycompany.model;

import com.mycompany.model.Sales;
import com.mycompany.model.Roles;
import java.io.Serializable;
import java.util.Collection;

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

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Encja JPA reprezentująca konto użytkownika systemu MyParts. Mapuje tabelę
 * {@code users} w bazie danych MySQL.
 *
 * <p>
 * Każdy użytkownik posiada unikalny identyfikator, login oraz hasło
 * przechowywane w postaci skrótu BCrypt. Relacja {@code @ManyToOne} łączy
 * użytkownika z przypisaną rolą ({@link Roles}), która określa zakres
 * dostępnych funkcji systemu. Relacja {@code @OneToMany} łączy użytkownika z
 * kolekcją zarejestrowanych przez niego pozycji sprzedaży ({@link Sales}).</p>
 *
 * <p>
 * Pole {@code password} oznaczone jest {@code @JsonIgnore} — nigdy nie jest
 * zwracane w odpowiedziach JSON ze względów bezpieczeństwa.</p>
 *
 * <p>
 * Dostępne nazwane zapytania:</p>
 * <ul>
 * <li>{@code Users.findAll} — pobiera wszystkich użytkowników</li>
 * <li>{@code Users.findByUserId} — wyszukuje użytkownika po ID</li>
 * <li>{@code Users.findByUsername} — wyszukuje użytkownika po loginie</li>
 * <li>{@code Users.findByPassword} — wyszukuje użytkownika po haśle</li>
 * <li>{@code Users.findWorkers} — pobiera użytkowników z rolą pracownik (roleId
 * = 1)</li>
 * </ul>
 *
 * @author Radosław
 */
@Entity
@Table(name = "users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByUserId", query = "SELECT u FROM Users u WHERE u.userId = :userId"),
    @NamedQuery(name = "Users.findByUsername", query = "SELECT u FROM Users u WHERE u.username = :username"),
    @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password"),
    @NamedQuery(name = "Users.findWorkers", query = "SELECT u FROM Users u WHERE u.role = 1")})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Unikalny identyfikator użytkownika generowany automatycznie przez bazę
     * danych. Mapuje kolumnę {@code user_id} (klucz główny).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id")
    private Integer userId;

    /**
     * Login użytkownika służący do uwierzytelnienia w systemie. Pole wymagane,
     * długość od 1 do 30 znaków. Mapuje kolumnę {@code username}.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "username")
    private String username;

    /**
     * Hasło użytkownika przechowywane jako skrót BCrypt z pepper. Pole
     * wymagane, długość do 60 znaków (rozmiar skrótu BCrypt). Oznaczone
     * {@code @JsonIgnore} — nie jest zwracane w odpowiedziach JSON. Mapuje
     * kolumnę {@code password}.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "password")
    @JsonIgnore
    private String password;

    /**
     * Kolekcja pozycji sprzedaży zarejestrowanych przez tego użytkownika.
     * Relacja {@code @OneToMany} — jeden użytkownik może zarejestrować wiele
     * sprzedaży. Oznaczona {@code @JsonIgnore} aby zapobiec cyklicznej
     * serializacji JSON.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    @JsonIgnore
    private Collection<Sales> salesCollection;

    /**
     * Rola przypisana do użytkownika określająca zakres jego uprawnień w
     * systemie. Relacja {@code @ManyToOne} — wielu użytkowników może mieć tę
     * samą rolę. Mapuje kolumnę {@code role} (klucz obcy do tabeli
     * {@code roles}).
     */
    @JoinColumn(name = "role", referencedColumnName = "role_id")
    @ManyToOne(optional = false)
    private Roles role;

    /**
     * Konstruktor bezargumentowy wymagany przez specyfikację JPA.
     */
    public Users() {
    }

    /**
     * Konstruktor tworzący użytkownika z podanym identyfikatorem.
     *
     * @param userId Identyfikator użytkownika.
     */
    public Users(Integer userId) {
        this.userId = userId;
    }

    /**
     * Konstruktor tworzący użytkownika z podanymi podstawowymi danymi.
     *
     * @param userId Identyfikator użytkownika.
     * @param username Login użytkownika.
     * @param password Hasło użytkownika w postaci skrótu BCrypt.
     */
    public Users(Integer userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    /**
     * Zwraca identyfikator użytkownika.
     *
     * @return Identyfikator użytkownika lub {@code null} jeśli nie został
     * jeszcze przypisany.
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Ustawia identyfikator użytkownika.
     *
     * @param userId Nowy identyfikator użytkownika.
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Zwraca login użytkownika.
     *
     * @return Login użytkownika.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Ustawia login użytkownika.
     *
     * @param username Nowy login użytkownika. Musi mieć od 1 do 30 znaków.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Zwraca hasło użytkownika w postaci skrótu BCrypt. Metoda używana
     * wyłącznie wewnętrznie do weryfikacji hasła — nie jest eksponowana przez
     * JSON ze względu na adnotację {@code @JsonIgnore} na polu.
     *
     * @return Skrót BCrypt hasła użytkownika.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Ustawia hasło użytkownika.
     *
     * @param password Nowe hasło użytkownika w postaci skrótu BCrypt. Musi mieć
     * od 1 do 60 znaków.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Zwraca kolekcję pozycji sprzedaży zarejestrowanych przez tego
     * użytkownika. Metoda oznaczona {@code @XmlTransient} — nie jest
     * uwzględniana podczas serializacji XML.
     *
     * @return Kolekcja obiektów {@link Sales} powiązanych z tym użytkownikiem.
     */
    @XmlTransient
    public Collection<Sales> getSalesCollection() {
        return salesCollection;
    }

    /**
     * Ustawia kolekcję pozycji sprzedaży powiązanych z tym użytkownikiem.
     *
     * @param salesCollection Nowa kolekcja pozycji sprzedaży.
     */
    public void setSalesCollection(Collection<Sales> salesCollection) {
        this.salesCollection = salesCollection;
    }

    /**
     * Zwraca rolę przypisaną do użytkownika.
     *
     * @return Obiekt {@link Roles} reprezentujący rolę użytkownika.
     */
    public Roles getRole() {
        return role;
    }

    /**
     * Ustawia rolę użytkownika.
     *
     * @param role Obiekt {@link Roles} reprezentujący nową rolę użytkownika.
     */
    public void setRole(Roles role) {
        this.role = role;
    }

    /**
     * Zwraca hash kod obiektu oparty na identyfikatorze użytkownika.
     *
     * @return Hash kod oparty na polu {@code userId}.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    /**
     * Porównuje dwa obiekty {@code Users} na podstawie identyfikatora
     * użytkownika. Dwóch użytkowników jest równych jeśli posiadają ten sam
     * {@code userId}.
     *
     * @param object Obiekt do porównania.
     * @return {@code true} jeśli obiekty mają ten sam identyfikator,
     * {@code false} w przeciwnym razie.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    /**
     * Zwraca tekstową reprezentację obiektu zawierającą identyfikator
     * użytkownika.
     *
     * @return Łańcuch tekstowy w formacie {@code Users[ userId=X ]}.
     */
    @Override
    public String toString() {
        return "com.mycompany.servlets.servlets.Users[ userId=" + userId + " ]";
    }
}
