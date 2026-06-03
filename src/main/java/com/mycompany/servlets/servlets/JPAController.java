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
    
    /**
     * Inicjalizuje hibernate w celu komunikacji z bazą danych.
     * 
     */
    public void start() { 
            
        Properties prop = new Properties();
//        prop.setProperty("hibernate.connection.url", "jdbc:mysql://192.168.0.73:3306/motorized_shop");
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
        configuration.addAnnotatedClass(Transactions.class);
     
        sessionFactory = configuration.buildSessionFactory();
    }
   
    /**
     * Pobiera listę uzytkowników z bazy danych.
     * 
     * 
     * @return Lista obiektów {@Link Users} zawierająca wszystkich użytkowników  w bazie.
     */
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
    
    /**
     * Pobiera z bazy konkretnego użytkownika po jego loginie.
     * 
     * 
     * @param name login użytkownika do znalezienia.
     * @return Objekt {@Link Users} odpowiadający nazwie uzytkownika.
     */
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
    
    /**
     * Pobiera wsystkich użytkowników z bazy, będacych na stanowisku pracownika,
     * 
     * @return Lista objektów {@Link Users} odpowiadająca uzytkownikom na stanowisku pracownika.
     */
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
    
    /**
     * Zapisuje użytkownika do bazy danych.
     * 
     * @param u Objekt uzytkownika który ma zostać zapisany.
     */
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
    /**
     * Aktualizuje konkretnego użytkownika jeśli taki istnieje.
     * 
     * @param u Objekt użytkownika, który ma zostac zaktualizowany w bazie.
     */
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
    
    /**
     * Usuwa konkretnego uzytkownika z bazy danych.
     * 
     * @param u Objekt użytkownika który ma zostać usunięty z bazy.
     */
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
    
    /**
     * Zwraca konkretne stanowisko po jego identyfikatorze w bazie.
     * 
     * @param id identyfikator stanowiska.
     * @return Objekt {@Link Roles} Stanowisko o wybranym identyfikatorze.
     */
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
     * Pobiera listę wszystkich produktów z bazy danych.
     *
     * @author Mateusz Gojny
     * @return Lista obiektów {@link Products} reprezentujących wszystkie produkty
     *         dostępne w systemie.
     */
    public List<Products> getProducts() {
        Session session = sessionFactory.openSession();
        List<Products> list = session.createQuery("from Products", Products.class).list();
        session.close();
        return list;
    }
    
    /**
     * Wyszukuje kategorię produktów na podstawie jej nazwy.
     * Korzysta z nazwanego zapytania {@code Categories.findByName}.
     *
     * @author Mateusz Gojny
     * @param name Nazwa kategorii do wyszukania.
     * @return Obiekt {@link Categories} odpowiadający podanej nazwie.
     * @throws jakarta.persistence.NoResultException jeśli kategoria o podanej
     *         nazwie nie istnieje w bazie danych.
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
     * Zapisuje nowy produkt w bazie danych.
     * Operacja jest wykonywana w ramach transakcji — w przypadku powodzenia
     * zmiany są zatwierdzane, a sesja zamykana.
     *
     * @author Mateusz Gojny
     * @param p Obiekt {@link Products} do zapisania w bazie danych.
     */
    public void saveProduct(Products p) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(p);
        session.getTransaction().commit();
        session.close();
    }
    
    /**
     * Wyszukuje produkt w bazie danych na podstawie nazwy, marki oraz nazwy kategorii.
     * Zwraca pierwszy pasujący wynik lub {@code null} jeśli produkt nie istnieje.
     *
     * @author Mateusz Gojny
     * @param name         Nazwa produktu.
     * @param brand        Marka produktu.
     * @param categoryName Nazwa kategorii, do której należy produkt.
     * @return Obiekt {@link Products} jeśli produkt został znaleziony,
     *         w przeciwnym razie {@code null}.
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
     * Zapisuje lub aktualizuje produkt w bazie danych.
     * Jeśli produkt już istnieje (posiada przypisane ID), zostaje zaktualizowany.
     * W przeciwnym razie zostaje utworzony nowy rekord.
     * Operacja jest wykonywana w ramach transakcji.
     *
     * @author Mateusz Gojny
     * @param product Obiekt {@link Products} do zapisania lub zaktualizowania.
     */
    public void saveOrUpdateProduct(Products product) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(product);
        session.getTransaction().commit();
        session.close();
    }
    
    /**
     * Wyszukuje kategorię produktów na podstawie jej nazwy przy użyciu zapytania HQL.
     * W odróżnieniu od {@link #getCategoryByName(String)}, ta metoda rzuca wyjątek
     * {@code NoResultException} jeśli kategoria nie istnieje.
     *
     * @author Mateusz Gojny
     * @param name Nazwa kategorii do wyszukania.
     * @return Obiekt {@link Categories} odpowiadający podanej nazwie.
     * @throws jakarta.persistence.NoResultException jeśli kategoria o podanej
     *         nazwie nie istnieje w bazie danych.
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
    
    /**
     * Pobiera listę wszystkich rekordów sprzedaży (pozycji koszyka) z bazy danych.
     * Operacja jest realizowana przy użyciu zapytania HQL. W przypadku błędu
     * aktywna transakcja sesji zostaje wycofana.
     *
     * @author Łukasz Motyka
     * @return Lista obiektów {@link Sales} reprezentujących wszystkie pozycje sprzedaży,
     *         lub {@code null} w przypadku wystąpienia wyjątku.
     */
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
    
    /**
     * Zapisuje nową pozycję sprzedaży w bazie danych.
     * Operacja jest wykonywana w ramach transakcji Hibernate — w przypadku powodzenia
     * zmiany są trwale zatwierdzane, a w razie błędu transakcja zostaje wycofana.
     *
     * @author Łukasz Motyka
     * @param s Obiekt {@link Sales} zawierający dane pozycji sprzedaży do utrwalenia w bazie.
     */
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
     * Zapisuje nagłówek nowej transakcji finansowej w bazie danych.
     * Metoda samodzielnie zarządza pełnym cyklem życia transakcji (rozpoczęcie, 
     * zatwierdzenie), a w przypadku wystąpienia błędu bezpiecznie wycofuje zmiany (rollback).
     *
     * @author Łukasz Motyka
     * @param transaction Obiekt {@link Transactions} reprezentujący transakcję do zapisania.
     */    
    public void saveTransaction(Transactions transaction) {
        Session session = sessionFactory.openSession();
        try {
            session.getTransaction().begin();
            session.persist(transaction);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    } 
    
    /**
     * Aktualizuje dane istniejącego produktu w bazie danych.
     * Operacja jest wykonywana w ramach transakcji — w przypadku błędu
     * transakcja jest wycofywana (rollback).
     *
     * @author Mateusz Gojny
     * @param p Obiekt {@link Products} z zaktualizowanymi danymi do zapisania.
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
     * Pobiera produkt z bazy danych na podstawie jego identyfikatora.
     * Zwraca {@code null} jeśli produkt o podanym ID nie istnieje.
     *
     * @author Mateusz Gojny
     * @param id Unikalny identyfikator produktu ({@code product_id}).
     * @return Obiekt {@link Products} odpowiadający podanemu ID,
     *         lub {@code null} jeśli produkt nie został znaleziony.
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
     * Usuwa wskazany produkt z bazy danych.
     * Operacja jest wykonywana w ramach transakcji — po pomyślnym usunięciu
     * transakcja jest zatwierdzana, a sesja zamykana.
     *
     * @author Mateusz Gojny
     * @param p Obiekt {@link Products} do usunięcia z bazy danych.
     */
    public void deleteProduct(Products p) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.remove(p);
        session.getTransaction().commit();
        session.close();
    }
    
    /**
     * Wyszukuje kategorię produktów na podstawie jej nazwy, zwracając {@code null}
     * zamiast rzucać wyjątek gdy kategoria nie istnieje.
     * Metoda jest bezpieczna dla przypadków gdy podana nazwa kategorii
     * może nie mieć odpowiednika w bazie danych (np. podczas importu CSV).
     *
     * @author Mateusz Gojny
     * @param name Nazwa kategorii do wyszukania.
     * @return Obiekt {@link Categories} odpowiadający podanej nazwie,
     *         lub {@code null} jeśli kategoria nie została znaleziona.
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