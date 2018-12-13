package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fill {
    @SerializedName("timestampFormat")
    @Expose
    public String timestampFormat;

    @SerializedName("price")
    @Expose
    public Double price;

    @SerializedName("timestamp")
    @Expose
    public String timestamp;

    @SerializedName("quantity")
    @Expose
    public Double quantity;

    @Override
    public String toString() {
        return "Fill{" +
                "timestampFormat='" + timestampFormat + '\'' +
                ", price=" + price +
                ", timestamp='" + timestamp + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
