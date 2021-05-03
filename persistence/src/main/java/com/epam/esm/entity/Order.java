package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
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
}
