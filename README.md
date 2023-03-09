# Multithreaded Simulation
Program na wejściu przyjmuje argumenty n, m, k, i p.
Tworzy planszę z n x m prostokątów, gdzie n - liczba wierszy, m - kolumn.
Przez cały czas działania programu każdy z prostokątów w odstępie losowo wybranych opoznień z przedziału [0.5k, 1.5k] milisekund wykonuje jedną z akcji:
- z prawdopodobieństwem p zmienia kolor na losowy
- z prawdopodobieństwem 1 - p przyjmuje jako kolor średnią swoich sąsiadów
