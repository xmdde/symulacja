import com.example.symulacja.MyTile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class TileTest {
    @Test
    void neighboursTest() {
        MyTile tile = new MyTile(4, 4, 4, 4, 1, 5, new Random(), new Object());
        Assertions.assertTrue(tile.noNeighbours());
    }
}