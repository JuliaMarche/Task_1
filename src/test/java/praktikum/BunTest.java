package praktikum;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BunTest {

    @Test
    public void getNameBunTest() {
        Bun bun = new Bun ("Флюоресцентная булка R2-D3", 988);
        assertEquals("Флюоресцентная булка R2-D3", bun.getName(), "Неккоректное название булки");
    }

    @Test
    public void getPriceBunTest() {
        Bun bun = new Bun ("Краторная булка N-200i", 1255);
        assertEquals(1255, bun.getPrice(), "Некорректная цена булки");
    }
}
