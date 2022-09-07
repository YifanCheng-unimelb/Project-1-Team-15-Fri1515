package src;

import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;
/**
 * @Author Mingyue Jiang, Yutong CHEN, Yifan Cheng
 * @Date 2022 2022/8/30
 * @Version 1.0
 */

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
