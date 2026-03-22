package CompanyWorkers;


import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author mateu
 */
public class Workers {
    
    private String name;
    private String surname;
    private int id;
    private String login;
    private String password;

    public Workers(String name, String surname, int id, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public Workers() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Workers{" + "name=" + name + ", surname=" + surname + ", id=" + id + ", login=" + login + ", password=" + password + '}';
    }
    
    public List<Workers> createExampleWorkers(){
        
        List<Workers> list = new ArrayList<>();
        
        list.add(new Workers("Admin", "Admin", 0, "Admin", "Admin"));
        list.add(new Workers("Mateusz", "Gojny", 1, "MatGoj1", "123456"));
        list.add(new Workers("Janusz", "Kownacki", 2, "JanKow2", "098765"));
        list.add(new Workers("Robert", "Franc", 3, "RobFra3", "456456"));
        
        return list;
    }
    
}
