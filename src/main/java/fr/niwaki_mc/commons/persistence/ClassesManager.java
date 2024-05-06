package fr.niwaki_mc.commons.persistence;

import fr.niwaki_mc.commons.models.Classes;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ClassesManager {

    public void insertDefaultClasses() {
        String[] defaultClassNames = {"Mage", "Gardien", "Clerc", "Guerrier"};

        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            for (String className : defaultClassNames) {
                if (!isClassExists(className, session)) {
                    session.persist(new Classes(className));
                }
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            // Handle exception
        }
    }

    public boolean isClassExists(String className, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<Classes> root = countQuery.from(Classes.class);
        countQuery.select(builder.count(root)).where(builder.equal(root.get("Name"), className));

        Long count = session.createQuery(countQuery).getSingleResult();
        return count > 0;
    }

    public List<Classes> getAllClasses() {
        List<Classes> resultList;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            CriteriaQuery<Classes> query = session.getCriteriaBuilder().createQuery(Classes.class);
            Root<Classes> rootEntry = query.from(Classes.class);
            CriteriaQuery<Classes> all = query.select(rootEntry);
            Query<Classes> allQuery = session.createQuery(all);
            resultList = allQuery.getResultList();
        }
        return resultList;
    }

}
