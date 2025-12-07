package praktikum;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IngredientTypeTest {

    @Test
    public void checkValueSauce() {
        assertEquals(IngredientType.SAUCE, IngredientType.valueOf("SAUCE"), "Некорректный тип ингредиента");
    }

    @Test
    public void checkValueFilling() {
        assertEquals(IngredientType.FILLING, IngredientType.valueOf("FILLING"), "Некорректный тип ингредиента");
    }
}
