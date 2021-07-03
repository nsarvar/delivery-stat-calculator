package dto;

import java.io.Serializable;
import java.util.Objects;

public class BusiestPostcode implements Comparable<BusiestPostcode>, Serializable {
    private String postcode;
    private int deliveryCount;

    private BusiestPostcode() {
    }

    public BusiestPostcode(String postcode, int deliveryCount) {
        this.postcode = postcode;
        this.deliveryCount = deliveryCount;
    }

    public void countIncrement() {
        this.deliveryCount++;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public int getDeliveryCount() {
        return deliveryCount;
    }

    public void setDeliveryCount(int deliveryCount) {
        this.deliveryCount = deliveryCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusiestPostcode that = (BusiestPostcode) o;
        return postcode.equals(that.postcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postcode);
    }

    @Override
    public int compareTo(BusiestPostcode o) {
        return Integer.compare(o.deliveryCount, deliveryCount);
    }
}
