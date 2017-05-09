package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TradeItPreviewStockOrEtfOrderResponse extends TradeItResponse  {
    @SerializedName("ackWarningsList")
    @Expose
    public List<String> ackWarningsList = new ArrayList<String>();

    @SerializedName("orderDetails")
    @Expose
    public OrderDetails orderDetails;

    @SerializedName("orderId")
    @Expose
    public String orderId;

    @SerializedName("warningsList")
    @Expose
    public List<String> warningsList = new ArrayList<String>();

    @Override
    public String toString() {
        return "TradeItPreviewStockOrEtfOrderResponse{" +
                "ackWarningsList=" + ackWarningsList +
                ", orderDetails=" + orderDetails +
                ", orderId=" + orderId +
                ", warningsList=" + warningsList +
                "}, " + super.toString();
    }
}
