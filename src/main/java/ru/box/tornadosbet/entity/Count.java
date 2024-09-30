package ru.box.tornadosbet.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Count {

    private Double balance;


    @Override
    public String toString() {
        return balance.toString();
    }
}
