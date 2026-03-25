/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package workers;

import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Klasa reprezentująca model danych dotyczący pracowników
 * @author Mateusz Gojny
 */
public class Workers {
    
    private String name;
    private String surname;
    private int id;
    private String login;
    private String password;

    /**
     * Podstawowy konstruktor obiektów klasy Workers przyjmujący parametry
     * @param name imię pracownika
     * @param surname nazwisko pracownika
     * @param id numer identyfikacyjny pracownika
     * @param login login do aplikacji pracownika
     * @param password hasło do aplikacji pracownika
     */
    public Workers(String name, String surname, int id, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.login = login;
        this.password = password;
    }

    /**
     * Pusty konstruktor klasy Workers
     */
    public Workers() {
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getSurname() {
        return surname;
    }

    /**
     *
     * @param surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getLogin() {
        return login;
    }

    /**
     *
     * @param login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Workers{" + "name=" + name + ", surname=" + surname + ", id=" + id + ", login=" + login + ", password=" + password + '}';
    }
    
    /**
     * Testowa metoda tworząca przykładowych pracowników firmy i zapisująca
     * ich do listy.
     * @return zwraca listę pracowników
     */
    public List<Workers> createExampleWorkers(){
        
        List<Workers> list = new ArrayList<>();
        
        list.add(new Workers("Admin", "Admin", 0, "Admin", "Admin"));
        list.add(new Workers("Mateusz", "Gojny", 1, "MatGoj1", "123456"));
        list.add(new Workers("Janusz", "Kownacki", 2, "JanKow2", "098765"));
        list.add(new Workers("Robert", "Franc", 3, "RobFra3", "456456"));
        
        return list;
    }
    
}
