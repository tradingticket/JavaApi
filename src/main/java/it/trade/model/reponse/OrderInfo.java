package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderInfo {
    @SerializedName("action")
    @Expose
    public String action;

    @SerializedName("quantity")
    @Expose
    public Double quantity;

    @SerializedName("symbol")
    @Expose
    public String symbol;

    @SerializedName("price")
    @Expose
    public Price price;

    @SerializedName("expiration")
    @Expose
    public String expiration;

    @SerializedName("orderQuantityType")
    @Expose
    public String orderQuantityType;

    @Override
    public String toString() {
        return "OrderInfo{" +
            "action='" + action + '\'' +
            ", quantity=" + quantity +
            ", symbol='" + symbol + '\'' +
            ", price=" + price +
            ", expiration='" + expiration + '\'' +
            ", orderQuantityType='" + orderQuantityType + '\'' +
            '}';
    }
}