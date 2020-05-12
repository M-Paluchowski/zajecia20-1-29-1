package examples;

import examples.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        User user = new User();
        user.setName("Marcin");
        user.setCreationDate(LocalDateTime.now());
        user.setType(UserType.GOLD);
        entityManager.persist(user);
        entityManager.getTransaction().commit();

//        entityManager.getTransaction().begin();
//        user.setName("Rafał");
//        entityManager.getTransaction().commit();

//        user.setName("Witold");
//        entityManager.getTransaction().begin();
//        entityManager.merge(user);
//        entityManager.getTransaction().commit();

//        User user1 = entityManager.find(User.class, 1L);

//        entityManager.getTransaction().begin();
//        entityManager.remove(user1);
//        entityManager.getTransaction().commit();

//        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.name LIKE '%in'");
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.name LIKE '%in'", User.class);

        List<User> resultList = query.getResultList();
//        List resultList = query.getResultList();

        for (User result : resultList) {
//            User userFromResult = (User) result;
            System.out.println(user.getName());
        }

        entityManager.close();
        entityManagerFactory.close();
    }
}
