package com.example.demo;

import jdk.swing.interop.SwingInterOpUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.provider.HibernateUtils;

import javax.persistence.Embeddable;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

        try(Session session = com.example.demo.HibernateUtil.getSessionFactory().openSession()) {
            // наполняем базу данными
            session.beginTransaction();

            Employee employee1 = new Employee();
            employee1.setId(1);
            employee1.setName("Tom");
            employee1.setSalary(120000);
            employee1.setEmail("tom@gmail.com");

            Employee employee2 = new Employee();
            employee2.setId(2);
            employee2.setName("Den");
            employee2.setSalary(150000);
            employee2.setEmail("den@gmail.com");

            Employee employee3 = new Employee();
            employee3.setId(3);
            employee3.setName("Markus");
            employee3.setSalary(90000);
            employee3.setEmail("markus@gmail.com");

            session.save(employee1);
            session.save(employee2);
            session.save(employee3);

            session.getTransaction().commit();

        } catch (HibernateException ex) {
            ex.printStackTrace();
        }

        try (Session session = com.example.demo.HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Employee> criteriaQuery = builder.createQuery(Employee.class);
            Root<Employee> root = criteriaQuery.from(Employee.class);
            criteriaQuery.select(root);

            // метод where() добавляет условие фильтрации записей
            criteriaQuery.where(builder.equal(root.get("id"), 1));
//            criteriaQuery.where(builder.greaterThan(root.get("salary"), 130000));

            // метод orderBy() сортирует данные
            criteriaQuery.orderBy(builder.asc(root.get("salary")));

            // метод createQuery может получать как обычные запросы, так и запросы в виде CriteriaQuery
            Query<Employee> query = session.createQuery(criteriaQuery);
            List<Employee> empList = query.list();
            System.out.println("______________________________________");
            empList.forEach(System.out::println);



        } catch (HibernateException e) {
            e.printStackTrace();
        }

        // обновление данных через CriteriaApi
        try (Session session = com.example.demo.HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<Employee> criteriaUpdate = builder.createCriteriaUpdate(Employee.class);
            Root<Employee> rootUpdate = criteriaUpdate.from(Employee.class);
            criteriaUpdate.set("salary", 222000);
            criteriaUpdate.where(builder.equal(rootUpdate.get("salary"), 90000));

            Transaction transaction = session.beginTransaction();
            session.createQuery(criteriaUpdate).executeUpdate();
            transaction.commit();


            CriteriaBuilder builderAfterUpd = session.getCriteriaBuilder();
            CriteriaQuery<Employee> criteriaQueryAfterUpd = builderAfterUpd.createQuery(Employee.class);
            Root<Employee> rootAfterUpd = criteriaQueryAfterUpd.from(Employee.class);
            criteriaQueryAfterUpd.select(rootAfterUpd);

            Query<Employee> queryAfterUpd = session.createQuery(criteriaQueryAfterUpd);
            List<Employee> empListAfterUpd = queryAfterUpd.list();
            System.out.println("______________________________________");
            empListAfterUpd.forEach(System.out::println);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

}
