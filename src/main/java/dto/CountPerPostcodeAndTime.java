package dto;

import java.io.Serializable;

public class CountPerPostcodeAndTime implements Comparable<CountPerPostcodeAndTime>, Serializable {
    private String postcode;
    private String from;
    private String to;
    private Integer deliveryCount;

    public CountPerPostcodeAndTime(String postcode, String from, String to, Integer deliveryCount) {
        this.postcode = postcode;
        this.from = from;
        this.to = to;
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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Integer getDeliveryCount() {
        return deliveryCount;
    }

    public void setDeliveryCount(Integer deliveryCount) {
        this.deliveryCount = deliveryCount;
    }

    @Override
    public int compareTo(CountPerPostcodeAndTime countPerPostcodeAndTime) {
        return 0;
    }


}
