package com.epam.esm.audit;

import com.epam.esm.entity.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "audit")
public class Audit implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String operation;

    private LocalDateTime date;

    private String entity;

    @PrePersist
    private void setDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        setDate(localDateTime);
    }

    @Override
    public Long getId() {
        return id;
    }

}