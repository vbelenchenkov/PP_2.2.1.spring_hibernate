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

    private static final String SELECT_USERS =
            "SELECT u FROM User u JOIN FETCH u.car";

    private static final String GET_USER_BY_CAR =
            "FROM User u JOIN FETCH u.car c WHERE c.model = :model AND c.series = :series";

    @Override
    public void addUser(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @Transactional
    public List<User> listUsers() {
        return sessionFactory.getCurrentSession()
                .createQuery(SELECT_USERS, User.class)
                .list();
    }

    @Override
    @Transactional
    public User getByCar(String model, int series) {
        Query<User> query = sessionFactory.getCurrentSession()
                .createQuery(GET_USER_BY_CAR, User.class);
        query.setParameter("model", model);
        query.setParameter("series", series);
        query.setMaxResults(1);
        return query.uniqueResult();
    }
}