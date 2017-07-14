package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionDetails {
    @SerializedName("description")
    @Expose
    public String description;

    @SerializedName("symbol")
    @Expose
    public String symbol;

    @SerializedName("date")
    @Expose
    public String date;

    @SerializedName("amount")
    @Expose
    public Double amount;

    @SerializedName("action")
    @Expose
    public String action;

    @SerializedName("type")
    @Expose
    public String type;

    @SerializedName("price")
    @Expose
    public Double price;

    @SerializedName("quantity")
    @Expose
    public Double quantity;

    @SerializedName("commission")
    @Expose
    public Double commission;

    @Override
    public String toString() {
        return "TransactionDetails{" +
                "description='" + description + '\'' +
                ", symbol='" + symbol + '\'' +
                ", date='" + date + '\'' +
                ", amount=" + amount +
                ", action='" + action + '\'' +
                ", type='" + type + '\'' +
                ", price='" + price + '\'' +
                ", quantity='" + quantity + '\'' +
                ", commission='" + commission + '\'' +
                '}';
    }
}
