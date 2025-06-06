package utils;


import java.util.concurrent.ThreadLocalRandom;

public final class RandomUtil {
    public static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt((max - min) + 1) + min;
    }

    public static boolean getRandomBool(double p) {
        return (ThreadLocalRandom.current().nextDouble() < p);
    }
}
