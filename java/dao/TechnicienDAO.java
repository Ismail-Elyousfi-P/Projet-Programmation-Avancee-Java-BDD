package dao;

import model.Technicien;
import util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


public class TechnicienDAO {

    public List<Technicien> findAll() {

        Session session = HibernateUtil.openSession();
        Transaction trs = session.beginTransaction();

        List<Technicien> list =
            session.createQuery(
                "from Technicien t order by t.nom, t.prenom",
                Technicien.class
            ).getResultList();

        trs.commit();
        session.close();

        return list;
    }

    public Technicien findById(int id) {

        Session session = HibernateUtil.openSession();
        Transaction trs = session.beginTransaction();

        Technicien technicien = session.get(Technicien.class, id);

        trs.commit();
        session.close();

        return technicien;
    }

    public void save(Technicien technicien) {

        Session session = HibernateUtil.openSession();
        Transaction trs = session.beginTransaction();

        session.save(technicien);

        trs.commit();
        session.close();
    }

    public void update(Technicien technicien) {

        Session session = HibernateUtil.openSession();
        Transaction trs = session.beginTransaction();

        session.update(technicien);

        trs.commit();
        session.close();
    }

    public void delete(Technicien technicien) {

        Session session = HibernateUtil.openSession();
        Transaction trs = session.beginTransaction();

        session.delete(technicien);

        trs.commit();
        session.close();
    }


    public List<Technicien> findDisponibles() {

        Session session = HibernateUtil.openSession();
        Transaction trs = session.beginTransaction();

        List<Technicien> list =
            session.createQuery(
                "from Technicien t where t.disponible = true order by t.nom, t.prenom",
                Technicien.class
            ).getResultList();

        trs.commit();
        session.close();

        return list;
    }

    public List<Technicien> findByNom(String nom) {

        Session session = HibernateUtil.openSession();
        Transaction trs = session.beginTransaction();

        List<Technicien> list =
            session.createQuery(
                "from Technicien t where t.nom like :nom order by t.nom, t.prenom",
                Technicien.class
            )
            .setParameter("nom", "%" + nom + "%")
            .getResultList();

        trs.commit();
        session.close();

        return list;
    }
}
