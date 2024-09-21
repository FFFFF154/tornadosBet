package ru.box.tornadosbet.entity.postgresql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "boxer")
public class Boxer { //TODO добавить боксеров

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "firstName")
    private String firstName;

    @Column(name = "secondName")
    private String secondName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "photo")
    private String photo; // TODO Посмотреть как реализовать в entity

    @Column(name = "height")
    private Double height;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "reach")
    private Double reach;

    @Column(name = "division")
    private String division;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")//TODO рассмотреть ManyToOne
    private Country country;
}
