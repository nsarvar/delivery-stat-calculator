package dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class Response implements Serializable {
    private Integer uniqueRecipeCount;
    private List<CountPerRecipe> countPerRecipe;
    private BusiestPostcode busiestPostcode;
    private CountPerPostcodeAndTime countPerPostcodeAndTime;
    private Set<String> matchByName;

    public Response() {
    }

    public Integer getUniqueRecipeCount() {
        return uniqueRecipeCount;
    }

    public List<CountPerRecipe> getCountPerRecipe() {
        return countPerRecipe;
    }

    public void setCountPerRecipe(List<CountPerRecipe> countPerRecipe) {
        this.countPerRecipe = countPerRecipe;
    }

    public void setUniqueRecipeCount(Integer uniqueRecipeCount) {
        this.uniqueRecipeCount = uniqueRecipeCount;
    }

    public BusiestPostcode getBusiestPostcode() {
        return busiestPostcode;
    }

    public void setBusiestPostcode(BusiestPostcode busiestPostcode) {
        this.busiestPostcode = busiestPostcode;
    }

    public CountPerPostcodeAndTime getCountPerPostcodeAndTime() {
        return countPerPostcodeAndTime;
    }

    public void setCountPerPostcodeAndTime(CountPerPostcodeAndTime countPerPostcodeAndTime) {
        this.countPerPostcodeAndTime = countPerPostcodeAndTime;
    }

    public Set<String> getMatchByName() {
        return matchByName;
    }

    public void setMatchByName(Set<String> matchByName) {
        this.matchByName = matchByName;
    }
}
