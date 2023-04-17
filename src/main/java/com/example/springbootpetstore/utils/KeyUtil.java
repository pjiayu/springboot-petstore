package com.example.springbootpetstore.utils;

import java.util.Random;

/**
 * @author 皮皮皮
 * @date 2023/4/5 14:15
 */
public class KeyUtil {
    public static String keyUtils() {
        String str = "0123456789";
        StringBuilder st = new StringBuilder(4);
        for (int i = 0; i < 4; i++) {
            char ch = str.charAt(new Random().nextInt(str.length()));
            st.append(ch);
        }
        String findkey = st.toString().toLowerCase();
        return findkey;
    }
}
