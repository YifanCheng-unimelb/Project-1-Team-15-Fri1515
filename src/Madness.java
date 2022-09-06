package src;

import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public class Madness extends Tetris{
    public Madness(TetrisGameCallback gameCallback, Properties properties) {
        super(gameCallback, properties);
    }

    private static int madSpeed;

    public static int getSpeed(int slowDown){
        madSpeed = ThreadLocalRandom.current().nextInt(slowDown, 2*slowDown);
        System.out.println(madSpeed);
        return madSpeed;
    }
}
