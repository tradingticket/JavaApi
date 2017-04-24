package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TradeItOrderStatusResponse extends TradeItResponse {
    @SerializedName("accountNumber")
    @Expose
    public String accountNumber;

    @SerializedName("orderStatusDetailsList")
    @Expose
    public List<OrderStatusDetails> orderStatusDetailsList = new ArrayList<OrderStatusDetails>();

    @Override
    public String toString() {
        return "TradeItOrderStatusResponse{" +
                "accountNumber='" + accountNumber + '\'' +
                ", orderStatusDetailsList=" + orderStatusDetailsList +
                "}, " + super.toString();
    }
}
