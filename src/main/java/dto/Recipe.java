package dto;

import java.io.Serializable;

public class Recipe implements Serializable {
    private String postcode;
    private String recipe;
    private String delivery;

    public Recipe(String postcode, String recipe, String delivery) {
        this.postcode = postcode;
        this.recipe = recipe;
        this.delivery = delivery;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "postcode='" + postcode + '\'' +
                ", recipe='" + recipe + '\'' +
                ", delivery='" + delivery + '\'' +
                '}';
    }
}
