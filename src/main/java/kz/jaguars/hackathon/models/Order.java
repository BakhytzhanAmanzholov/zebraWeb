package kz.jaguars.hackathon.models;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Staff staff;
    @ManyToOne
    private Account account;
    private Date date;
    @ManyToMany
    @ToString.Exclude
    private List<Product> products = new ArrayList<>();
}
