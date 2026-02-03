package dao;

import model.Responsable;
import util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class ResponsableDAO {

    // Les responsables sont créés et gérés hors application (via script SQL).
    // Ce DAO est donc volontairement limité au opérations de lecture pour l'authentification.

    public Responsable findByEmailAndPassword(String email, String mdpHash) {

        Session session = HibernateUtil.openSession();
        Transaction trs = session.beginTransaction();

        Responsable r = session.createQuery(
                "from Responsable r where r.email = :email and r.mdpHash = :mdp",
                Responsable.class
        )
        .setParameter("email", email)
        .setParameter("mdp", mdpHash)
        .uniqueResult();

        trs.commit();
        session.close();

        return r;
    }

    public Responsable findById(int id) {

        Session session = HibernateUtil.openSession();
        Transaction trs = session.beginTransaction();

        Responsable r = session.get(Responsable.class, id);

        trs.commit();
        session.close();

        return r;
    }
}
