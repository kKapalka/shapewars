package pl.edu.pwsztar.shapewars.entities.enums;

import java.util.Arrays;
import java.util.Random;

public class Colors {
    public enum ColorType {
        RED(0),
        BLUE(1),
        GREEN(2),
        YELLOW(3),
        ORANGE(4),
        PURPLE(5),
        WHITE(6),
        BLACK(7),
        RAINBOW(8);

        int value;
        private ColorType(int value){
            this.value=value;
        }
        public static ColorType getByValue(int value){
            return Arrays.stream(values())
                    .filter(colorType -> colorType.value==value).findFirst().get();
        }
        public static ColorType getRandom(){
            return values()[new Random().nextInt(values().length)];
        }
        }

}
