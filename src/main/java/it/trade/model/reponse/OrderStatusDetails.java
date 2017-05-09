package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderStatusDetails {
    @SerializedName("groupOrderType")
    @Expose
    public String groupOrderType;

    @SerializedName("orderExpiration")
    @Expose
    public String orderExpiration;

    @SerializedName("orderType")
    @Expose
    public String orderType;

    @SerializedName("groupOrderId")
    @Expose
    public String groupOrderId;

    @SerializedName("orderLegs")
    @Expose
    public List<OrderLeg> orderLegs = new ArrayList<OrderLeg>();

    @SerializedName("orderNumber")
    @Expose
    public String orderNumber;

    @SerializedName("orderStatus")
    @Expose
    public String orderStatus;

    @SerializedName("groupOrders")
    @Expose
    public List<OrderStatusDetails> groupOrders = new ArrayList<OrderStatusDetails>();

    @Override
    public String toString() {
        return "OrderStatusDetails{" +
                "groupOrderType='" + groupOrderType + '\'' +
                ", orderExpiration='" + orderExpiration + '\'' +
                ", orderType='" + orderType + '\'' +
                ", groupOrderId='" + groupOrderId + '\'' +
                ", orderLegs=" + orderLegs +
                ", orderNumber='" + orderNumber + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", groupOrders=" + groupOrders +
                '}';
    }
}
