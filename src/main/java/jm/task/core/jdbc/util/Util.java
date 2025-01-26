package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";

    private Util() {
    }

    public static Connection getConnection() {

        Properties prop = new Properties();

        try (InputStream inputStream = Util.class.getClassLoader().
                getResourceAsStream("application.properties")) {
            prop.load(inputStream);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            return DriverManager.getConnection(
                    prop.getProperty(URL_KEY),
                    prop.getProperty(USERNAME_KEY),
                    prop.getProperty(PASSWORD_KEY)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Session getConfiguration() {
        Configuration configuration = new Configuration().addAnnotatedClass(User.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        return sessionFactory.getCurrentSession();
    }
}
