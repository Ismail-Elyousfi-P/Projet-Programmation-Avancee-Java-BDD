package dao;

import model.Intervention;
import model.Technicien;
import model.Batiment;
import util.HibernateUtil;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.util.List;


public class InterventionDAO {

    public List<Intervention> findAll() {

        Session session = HibernateUtil.openSession();
        Transaction trs = session.beginTransaction();

        List<Intervention> list =
            session.createQuery(
                "from Intervention i order by i.dateIntervention desc",
                Intervention.class
            ).getResultList();

        trs.commit();
        session.close();

        return list;
    }

    public Intervention findById(int id) {

        Session session = HibernateUtil.openSession();
        Transaction trs = session.beginTransaction();

        Intervention intervention = session.get(Intervention.class, id);

        trs.commit();
        session.close();

        return intervention;
    }

    public void save(Intervention intervention) {

        Session session = HibernateUtil.openSession();
        Transaction trs = session.beginTransaction();

        session.save(intervention);

        trs.commit();
        session.close();
    }

    public void update(Intervention intervention) {

        Session session = HibernateUtil.openSession();
        Transaction trs = session.beginTransaction();

        session.update(intervention);

        trs.commit();
        session.close();
    }

    public void delete(Intervention intervention) {

        Session session = HibernateUtil.openSession();
        Transaction trs = session.beginTransaction();

        session.delete(intervention);

        trs.commit();
        session.close();
    }


    public List<Intervention> findByStatut(String statut) {

        Session session = HibernateUtil.openSession();
        Transaction trs = session.beginTransaction();

        List<Intervention> list =
            session.createQuery(
                "from Intervention i where i.statut = :statut order by i.dateIntervention desc",
                Intervention.class
            )
            .setParameter("statut", statut)
            .getResultList();

        trs.commit();
        session.close();

        return list;
    }

    public List<Intervention> findByTechnicien(Technicien technicien) {

        Session session = HibernateUtil.openSession();
        Transaction trs = session.beginTransaction();

        List<Intervention> list =
            session.createQuery(
                "from Intervention i where i.technicien = :tech order by i.dateIntervention desc",
                Intervention.class
            )
            .setParameter("tech", technicien)
            .getResultList();

        trs.commit();
        session.close();

        return list;
    }

    public List<Intervention> findByBatiment(Batiment batiment) {

        Session session = HibernateUtil.openSession();
        Transaction trs = session.beginTransaction();

        List<Intervention> list =
            session.createQuery(
                "from Intervention i where i.batiment = :bat order by i.dateIntervention desc",
                Intervention.class
            )
            .setParameter("bat", batiment)
            .getResultList();

        trs.commit();
        session.close();

        return list;
    }
    
    public List<Intervention> findByCriteria(
            Technicien technicien,
            Batiment batiment,
            String statut,
            Date dateIntervention
    ) {

        try (Session session = HibernateUtil.openSession()) {

            StringBuilder hql = new StringBuilder(
                    "from Intervention i where 1=1"
            );

            if (technicien != null) {
                hql.append(" and i.technicien = :technicien");
            }

            if (batiment != null) {
                hql.append(" and i.batiment = :batiment");
            }

            if (statut != null && !statut.isBlank()) {
                hql.append(" and i.statut = :statut");
            }

            if (dateIntervention != null) {
                hql.append(" and date(i.dateIntervention) = :dateIntervention");
            }

            Query<Intervention> query = session.createQuery(hql.toString(), Intervention.class);

            if (technicien != null) {
                query.setParameter("technicien", technicien);
            }

            if (batiment != null) {
                query.setParameter("batiment", batiment);
            }

            if (statut != null && !statut.isBlank()) {
                query.setParameter("statut", statut);
            }

            if (dateIntervention != null) {
                query.setParameter(
                    "dateIntervention",
                    new java.sql.Date(dateIntervention.getTime())
                );
            }

            return query.getResultList();
        }
    }

}
