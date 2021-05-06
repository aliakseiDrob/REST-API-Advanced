package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id", "giftCertificates"})
@Table(name = "tag")
@SqlResultSetMapping(name = "mostWidelyUsedTagMapper",
        classes = {
                @ConstructorResult(targetClass = MostWidelyUsedTag.class,
                        columns = {
                                @ColumnResult(name = "tag_id", type = Long.class),
                                @ColumnResult(name = "tag_name", type = String.class),
                                @ColumnResult(name = "highest_cost", type = BigDecimal.class)
                        })})
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String name;
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<GiftCertificate> giftCertificates;

    public Tag(long id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }

    public Tag(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
