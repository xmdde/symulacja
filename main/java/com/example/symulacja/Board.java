package com.example.symulacja;

import javafx.scene.layout.BorderPane;

import java.util.Random;

public class Board {
    public MyTile[][] tiles;
    final double width;
    final double height;
    final Random generator;
    final Object locker;

    Board(int n, int m, int k, double p, BorderPane borderPane) {
        this.locker = new Object();
        this.generator = new Random();
        this.width = 1430.0 / m;
        this.height = 800.0 / n;
        this.tiles = new MyTile[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                tiles[i][j] = new MyTile(width * j, height * i, width, height, k, p, generator, locker);
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                tiles[i][j].neighbours[0] = tiles[i][(j+1+m)%m];
                tiles[i][j].neighbours[1] = tiles[i][(j-1+m)%m];
                tiles[i][j].neighbours[2] = tiles[(i+1+n)%n][j];
                tiles[i][j].neighbours[3] = tiles[(i-1+n)%n][j];
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                borderPane.getChildren().addAll(tiles[i][j]);
            }
        }
    }
}
