package cmd;

import dto.Response;
import picocli.CommandLine;
import service.RecipeService;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "Recipe app CLI", description = "Recipe Stats Calculator")
public class Cmd implements Callable<Integer> {

    @CommandLine.Option(names = {"-f", "--file"}, description = "File name with absolute path", required = false)
    private String file;

    @CommandLine.Option(names = {"-p", "--postcode"}, description = "Postcode", required = false,
            defaultValue = "10120")
    private String postcode;

    @CommandLine.Option(names = {"-t"}, description = "Time range", required = false,
            defaultValue = "10AM-3PM")
    private String timeRange;

    @CommandLine.Option(names = {"-s", "--search_terms"}, description = "Search", required = false,
            split = ",", defaultValue = "Potato, Veggie, Mushroom")
    private String[] searchTerms;

    @Override
    public Integer call() {
        // get values from ENV if provided
        String fileEnv = System.getenv("FILE");
        String postCodeEnv = System.getenv("POSTCODE");
        String timeRangeEnv = System.getenv("TIME_RANGE");
        String searchTermsEnv = System.getenv("SEARCH_TERMS");
        if (fileEnv != null && !fileEnv.isBlank())
            file = "/data/" + fileEnv;
        if (postCodeEnv != null && !postCodeEnv.isBlank())
            postcode = postCodeEnv;
        if (timeRangeEnv != null && !timeRangeEnv.isBlank())
            timeRange = timeRangeEnv;
        if (searchTermsEnv != null && !searchTermsEnv.isBlank())
            searchTerms = searchTermsEnv.split(",");

        RecipeService recipeService = new RecipeService(file, postcode, timeRange, searchTerms);
        Response response = recipeService.init();
        recipeService.printResult(response);
        return 0;
    }
}
