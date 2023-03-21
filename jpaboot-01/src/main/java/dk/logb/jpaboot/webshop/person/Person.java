package dk.logb.jpaboot.webshop.person;

import dk.logb.jpaboot.webshop.order.Order;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQuery(name = "Person.findByIdWithOrdersAndOrderLinesAndProducts",
        query = """
                SELECT DISTINCT p FROM Person p LEFT JOIN FETCH p.orders o 
                LEFT JOIN FETCH o.orderLines ol LEFT JOIN FETCH ol.product prod 
                LEFT JOIN FETCH prod.suppliers where p.id = :id
                """
        )
@NamedQuery(name = "Person.findByName", query = "SELECT p FROM Person p WHERE p.name = :name")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Basic(fetch = FetchType.LAZY)
    private String address;
    private LocalDateTime created = LocalDateTime.now();
    @OneToMany(mappedBy = "person", cascade = {CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH,
            CascadeType.DETACH}, fetch = FetchType.LAZY)
    Set<Order> orders = new HashSet<>();

    public Person() {
    }

    public Person(String name, String address) {
        this.name = name;
        this.address = address;
    }
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", created=" + created +
                '}';
    }

    //jpa aware equals and hashcode
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
