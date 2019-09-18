package util;

import java.util.Random;

public class ToolUtil {

    public static String getRandomNum(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int i1 = random.nextInt(10);
            stringBuilder.append(i1);
        }
        return stringBuilder.toString();
    }

}
