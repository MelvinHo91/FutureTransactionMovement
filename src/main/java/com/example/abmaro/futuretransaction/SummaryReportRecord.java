package com.example.abmaro.futuretransaction;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SummaryReportRecord {
    /**
     * For Generate CSV Headers use.
     */
    public enum SummaryReportRecordHeaders {
        Client_Information,
        Product_Information,
        Total_Transaction_Amount
    }

    public final String clientInformation;
    public final String productInformation;
    public BigDecimal totalTransactionAmount;

    public void addTotalTransactionAmount(BigDecimal amount) {
        totalTransactionAmount = totalTransactionAmount.add(amount);
    }
}
