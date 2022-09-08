package src;

import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;
/**
 * @Author Group 15 - Mingyue Jiang, Yutong CHEN, Yifan Cheng
 * @Date 2022 2022/9/2
 * @Version 1.0
 */

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
