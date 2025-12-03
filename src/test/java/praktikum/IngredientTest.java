package praktikum;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IngredientTest {

    @ParameterizedTest
    @CsvSource({
            "SAUCE, Соус Spicy-X, 90",
            "FILLING, Мясо бессмертных моллюсков Protostomia, 1337",
            "SAUCE, Соус с шипами Антарианского плоскоходца, 88",
            "FILLING, Кристаллы марсианских альфа-сахаридов, 762"
    })

    public void getIngredientPropertiesTest(String type, String name, float price) {
        IngredientType ingredientType = IngredientType.valueOf(type);
        Ingredient ingredient = new Ingredient(ingredientType, name, price);
        assertEquals(ingredientType,ingredient.getType(),"Неккоректный тип игридиента");
        assertEquals(name,ingredient.getName(), "Неккоректное название игридиента");
        assertEquals(price, ingredient.getPrice(), "Неккоректная цена игридиента");
    }
}
