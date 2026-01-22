package com.okhanzhin.hellojenkins;

/**
 *
 * @author okhanzhin on 22.01.2026
 */
public class App {
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        System.out.println("Hello World!");
        System.out.println("5 + 3 = " + calc.add(5, 3));
        System.out.println("10 - 4 = " + calc.subtract(10, 4));
    }
}
