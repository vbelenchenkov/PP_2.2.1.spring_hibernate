package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.query.Query;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @Transactional
    public List<User> listUsers() {
        return sessionFactory.getCurrentSession().createQuery("select u from User u join fetch u.car", User.class).list();
    }

    @Override
    @Transactional
    public User getUserByCar(String model, int series) {
        String hql = "FROM User u JOIN FETCH u.car c WHERE c.model = :model AND c.series = :series";
        Query<User> query = sessionFactory.getCurrentSession().createQuery(hql, User.class);
        query.setParameter("model", model);
        query.setParameter("series", series);
        query.setMaxResults(1);
        return query.uniqueResult();
    }



}