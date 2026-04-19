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
        prop.setProperty("hibernate.connection.url", "jdbc:mysql://192.168.0.73:3306/motorized_shop");
        //prop.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/motorized_shop");
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
        
        List<Users> u = null;
        
        try {
            u = session.createQuery("from Users", Users.class).list();
            
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
            
            return u;
        }
    }
    
    public Users getUserByName(String name) {
        Session session = sessionFactory.openSession();
        
        Users u = null;
        
        try {
            Query q = session.createNamedQuery("Users.findByUsername", Users.class);
            q.setParameter("username", name);
            u = (Users) q.getSingleResult();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
            
            return u;
        }
    }
    
    public List<Users> getUserWorkers() {
        Session session = sessionFactory.openSession();
        
        List<Users> u = null;
        try {
            Query q = session.createNamedQuery("Users.findWorkers", Users.class);
            u = (List<Users>) q.getResultList();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
            
            return u;
        }
    }
    
    public void saveUser(Users u) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        try {
            session.persist(u);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
    
    public void updateUser(Users u) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        try {
            session.update(u);

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
    
    public void removeUser(Users u) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        try {
            session.remove(u);

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
    
    public Roles getRoleById(int id) {
        Session session = sessionFactory.openSession();
        Roles r = null;
        try {
            Query q = session.createNamedQuery("Roles.findByRoleId", Roles.class);
            q.setParameter("roleId", id);
            r = (Roles) q.getSingleResult();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
            return r;
        }
    }
    
    /**
     * @author Mateusz Gojny
     * @return
     */
    public List<Products> getProducts() {
        Session session = sessionFactory.openSession();
        List<Products> list = session.createQuery("from Products", Products.class).list();
        session.close();
        return list;
    }
    
    /**
     * @author Mateusz Gojny
     * @param name
     * @return
     */
    public Categories getCategoryByName(String name) {
        Session session = sessionFactory.openSession();
        Query q = session.createNamedQuery("Categories.findByName", Categories.class);
        q.setParameter("name", name);
        Categories c = (Categories) q.getSingleResult();
        session.close();
        return c;
    }
    
    /**
     * @author Mateusz Gojny
     * @param p
     */
    public void saveProduct(Products p) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.persist(p);

        session.getTransaction().commit();
        session.close();
    }
    
    /**
     * @author Mateusz Gojny
     * @param name
     * @param brand
     * @param categoryName
     * @return
     */
    public Products findProduct(String name, String brand, String categoryName) {

        Session session = sessionFactory.openSession();

        try {
            Query<Products> q = session.createQuery(
                    "SELECT p FROM Products p WHERE p.name = :name AND p.brand = :brand AND p.categoryId.name = :cat",
                    Products.class
            );

            q.setParameter("name", name);
            q.setParameter("brand", brand);
            q.setParameter("cat", categoryName);

            List<Products> list = q.getResultList();

            if (list.isEmpty()) {
                return null;
            }

            return list.get(0);

        } finally {
            session.close();
        }
    }
    
    /**
     * @author Mateusz Gojny
     * @param product
     */
    public void saveOrUpdateProduct(Products product) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.saveOrUpdate(product);

        session.getTransaction().commit();
        session.close();
    }
    
    /**
     * @author Mateusz Gojny
     * @param name
     * @return
     */
    public Categories findCategoryByName(String name) {

        Session session = sessionFactory.openSession();

        try {
            Query<Categories> q = session.createQuery(
                    "FROM Categories c WHERE c.name = :name",
                    Categories.class
            );

            q.setParameter("name", name);

            return q.getSingleResult();

        } finally {
            session.close();
        }
    }
    
    public List<Sales> getSales() {
        Session session = sessionFactory.openSession();
        List<Sales> list = null;
        try {
            list = session.createQuery("from Sales", Sales.class).list();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
            return list;
        }
    }
    
    public void saveSale(Sales s) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.persist(s);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
    
    /**
     * @author Mateusz Gojny
     * @param p
     */
    public void updateProduct(Products p) {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    try {
        session.update(p);
        session.getTransaction().commit();
    } catch (Exception e) {
        session.getTransaction().rollback();
    } finally {
        session.close();
    }
}
    
    /**
     * @author Mateusz Gojny
     * @param id
     * @return
     */
    public Products getProductById(int id) {

    Session session = sessionFactory.openSession();

    try {
        return session.get(Products.class, id);
    } finally {
        session.close();
      }
    }
    
    /**
     * @author Mateusz Gojny
     * @param p
     */
    public void deleteProduct(Products p) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.remove(p);

        session.getTransaction().commit();
        session.close();
    }
    
    /**
     * 
     * @author Mateusz Gojny
     * @param name
     * @return
     * 
     */
    public Categories findManyCategoriesByName(String name) {
    Session session = sessionFactory.openSession();
    try {
        Query<Categories> q = session.createQuery(
            "FROM Categories c WHERE c.name = :name",
            Categories.class
        );
        q.setParameter("name", name);
        List<Categories> result = q.getResultList();
        return result.isEmpty() ? null : result.get(0);
    } finally {
        session.close();
    }
}
    
}


