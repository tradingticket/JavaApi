package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TradeItBrokerAccount {
    @SerializedName("accountNumber")
    @Expose
    public String accountNumber;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("accountBaseCurrency")
    @Expose
    public String accountBaseCurrency;

    @SerializedName("orderCapabilities")
    @Expose
    public List<OrderCapability> orderCapabilities;

    @Override
    public String toString() {
        return "TradeItBrokerAccount{" +
                "accountNumber='" + accountNumber + '\'' +
                ", name='" + name + '\'' +
                ", accountBaseCurrency='" + accountBaseCurrency + '\'' +
                ", orderCapabilities='" + orderCapabilities + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TradeItBrokerAccount that = (TradeItBrokerAccount) o;

        if (accountNumber != null ? !accountNumber.equals(that.accountNumber) : that.accountNumber != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (accountBaseCurrency != null ? !accountBaseCurrency.equals(that.accountBaseCurrency) : that.accountBaseCurrency != null)
            return false;
        return orderCapabilities != null ? orderCapabilities.equals(that.orderCapabilities) : that.orderCapabilities == null;
    }

    @Override
    public int hashCode() {
        int result = accountNumber != null ? accountNumber.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (accountBaseCurrency != null ? accountBaseCurrency.hashCode() : 0);
        result = 31 * result + (orderCapabilities != null ? orderCapabilities.hashCode() : 0);
        return result;
    }

    public class OrderCapability {

        @SerializedName("instrument")
        @Expose
        private Instrument instrument;

        public Instrument getInstrument() {
            if (this.instrument == null) {
                instrument = Instrument.UNKNOWN;
            }
            return instrument;
        }

        public void setInstrument(Instrument instrument) {
            this.instrument = instrument;
        }

        @SerializedName("actions")
        @Expose
        public List<DisplayLabelValue> actions;

        @SerializedName("priceTypes")
        @Expose
        public List<DisplayLabelValue> priceTypes;

        @SerializedName("expirationTypes")
        @Expose
        public List<DisplayLabelValue> expirationTypes;

        public class DisplayLabelValue {
            @SerializedName("value")
            @Expose
            public String value;

            @SerializedName("displayLabel")
            @Expose
            public String displayLabel;

            @Override
            public String toString() {
                return "DisplayLabelValue{" +
                        "value='" + value + '\'' +
                        ", displayLabel='" + displayLabel + '\'' +
                        '}';
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                DisplayLabelValue that = (DisplayLabelValue) o;

                if (value != null ? !value.equals(that.value) : that.value != null) return false;
                return displayLabel != null ? displayLabel.equals(that.displayLabel) : that.displayLabel == null;
            }

            @Override
            public int hashCode() {
                int result = value != null ? value.hashCode() : 0;
                result = 31 * result + (displayLabel != null ? displayLabel.hashCode() : 0);
                return result;
            }
        }

        @Override
        public String toString() {
            return "OrderCapability{" +
                    "instrument=" + instrument +
                    ", actions=" + actions +
                    ", priceTypes=" + priceTypes +
                    ", expirationTypes=" + expirationTypes +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            OrderCapability that = (OrderCapability) o;

            if (instrument != that.instrument) return false;
            if (actions != null ? !actions.equals(that.actions) : that.actions != null) return false;
            if (priceTypes != null ? !priceTypes.equals(that.priceTypes) : that.priceTypes != null) return false;
            return expirationTypes != null ? expirationTypes.equals(that.expirationTypes) : that.expirationTypes == null;
        }

        @Override
        public int hashCode() {
            int result = instrument != null ? instrument.hashCode() : 0;
            result = 31 * result + (actions != null ? actions.hashCode() : 0);
            result = 31 * result + (priceTypes != null ? priceTypes.hashCode() : 0);
            result = 31 * result + (expirationTypes != null ? expirationTypes.hashCode() : 0);
            return result;
        }
    }
}
