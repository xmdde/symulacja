package com.example.symulacja;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.Random;

/**
 * Klasa obsługująca pola tworzące planszę w symualcji.
 */
public class MyTile extends Rectangle implements Runnable {

    Random generator;
    final Object locker;
    float r = 0;
    float g = 0;
    float b = 0;
    int k;
    double p;
    MyTile[] neighbours = new MyTile[4];

    /**
     * Konstruktor pól. Dla każdego pola tworzony jest wątek, a następnie uruchamiany.
     * @param x współrzędna x lewego górnego wierzchołka prostokąta
     * @param y współrzędna y lewego górnego wierzchołka prostokąta
     * @param width szerokość prostokąta
     * @param height wysokość prostokąta
     * @param k szybkośc działania, opoznienie jest losowane z
     * @param p prawdopodobienstwo zmiany koloru na losowy z zakresu (0,1)
     * @param generator obiekt Random generator liczb losowych
     * @param locker obiekt, na którym wykonuje się synchronizacja
     */
    MyTile(double x, double y, double width, double height, int k, double p, Random generator, Object locker) {
        super(x, y, width, height);
        this.k = k;
        this.p = p;
        this.generator = generator;
        this.locker = locker;
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Funkcja zmieniająca kolor pola na losowy różniący się od obecnego.
     */
    public void setRandomColor() {
        float tmp = r;
        while(tmp == r) {
            tmp = generator.nextFloat();
        }
        r = tmp;
        tmp = g;
        while(tmp == g) {
            tmp = generator.nextFloat();
        }
        g = tmp;
        tmp = b;
        while(tmp == b) {
            tmp = generator.nextFloat();
        }
        b = tmp;
        Color color = new Color(r, g, b,1.0);
        this.setFill(color);
    }

    /**
     * Funkcja zmieniająca kolor pola na średnią kolorów swoich sąsiadów.
     */
    public void setNbhColor() {
        float tmpr = 0, tmpg = 0 , tmpb = 0;

        if (neighbours[0]!=null && neighbours[1]!=null && neighbours[2]!=null && neighbours[3]!=null) {
            for (int i = 0; i < 4; i++) {
                tmpr += neighbours[i].r;
                tmpg += neighbours[i].g;
                tmpb += neighbours[i].b;
            }
            this.r = tmpr / 4;
            this.g = tmpg / 4;
            this.b = tmpb / 4;
            Color color = new Color(r, g, b, 1.0);
            this.setFill(color);
        }
    }

    /**
     * Funkcja run wykonująca się przez cały czas działania programu.
     * Przy każdym wykonaniu losowane jest opóźnienie z przedziału [0.5k, 1.5k] milisekund,
     * a po nim z prawdopodobieństwem p pole zmienia kolor na losowy, a z prawdopodobieństwem 1-p przyjmuje jako kolor średnią swoich sąsiadów.
     */
    @Override
    public void run() {
        this.setRandomColor();

        while (true) {
            try {
                double time = 0.5 * k + (generator.nextDouble() * k);
                Thread.sleep((long) time);
                double random = generator.nextDouble();
                synchronized (locker) {
                    if (random < p) {
                        this.setRandomColor();
                    }
                    else {
                        this.setNbhColor();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}