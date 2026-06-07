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
 * Encja JPA reprezentująca rolę użytkownika w systemie MyParts. Mapuje tabelę
 * {@code roles} w bazie danych MySQL.
 *
 * <p>
 * System przewiduje trzy role o następujących identyfikatorach:</p>
 * <ul>
 * <li>{@code roleId = 1} — Pracownik (podgląd produktów, obsługa kasy)</li>
 * <li>{@code roleId = 2} — Kierownik (zarządzanie produktami, raport
 * sprzedaży)</li>
 * <li>{@code roleId = 3} — Administrator (pełny dostęp, zarządzanie
 * użytkownikami)</li>
 * </ul>
 *
 * <p>
 * Relacja {@code @OneToMany} łączy rolę z kolekcją użytkowników ({@link Users})
 * posiadających tę rolę. Kolekcja oznaczona jest {@code @JsonIgnore} aby
 * zapobiec cyklicznej serializacji JSON.</p>
 *
 * <p>
 * Dostępne nazwane zapytania:</p>
 * <ul>
 * <li>{@code Roles.findAll} — pobiera wszystkie role</li>
 * <li>{@code Roles.findByRoleId} — wyszukuje rolę po ID</li>
 * <li>{@code Roles.findByRoleName} — wyszukuje rolę po nazwie</li>
 * </ul>
 *
 * @author Radosław
 */
@Entity
@Table(name = "roles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Roles.findAll", query = "SELECT r FROM Roles r"),
    @NamedQuery(name = "Roles.findByRoleId", query = "SELECT r FROM Roles r WHERE r.roleId = :roleId"),
    @NamedQuery(name = "Roles.findByRoleName", query = "SELECT r FROM Roles r WHERE r.roleName = :roleName")})
public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Unikalny identyfikator roli generowany automatycznie przez bazę danych.
     * Mapuje kolumnę {@code role_id} (klucz główny).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * Nazwa roli (np. "pracownik", "kierownik", "administrator"). Pole
     * wymagane, długość od 1 do 30 znaków. Mapuje kolumnę {@code role_name}.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "role_name")
    private String roleName;

    /**
     * Kolekcja użytkowników posiadających tę rolę. Relacja {@code @OneToMany} —
     * jedna rola może być przypisana wielu użytkownikom. Oznaczona
     * {@code @JsonIgnore} aby zapobiec cyklicznej serializacji JSON.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
    @JsonIgnore
    private Collection<Users> usersCollection;

    /**
     * Konstruktor bezargumentowy wymagany przez specyfikację JPA.
     */
    public Roles() {
    }

    /**
     * Konstruktor tworzący rolę z podanym identyfikatorem.
     *
     * @param roleId Identyfikator roli.
     */
    public Roles(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * Konstruktor tworzący rolę z podanym identyfikatorem i nazwą.
     *
     * @param roleId Identyfikator roli.
     * @param roleName Nazwa roli.
     */
    public Roles(Integer roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    /**
     * Zwraca identyfikator roli.
     *
     * @return Identyfikator roli lub {@code null} jeśli nie został jeszcze
     * przypisany.
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * Ustawia identyfikator roli.
     *
     * @param roleId Nowy identyfikator roli.
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * Zwraca nazwę roli.
     *
     * @return Nazwa roli (np. "pracownik", "kierownik", "administrator").
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Ustawia nazwę roli.
     *
     * @param roleName Nowa nazwa roli. Musi mieć od 1 do 30 znaków.
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Zwraca kolekcję użytkowników posiadających tę rolę. Metoda oznaczona
     * {@code @XmlTransient} i {@code @JsonIgnore} — nie jest uwzględniana
     * podczas serializacji XML ani JSON.
     *
     * @return Kolekcja obiektów {@link Users} z przypisaną tą rolą.
     */
    @XmlTransient
    @JsonIgnore
    public Collection<Users> getUsersCollection() {
        return usersCollection;
    }

    /**
     * Ustawia kolekcję użytkowników posiadających tę rolę.
     *
     * @param usersCollection Nowa kolekcja użytkowników.
     */
    public void setUsersCollection(Collection<Users> usersCollection) {
        this.usersCollection = usersCollection;
    }

    /**
     * Zwraca hash kod obiektu oparty na identyfikatorze roli.
     *
     * @return Hash kod oparty na polu {@code roleId}.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleId != null ? roleId.hashCode() : 0);
        return hash;
    }

    /**
     * Porównuje dwa obiekty {@code Roles} na podstawie identyfikatora roli.
     * Dwie role są równe jeśli posiadają ten sam {@code roleId}.
     *
     * @param object Obiekt do porównania.
     * @return {@code true} jeśli obiekty mają ten sam identyfikator,
     * {@code false} w przeciwnym razie.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Roles)) {
            return false;
        }
        Roles other = (Roles) object;
        if ((this.roleId == null && other.roleId != null) || (this.roleId != null && !this.roleId.equals(other.roleId))) {
            return false;
        }
        return true;
    }

    /**
     * Zwraca tekstową reprezentację obiektu zawierającą identyfikator roli.
     *
     * @return Łańcuch tekstowy w formacie {@code Roles[ roleId=X ]}.
     */
    @Override
    public String toString() {
        return "com.mycompany.servlets.servlets.Roles[ roleId=" + roleId + " ]";
    }
}
