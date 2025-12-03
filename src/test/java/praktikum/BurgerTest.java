package praktikum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class BurgerTest {

    Burger burger;
    Database database;

    @Mock
    Bun bun;

    @Mock
    Ingredient sauce;

    @Mock
    Ingredient filling;

    @BeforeEach
    public void setUpTest() {
        burger = new Burger();
        database = new Database();
    }

    @Test
    public void setBunTest() {
        burger.setBuns(bun);
        assertEquals(bun, burger.bun, "Некорректное присвоена булка");
    }

    @Test
    public void addIngredientTest() {
        Mockito.when(filling.getType()).thenReturn(IngredientType.FILLING);
        Mockito.when(filling.getName()).thenReturn("Биокотлета из марсианской Магнолии");
        Mockito.when(filling.getPrice()).thenReturn(424f);

        burger.addIngredient(filling);

        assertEquals(1, burger.ingredients.size(), "Ингредиент не был добавлен");
        assertEquals(filling, burger.ingredients.get(0), "Добавленный ингредиент не совпадает с ожидаемым");
        assertEquals(IngredientType.FILLING, burger.ingredients.get(0).getType(), "Некорректный тип ингредиента");
        assertEquals("Биокотлета из марсианской Магнолии", burger.ingredients.get(0).getName(), "Некорректное наименование ингредиента");
        assertEquals(424f, burger.ingredients.get(0).getPrice(), "Некорректная цена ингредиента");
    }

    @Test
    public void removeIngredientTest() {
        burger.addIngredient(sauce);
        assertEquals(1, burger.ingredients.size(), "Ингредиент не был добавлен");
        burger.removeIngredient(0);
        assertTrue(burger.ingredients.isEmpty(), "Ингредиент не был удален");
    }

    @Test
    public void moveIngredientTest() {
        burger.addIngredient(filling);
        burger.addIngredient(sauce);
        burger.moveIngredient(0, 1);

        assertEquals(sauce, burger.ingredients.get(0), "Ингредиент не был перемещен корректно");
        assertEquals(filling, burger.ingredients.get(1), "Ингредиент не был перемещен корректно");
    }

    @ParameterizedTest
    @CsvSource({
            "0,3",
            "1,4"
    })
    public void getPriceParameterized(int bunIndex, int ingredientIndex) {
        Bun dbBun = database.availableBuns().get(bunIndex);
        Ingredient dbIngredient = database.availableIngredients().get(ingredientIndex);

        Mockito.when(bun.getPrice()).thenReturn(dbBun.getPrice());
        Mockito.when(sauce.getPrice()).thenReturn(dbIngredient.getPrice());

        burger.setBuns(bun);
        burger.addIngredient(sauce);

        float expected = dbBun.getPrice() * 2 + dbIngredient.getPrice();
        assertEquals(expected, burger.getPrice(), 0.01f, "Некорректно вычеслена цена бургера");
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,5",
            "0,2,3"
    })
    public void getReceiptParameterized(int bunIndex, int ingredientIndexFir, int ingredientIndexSec) {
        Bun dbBun = database.availableBuns().get(bunIndex);
        Ingredient dbIngredientFirst = database.availableIngredients().get(ingredientIndexFir);
        Ingredient dbIngredientSecond = database.availableIngredients().get(ingredientIndexSec);


        Mockito.when(bun.getPrice()).thenReturn(dbBun.getPrice());
        Mockito.when(bun.getName()).thenReturn(dbBun.getName());


        Mockito.when(sauce.getName()).thenReturn(dbIngredientFirst.getName());
        Mockito.when(sauce.getType()).thenReturn(dbIngredientFirst.getType());
        Mockito.when(sauce.getPrice()).thenReturn(dbIngredientFirst.getPrice());


        Mockito.when(filling.getName()).thenReturn(dbIngredientSecond.getName());
        Mockito.when(filling.getType()).thenReturn(dbIngredientSecond.getType());
        Mockito.when(filling.getPrice()).thenReturn(dbIngredientSecond.getPrice());

        burger.setBuns(bun);
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains(dbBun.getName()), "Булка отсутствует в чеке");
        assertTrue(receipt.contains(dbIngredientFirst.getType().toString().toLowerCase() + " " + dbIngredientFirst.getName()), "Первый ингредиент отсутствует или указан неверно в чеке");
        assertTrue(receipt.contains(dbIngredientSecond.getType().toString().toLowerCase() + " " + dbIngredientSecond.getName()),  "Второй ингредиент отсутствует или указан неверно в чеке");
        assertTrue(receipt.contains(String.format("Price: %f", burger.getPrice())), "Итоговая цена отсутствует или указана неверно в чеке");
    }
}
