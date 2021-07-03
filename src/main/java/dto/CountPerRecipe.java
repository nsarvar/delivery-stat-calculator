package dto;

import java.io.Serializable;
import java.util.Objects;

public class CountPerRecipe implements Comparable<CountPerRecipe>, Serializable {
    private String recipe;
    private Integer count;

    public CountPerRecipe(String recipe, Integer count) {
        this.recipe = recipe;
        this.count = count;
    }

    public void incrementCount() {
        this.count++;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountPerRecipe that = (CountPerRecipe) o;
        return recipe.equals(that.recipe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipe);
    }

    @Override
    public int compareTo(CountPerRecipe o) {
        return recipe.compareTo(o.recipe);
    }
}
