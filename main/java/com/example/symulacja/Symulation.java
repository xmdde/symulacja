package com.example.symulacja;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Klasa obsługująca symulację.
 * Tworzy tablicę obiektów MyTiles, dodaje sąsiadów do każdego z elementów i dodaje je na panel.
 */
public class Symulation extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        int n = 0, m = 0, k = 0; // n - number of rows, m - num of columns, k - speed
        double p = 0; // probability of color change (0,1)
        BorderPane borderPane = new BorderPane();
        boolean check = true;

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
            Board board = new Board(n, m, k, p, borderPane);
            stage.setResizable(false);
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