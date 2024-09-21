package ru.box.tornadosbet.entity.postgresql;

import jakarta.persistence.*;

import java.util.Set;

@Entity(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private Long id;

    @Column(name = "country_name")
    private String countryName;

    @OneToMany(mappedBy = "country",
            fetch = FetchType.LAZY)
    private Set<Boxer> boxers;
}
