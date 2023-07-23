package com.haiprj.demogame.utils;

public class Log {
    public static void log(Object... data) {
        for (Object o : data) {
            System.out.println(o.toString());
        }
    }
}
