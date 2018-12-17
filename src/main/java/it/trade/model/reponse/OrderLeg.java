package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderLeg {
    @SerializedName("priceInfo")
    @Expose
    public PriceInfo priceInfo;

    @SerializedName("fills")
    @Expose
    public List<Fill> fills = new ArrayList<Fill>();

    @SerializedName("symbol")
    @Expose
    public String symbol;

    @SerializedName("orderedQuantity")
    @Expose
    public Double orderedQuantity;

    @SerializedName("filledQuantity")
    @Expose
    public Double filledQuantity;

    @SerializedName("action")
    @Expose
    public String action;

    @Override
    public String toString() {
        return "OrderLeg{" +
                "priceInfo=" + priceInfo +
                ", fills=" + fills +
                ", symbol='" + symbol + '\'' +
                ", orderedQuantity=" + orderedQuantity +
                ", filledQuantity=" + filledQuantity +
                ", action='" + action + '\'' +
                '}';
    }
}
