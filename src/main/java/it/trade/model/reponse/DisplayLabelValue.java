package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DisplayLabelValue {
    @SerializedName("value")
    @Expose
    public String value;

    @SerializedName("displayLabel")
    @Expose
    public String displayLabel;

    @SerializedName("supportedOrderQuantityTypes")
    @Expose
    public List<String> supportedOrderQuantityTypes;

    public DisplayLabelValue(String displayLabel, String value) {
        this(displayLabel, value, null);
    }
    public DisplayLabelValue(String displayLabel, String value, List<String> supportedOrderQuantityTypes) {
        this.displayLabel = displayLabel;
        this.value = value;
        this.supportedOrderQuantityTypes = supportedOrderQuantityTypes;
    }

    @Override
    public String toString() {
        return "DisplayLabelValue{" +
            "value='" + value + '\'' +
            ", displayLabel='" + displayLabel + '\'' +
            ", supportedOrderQuantityTypes=" + supportedOrderQuantityTypes +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DisplayLabelValue that = (DisplayLabelValue) o;

        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        if (displayLabel != null ? !displayLabel.equals(that.displayLabel) : that.displayLabel != null) return false;
        return supportedOrderQuantityTypes != null ? supportedOrderQuantityTypes.equals(that.supportedOrderQuantityTypes) : that.supportedOrderQuantityTypes == null;
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (displayLabel != null ? displayLabel.hashCode() : 0);
        result = 31 * result + (supportedOrderQuantityTypes != null ? supportedOrderQuantityTypes.hashCode() : 0);
        return result;
    }
}
