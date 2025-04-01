package com.maybach7.formhandler.service;

import java.util.Random;

public class AuthService {
    private static final Random rand = new Random();

    public static String createLogin() {
        String s = "";
        for(int i = 0; i<10; i++) {
            s += (char)('a' + rand.nextInt(26));
        }
        return s;
    }

    public static String createPassword() {
        String s = "";
        for(int i = 0; i<10; i++) {
            int r = 33 + rand.nextInt(93);
            s += (char) r;
        }
        return s;
    }

    public static void main(String[] args) {
        System.out.println(createLogin());
        System.out.println(createPassword());
    }
}