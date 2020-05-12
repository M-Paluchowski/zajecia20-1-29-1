package excercises.one.product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Scanner;

public class ProductApp {

    private EntityManager entityManager;
    private Scanner scanner;

    public void run() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db");
        entityManager = entityManagerFactory.createEntityManager();
        scanner = new Scanner(System.in);

        while (true) {
            System.out.println("0. Wyjdź");
            System.out.println("1. Dodaj produkt");
            System.out.println("2. Wyświetl produkty");
            System.out.println("3. Wyświetl posortowane produkty");

            String option = scanner.nextLine();

            switch (option) {
                case "0":
                    entityManager.close();
                    scanner.close();
                    return;
                case "1":
                    insertProduct();
                    break;
                case "2":
                    displayAllProducts();
                    break;
                case "3":
                    displayAllProductsSorted();
                    break;
                default:
                    System.out.println("Nie ma takiej opcji");
            }
        }
    }

    private void displayAllProductsSorted() {
        System.out.println("Po czym chcesz sortować? 1 - nazwa, 2 - cena");
        String sortAttribute = scanner.nextLine();

        String sortField = "";
        if ("1".equalsIgnoreCase(sortAttribute)) {
            sortField = "name";
        } else if ("2".equalsIgnoreCase(sortAttribute)) {
            sortField = "price";
        } else {
            System.out.println("Nie ma takiego wyboru");
            return;
        }

        TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p ORDER BY p." + sortField, Product.class);
        query.getResultList()
                .forEach(System.out::println);
    }

    private void displayAllProducts() {
        TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p", Product.class);
        List<Product> resultList = query.getResultList();

        resultList.forEach(System.out::println);
    }

    private void insertProduct() {
        System.out.println("Podaj nazwę produktu");
        String productName = scanner.nextLine();

        System.out.println("Podaj cenę");
        double price = scanner.nextDouble();
        scanner.nextLine();

        Product product = new Product(productName, price);

        entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.getTransaction().commit();
    }
}
