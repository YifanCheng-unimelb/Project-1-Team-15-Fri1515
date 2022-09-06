package src;

import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public class Medium extends Tetris{
    public Medium(TetrisGameCallback gameCallback, Properties properties) {
        super(gameCallback, properties);
    }

    private static int mediumSpeed;

    public static int getSpeed(int slowDown){
        mediumSpeed = (int)(slowDown*0.8);
        System.out.println(mediumSpeed);
        return mediumSpeed;
    }
}
