package com.blog.blog_app.services;


public class CalculatedServicee {

    public static int add(int a, int b) {
        return a + b;
    }

    public static int sub(int a, int b) {
        return a - b;
    }

    public static int mul(int a, int b) {
        return a * b;
    }

    public static int div(int a, int b) {
        return a / b;
    }

    public static int mod(int a, int b) {
        return a % b;
    }

    public static int summition(int ...numbers) {
         int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return sum;
    }

}
