package jm.task.core.jdbc.dao;

import com.mysql.cj.protocol.x.Notice;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();

    @Override
    @Transactional
    public void createUsersTable() {
//        Session session = Util.getSessionFactory().openSession();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (id BIGSERIAL NOT NULL PRIMARY KEY" +
                    ", name VARCHAR(50)" +
                    ",lastName VARCHAR(50)" +
                    ",age INTEGER NOT NULL)")
                    .executeUpdate();
            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
            if(tx!=null){
                tx.rollback();
            }
        }finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = sessionFactory.openSession();) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users")
                    .executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица удалена! Если она была!");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = Util.getSessionFactory().openSession();) {
            session.beginTransaction();
            NativeQuery sqlQuery = session.createSQLQuery("INSERT INTO users (name, lastname, age) VALUES (:name,:lastName,:age)");
            sqlQuery.setParameter("name", name);
            sqlQuery.setParameter("lastName", lastName);
            sqlQuery.setParameter("age", age);
            sqlQuery.executeUpdate();
            session.getTransaction().commit();

        }catch (HibernateException e){
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.delete(session.get(User.class, id));
            transaction.commit();
        }catch(HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }

    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        try {
            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
            criteriaQuery.from(User.class);
            return session.createQuery(criteriaQuery).getResultList();
        } finally {
            session.close();
        }
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = sessionFactory.openSession();){
            session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE users")
                    .executeUpdate();
        }catch (HibernateException e){
            e.printStackTrace();
        }
    }
}
