package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    public Util(){}
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1q2w";
    private static SessionFactory sessionFactory;

    public static Connection getConnection(){
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(URL,USER,PASSWORD);
            if(!conn.isClosed()){
                System.out.println("Соединение с БД установленно!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
//
//    public static SessionFactory getSession(){
//        Configuration config = new Configuration()
//                .addAnnotatedClass(User.class);
//        SessionFactory sessionFactory = config.buildSessionFactory();
//        if(sessionFactory != null){
//            System.out.println("Соединение установлено! ");
//        }
//     return sessionFactory;
//    }

    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            try {
                Properties properties = new Properties();
                properties.put(Environment.DRIVER, "org.postgresql.Driver");
                properties.put(Environment.URL, URL);
                properties.put(Environment.USER, USER);
                properties.put(Environment.PASS, PASSWORD);
                properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
                properties.put(Environment.HBM2DDL_AUTO, "update");
                properties.put(Environment.SHOW_SQL, true);
                properties.put(Environment.FORMAT_SQL, true);
                properties.put(Environment.USE_SQL_COMMENTS, true);

                Configuration configuration = new Configuration();
                configuration.setProperties(properties);
                configuration.addAnnotatedClass(User.class);

                sessionFactory = configuration
                        .buildSessionFactory(new StandardServiceRegistryBuilder()
                                .applySettings(configuration.getProperties())
                                .build());
            } catch (Exception e) {
                System.err.println("SessionFactory creation failed: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}