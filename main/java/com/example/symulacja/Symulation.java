package com.example.symulacja;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

/**
 * Klasa obsługująca symulację.
 * Tworzy tablicę obiektów MyTiles, dodaje sąsiadów do każdego z elementów i dodaje je na panel.
 */
public class Symulation extends Application {
    /**
     * Obiekt typu Object niezbędny do synchronizacji zmiany koloru pól.
     */
    final Object locker = new Object();

    @Override
    public void start(Stage stage) throws IOException {

        int n = 0, m = 0, k = 0; // n - liczba wierszy, m - kolumn, k - szybkosc dzialania
        double p = 0; // prawdopodobienstwo zmiany koloru (0,1)
        BorderPane borderPane = new BorderPane();
        boolean check = true;
        Random generator = new Random();

        try {
            n = Integer.parseInt(getParameters().getUnnamed().get(0));
            m = Integer.parseInt(getParameters().getUnnamed().get(1));
            k = Integer.parseInt(getParameters().getUnnamed().get(2));
            p = Double.parseDouble(getParameters().getUnnamed().get(3));

            if (n <= 0 || m <= 0 || k <= 0 || p < 0.0 || p > 1.0) {
                check = false;
                System.out.println("Wprowadzono błędne dane!");
                System.exit(0);
            }

        } catch (NumberFormatException e) {
            check = false;
            System.out.println("Wprowadzono błędne dane!");
            System.exit(0);
        }

        if (check) {

            Scene scene = new Scene(borderPane, 1430.0, 800.0);
            final double width = 1430.0 / m;
            final double height = 800.0 / n;

            MyTile[][] tiles = new MyTile[n][m];

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
                    borderPane.getChildren().add(tiles[i][j]);
                }
            }

            stage.setTitle("Symulacja");
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(e -> {
                Platform.exit();
                System.exit(0);
            });
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}