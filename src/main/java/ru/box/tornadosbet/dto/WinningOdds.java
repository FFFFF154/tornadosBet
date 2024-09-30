package ru.box.tornadosbet.dto;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.box.tornadosbet.entity.postgresql.Boxer;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class WinningOdds  implements Serializable {
    private Double chanceOne;
    private Double chanceTwo;


    private Boxer firstBoxer;
    private Boxer secondBoxer;

    public WinningOdds(Boxer firstBoxer, Boxer secondBoxer) {
        this.firstBoxer = firstBoxer;
        this.secondBoxer = secondBoxer;
        this.chanceOne = ((firstBoxer.getHeight() +
                firstBoxer.getReach()) *
                firstBoxer.getWeight()) /
                (firstBoxer.getAge() * 500);
        this.chanceTwo = ((secondBoxer.getHeight() +
                secondBoxer.getReach()) *
                secondBoxer.getWeight()) /
                (secondBoxer.getAge() * 500);

//        if (Double.compare(chanceOne, chanceTwo) == 1){
//            this.chanceOne = chanceOne/chanceTwo;
//            this.chanceTwo = chanceTwo / chanceTwo;
//        } else {
//            this.chanceOne = chanceOne / chanceOne;
//            this.chanceTwo = chanceTwo / chanceOne;
//        }
    }

    @Override
    public String toString() {
        return chanceOne + " " + chanceTwo;
    }
}
