package dao;

import model.Batiment;
import util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class BatimentDAO {

    public List<Batiment> findAll() {

        Session session = HibernateUtil.openSession();
        Transaction trs = session.beginTransaction();

        List<Batiment> list =
            session.createQuery("from Batiment", Batiment.class)
                   .getResultList();

        trs.commit();
        session.close();

        return list;
    }

    public Batiment findById(int id) {

        Session session = HibernateUtil.openSession();
        Transaction trs = session.beginTransaction();

        Batiment b = session.get(Batiment.class, id);

        trs.commit();
        session.close();

        return b;
    }

    public void save(Batiment entity) {

        Session session = HibernateUtil.openSession();
        Transaction trs = session.beginTransaction();

        session.save(entity);

        trs.commit();
        session.close();
    }

    public void update(Batiment entity) {

        Session session = HibernateUtil.openSession();
        Transaction trs = session.beginTransaction();

        session.update(entity);

        trs.commit();
        session.close();
    }

    public void delete(Batiment entity) {

        Session session = HibernateUtil.openSession();
        Transaction trs = session.beginTransaction();

        session.delete(entity);

        trs.commit();
        session.close();
    }
}
