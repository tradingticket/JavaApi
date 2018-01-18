package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DisplayLabelValue {
    @SerializedName("value")
    @Expose
    public String value;

    @SerializedName("displayLabel")
    @Expose
    public String displayLabel;

    public DisplayLabelValue(String displayLabel, String value) {
        this.displayLabel = displayLabel;
        this.value = value;
    }

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
