package it.trade.model;

import java.util.ArrayList;
import java.util.List;

public class TradeItSecurityQuestion {

    protected String securityQuestion = "";
    protected List<String>  securityQuestionOptions = new ArrayList<>();

    public TradeItSecurityQuestion(String securityQuestion, List<String> securityQuestionOptions) {
        this.securityQuestion = securityQuestion;
        this.securityQuestionOptions = (securityQuestionOptions != null ? securityQuestionOptions : this.securityQuestionOptions);
    }

    protected TradeItSecurityQuestion() {}

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public List<String> getSecurityQuestionOptions() {
        return securityQuestionOptions;
    }

    @Override
    public String toString() {
        return "TradeItSecurityQuestion{" +
                "securityQuestion='" + securityQuestion + '\'' +
                ", securityQuestionOptions=" + securityQuestionOptions +
                '}';
    }
}
