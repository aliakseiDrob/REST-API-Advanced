package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@EqualsAndHashCode(exclude = {"id","certificate"})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "date", updatable = false)
    private LocalDateTime orderDate;
    @Column(name = "order_cost", updatable = false)
    private BigDecimal orderCost;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "certificate_id", nullable = false)
    private GiftCertificate certificate;

    public Order(long id, LocalDateTime orderDate, BigDecimal orderCost, User user) {
        this.id = id;
        this.orderDate = orderDate;
        this.orderCost = orderCost;
        this.user = user;
    }

    @PrePersist
    private void setParameters() {
        setDate();
        setCost();
    }

    private void setDate() {
        LocalDateTime localDate = LocalDateTime.now();
        setOrderDate(localDate);
    }

    private void setCost() {
        setOrderCost(getCertificate().getPrice());
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", orderCost=" + orderCost +
                ", user=" + user +
                '}';
    }
}
