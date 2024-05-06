package fr.niwaki_mc.commons.persistence;

import fr.niwaki_mc.commons.models.Classes;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import static org.hibernate.cfg.JdbcSettings.*;

public class HibernateUtils {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration()
                    .addAnnotatedClass(Classes.class)
                    .configure()
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Echec de la création d'une SessionFactory." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
