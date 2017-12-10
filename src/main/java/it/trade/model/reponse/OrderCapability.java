package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
