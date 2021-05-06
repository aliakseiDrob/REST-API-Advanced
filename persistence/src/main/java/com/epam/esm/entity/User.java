package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(exclude = {"id", "orders"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String name;
    @JsonBackReference
    @OneToMany(mappedBy = "user")
    private Set<Order> orders;

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(long id, String name, Set<Order> orders) {
        this.id = id;
        this.name = name;
        this.orders = orders;
    }

    public User(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
