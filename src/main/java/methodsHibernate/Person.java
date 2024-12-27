package methodsHibernate;
import java.util.Date;

public class Person {
  
    private long id;
    private String name;
    private Date dob;
  
    public Person() {
    }
  
    public Person(String name, Date dob) {
        this.name = name;
        this.dob = dob;
    }
 
    public long getId() {
        return id;
    }
  
    public void setId(long id) {
        this.id = id;
    }
  
    public String getName() {
        return name;
    }
  
    public void setName(String name) {
        this.name = name;
    }
  
    public Date getDob() {
        return dob;
    }
  
    public void setDob(Date dob) {
        this.dob = dob;
    }
  
}