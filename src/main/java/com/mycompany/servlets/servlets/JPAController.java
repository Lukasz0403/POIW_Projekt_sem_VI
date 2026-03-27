/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servlets.servlets;

import java.util.List;
import java.util.Properties;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

/**
 *
 * @author Radosław
 */
public class JPAController {
    
        SessionFactory sessionFactory;
    
    
    public void start() {
        
        Properties prop = new Properties();
        //to co było poprzednie, ale musiałem zmienić adres na localhost bo krzaczyło
        //prop.setProperty("hibernate.connection.url", "jdbc:mysql://192.168.0.73:3306/motorized_shop");
        prop.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/motorized_shop");
        prop.setProperty("hibernate.connection.username", "motor_access");
        prop.setProperty("hibernate.connection.password", "12345");
        prop.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        prop.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        prop.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        prop.setProperty("hibernate.hbm2ddl.auto", "update");
        prop.setProperty("hibernate.show_sql", "true");


        Configuration configuration = new Configuration().addProperties(prop);
        configuration.addAnnotatedClass(Users.class);
        configuration.addAnnotatedClass(Sales.class);
        configuration.addAnnotatedClass(Products.class);
        configuration.addAnnotatedClass(Categories.class);
        configuration.addAnnotatedClass(Roles.class);
     
        sessionFactory = configuration.buildSessionFactory();
    }
    
    public List<Users> getUsers() {
        Session session = sessionFactory.openSession();
        List<Users> students = session.createQuery("from Users", Users.class).list();
        session.close();
        return students;
    }
    
    public Users getUserByName(String name) {
        Session session = sessionFactory.openSession();
        Query q = session.createNamedQuery("Users.findByUsername", Users.class);
        q.setParameter("username", name);
        Users u = (Users) q.getSingleResult();
        
        System.out.println(u);
        
        return u;
    }
    
    public void showUsers() {
        Session session = sessionFactory.openSession();
        List<Users> students = session.createQuery("from Users", Users.class).list();

        for (Users student : students) {
            System.out.println(student);
        }
        session.close();
    }
    
    public List<Products> getProducts() {
    Session session = sessionFactory.openSession();
    List<Products> list = session.createQuery("from Products", Products.class).list();
    session.close();
    return list;
}
    
}
