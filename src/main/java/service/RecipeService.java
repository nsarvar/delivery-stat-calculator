package service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import dto.*;
import utils.Util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class RecipeService {
    private String file;
    private final List<String> searchTerms;
    private final String postcode;
    private final Map<String, LocalTime> timeRange;
    private final Set<String> searchResult = new TreeSet<>();
    private final Map<String, CountPerRecipe> countRecipeMap = new TreeMap<>();
    private final Map<String, BusiestPostcode> postcodeMap = new HashMap<>();
    private final CountPerPostcodeAndTime pt;
    private BusiestPostcode busiestPostcode;

    private final Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setPrettyPrinting()
            .serializeNulls().create();

    public RecipeService(String file, String postcode, String timeRange, String[] searchTerms) {
        this.file = file;
        this.postcode = postcode;
        this.timeRange = Util.extractTimeFromTimeRange(timeRange);
        this.searchTerms = Arrays.stream(searchTerms)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        pt = new CountPerPostcodeAndTime(postcode,
                Util.timeToStr(this.timeRange.get("from")),
                Util.timeToStr(this.timeRange.get("to")), 0);
    }

    /**
     * Instead of loading entire file into memory, we will load the file in chunks.
     * If filename is not provided, it reads sample file from resources directory.
     *
     * @return Response
     */
    public Response init() {
        try {
            InputStream inputStream;
            if (file == null || file.isEmpty()) {
                file = "sample_data.json";
                inputStream = Util.getFileFromResourceAsStream(file);
            } else {
                inputStream = new FileInputStream(file);
            }
            readJsonStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getResult();
    }

    /**
     * Reads a JSON document containing an array of messages. It steps through array elements
     * as a stream to avoid loading the complete document into memory.
     */
    public void readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        reader.beginArray();
        while (reader.hasNext()) {
            Recipe recipe = gson.fromJson(reader, Recipe.class);
            countPerRecipe(recipe);
            countBusiestPostcode(recipe);
            countPerPostcodeAndTime(recipe);
            matchByName(recipe);
        }
        reader.endArray();
        reader.close();
    }

    /**
     * This function collects recipe name with count in the TreeMap. I have chosen TreeMap over HashMap because
     * elements are sorted and treeMap time complexity for the whole process is better than HashMap in this case.
     * <p>
     * TreeMap
     * ---------------
     * get() - O(logN)
     * put() - O(logN)
     * -------------
     * Since we run accessing TreeMap element and updating it inside loop, Total value will be O(NlogN)
     * <p>
     * HashMap
     * -----------------
     * get() - O(1) => worst case O(n)
     * put() - O(1) => worst case O(n)
     * ------------
     * Total for HashMap (worst case) - O(n^2)
     * Adding to ArrayList - O(n)
     * ArrayList sort() -> O(N*logN)
     *
     * @param recipe recipe
     */
    private void countPerRecipe(Recipe recipe) {
        var r = countRecipeMap.get(recipe.getRecipe());
        if (r == null)
            r = new CountPerRecipe(recipe.getRecipe(), 1);
        else
            r.incrementCount();
        countRecipeMap.put(r.getRecipe(), r);
    }

    /**
     * HashMap is used to group recipes by PostCode and count the number of delivers because
     * time complexity for get and put methods of HashMap is O(1) (worst case O(n)) and we don't need a sorting
     * as we care only about one value (busiest postcode). In this case, we can just use additional variable to
     * track the busiest postcode. `busiestPostcode` variable keeps a BusiestPostcode object with highest deliveryCount.
     *
     * @param recipe recipe
     */
    private void countBusiestPostcode(Recipe recipe) {
        BusiestPostcode p = postcodeMap.get(recipe.getPostcode());
        if (p == null)
            p = new BusiestPostcode(recipe.getPostcode(), 1);
        else
            p.countIncrement();
        if (busiestPostcode == null || p.getDeliveryCount() > busiestPostcode.getDeliveryCount())
            busiestPostcode = p;
        postcodeMap.put(p.getPostcode(), p);
    }

    /**
     * if given postcode equals to recipe postcode and time-range lies in range of delivery time,
     * then increment the count.
     *
     * @param recipe recipe
     */
    private void countPerPostcodeAndTime(Recipe recipe) {
        if (recipe.getPostcode().equals(postcode)) {
            Map<String, LocalTime> recipeTimeRange = Util.extractTimeFromTimeRange(recipe.getDelivery());
            if (!recipeTimeRange.get("from").isBefore(timeRange.get("from"))
                    && !recipeTimeRange.get("to").isAfter(timeRange.get("to"))) {
                pt.countIncrement();
            }
        }
    }

    /**
     * if recipe contains any of the given terms, add the recipe to TreeSet which maintains items in sorted
     * by default.
     *
     * @param recipe recipe
     */
    private void matchByName(Recipe recipe) {
        String recipeName = recipe.getRecipe().toLowerCase();
        boolean isPresent = searchTerms.stream()
                .anyMatch(recipeName::contains);
        if (isPresent)
            searchResult.add(recipe.getRecipe());
    }

    public Response getResult() {
        Response response = new Response();
        response.setUniqueRecipeCount(countRecipeMap.size());
        response.setCountPerRecipe(new ArrayList<>(countRecipeMap.values()));
        response.setBusiestPostcode(busiestPostcode);
        response.setCountPerPostcodeAndTime(pt);
        response.setMatchByName(searchResult);
        return response;
    }

    public void printResult(Response response) {
        String responseJson = gson.toJson(response);
        System.out.println(responseJson);
    }
}
