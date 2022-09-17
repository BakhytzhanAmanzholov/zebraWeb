package kz.jaguars.hackathon.models;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CoffeeHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String shortName; // находится возле Евразии? shortName - Евразия.
    private String address;
    private String workingHours;

    @ManyToMany
    @ToString.Exclude
    private Set<Staff> staffs = new HashSet<>();

    // заказы
    // маржинальность точки
    // объем за период
    // самый продаваемый продукт
    // статистика по рабчему времени
}
