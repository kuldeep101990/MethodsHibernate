package methodsHibernate;

import java.util.Date;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateInheritanceExample {

    public static void main(String[] args) {

        System.out.println("Initializing Hibernate configuration...");
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml"); 

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        Session session = sessionFactory.openSession();

        try {
            // Creating a new Person object
            System.out.println("Creating a new Person object...");
            Person person1 = new Person();
            person1.setName("John Doe");
            person1.setDob(new Date());

            // save(): Immediately generates an insert query and saves the object in the database, returns the identifier of the saved object.
            System.out.println("Saving Person object using save() method...");
            Transaction transaction = session.beginTransaction(); 
            session.save(person1); 
            transaction.commit(); 
            System.out.println("Person saved with ID: " + person1.getId());

            // get(): Fetches an object from the database using the identifier. Returns null if not found.
            System.out.println("Fetching Person using get() method...");
            Person fetchedPerson = (Person) session.get(Person.class, person1.getId());
            if (fetchedPerson != null) {
                System.out.println("Fetched person using get(): " + fetchedPerson.getName());
            } else {
                System.out.println("No person found with the given ID using get().");
            }
            
           // load(): Fetches a proxy object of the entity without hitting the database immediately, throws an exception if the entity does not exist when accessed.
            System.out.println("Fetching Person using load() method...");
            try {
                Person loadedPerson = (Person) session.load(Person.class, person1.getId());
                System.out.println("Fetched person using load(): " + loadedPerson.getName());
            } catch (Exception e) {
                System.out.println("Exception occurred while loading person: " + e.getMessage());
            }

            // persist(): Similar to save(), but does not return the identifier, follows JPA specification and ensures the object is only in the persistent state if the transaction is committed.
            System.out.println("Creating another Person object...");
            Person person2 = new Person();
            person2.setName("Jane Doe");
            person2.setDob(new Date());

            System.out.println("Saving Person object using persist() method...");
            transaction = session.beginTransaction();
            session.persist(person2); 
            transaction.commit();
            System.out.println("Person saved with ID: " + person2.getId());

            // update(): Updates the data of a persistent object in the database, should be used for objects already managed by the session.
            System.out.println("Updating Person object using update() method...");
            transaction = session.beginTransaction();
            person2.setName("Jane Smith"); 
            session.update(person2); 
            transaction.commit();
            System.out.println("Person updated: New Name = " + person2.getName());

            // saveOrUpdate(): Saves a new object or updates an existing one depending on its identifier, useful for handling both new and detached objects.
            System.out.println("Creating and saving/updating another Person using saveOrUpdate()...");
            Person person3 = new Person();
            person3.setName("Michael Johnson");
            person3.setDob(new Date());

            transaction = session.beginTransaction();
            session.saveOrUpdate(person3); 
            transaction.commit();
            System.out.println("Person saved/updated with ID: " + person3.getId());

            // merge(): Copies the state of a detached object into the persistent context, returns a managed instance of the object.
            System.out.println("Creating and merging a detached Person object...");
            Person detachedPerson = new Person();
            detachedPerson.setName("Emily Davis");
            detachedPerson.setDob(new Date());

            transaction = session.beginTransaction();
            Person mergedPerson = (Person) session.merge(detachedPerson); 
            transaction.commit();
            System.out.println("Merged Person details: Name = " + mergedPerson.getName() + ", DOB = " + mergedPerson.getDob());

            // delete(): Removes an object from the database.
            System.out.println("Deleting a Person object using delete() method...");
            transaction = session.beginTransaction();
            session.delete(person1); 
            transaction.commit();
            System.out.println("Person with ID " + person1.getId() + " deleted.");

            // evict(): Removes an object from the session cache but does not affect the database.
            System.out.println("Evicting a Person object from session cache...");
            session.evict(person2); 
            System.out.println("Person with ID " + person2.getId() + " evicted from cache.");

            // clear(): Clears the entire session cache.
            System.out.println("Clearing the entire session cache...");
            session.clear(); 
            System.out.println("Session cache cleared.");

        } catch (Exception e) {
            System.out.println("An error occurred: Rolling back transaction...");
            if (session.getTransaction() != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            // Close the session and SessionFactory
            System.out.println("Closing session and SessionFactory...");
            session.close(); 
            sessionFactory.close(); 
            System.out.println("Hibernate session and SessionFactory closed successfully.");
        }
    }
}
