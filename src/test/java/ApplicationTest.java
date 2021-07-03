import dto.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.RecipeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ApplicationTest {
    private RecipeService recipeService;
    private final String[] searchTerms = {"chicken"};

    @BeforeEach
    void setUp() {
        String timeRange = "10AM-3PM";
        String postcode = "10120";
        String file = "";
        recipeService = new RecipeService(file, postcode, timeRange, searchTerms);
    }

    @Test
    public void testRecipe() {
        Response result = recipeService.init();

        assertEquals(16, result.getUniqueRecipeCount());

        assertEquals("Cherry Balsamic Pork Chops", result.getCountPerRecipe().get(0).getRecipe());
        assertEquals(2, result.getCountPerRecipe().get(0).getCount());
        assertEquals("Speedy Steak Fajitas", result.getCountPerRecipe().get(13).getRecipe());
        assertEquals(3, result.getCountPerRecipe().get(13).getCount());

        assertEquals("10120", result.getBusiestPostcode().getPostcode());
        assertEquals(2, result.getBusiestPostcode().getDeliveryCount());

        assertEquals("10120", result.getCountPerPostcodeAndTime().getPostcode());
        assertEquals(2, result.getCountPerPostcodeAndTime().getDeliveryCount());

        assertEquals(5, result.getMatchByName().size());
        result.getMatchByName().forEach(s -> assertTrue(s.toLowerCase().contains(searchTerms[0].toLowerCase())));
    }
}
