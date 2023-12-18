package com.example.abmaro.futuretransaction;

import com.example.abmaro.futuretransaction.types.Date;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRecord {
    public String recordCode;
    public String clientType;
    public BigDecimal clientNumber;
    public BigDecimal accountNumber;
    public BigDecimal subaccountNumber;
    public String oppositePartyCode;
    public String productGroupCode;
    public String exchangeCode;
    public String symbol;
    public Date expirationDate;
    public String currencyCode;
    public String movementCode;
    public String buySellCode;
    public String quantityLongSign;
    public BigDecimal quantityLong;
    public String quantityShortSign;
    public BigDecimal quantityShort;
    public BigDecimal exchBrokerFeeDec;
    public String exchBrokerFeeDC;
    public String exchBrokenFeeCurCode;
    public BigDecimal clearingFeeDec;
    public String clearingFeeDC;
    public String clearingFeeCurCode;
    public BigDecimal commission;
    public String commissionDC;
    public String commissionCurCode;
    public Date transactionDate;
    public BigDecimal futureReference;
    public String ticketNumber;
    public BigDecimal externalNumber;
    public BigDecimal transactionPriceDec;
    public String traderInitials;
    public String oppositeTraderId;
    public String openCloseCode;
    public String filler;

    public void fromRecord(String record) {
        if (record.length() < 176) {
            throw new TransactionParseException("Invalid record size, must be longer than or equal to 176", record, new TransactionRange(0, record.length()));
        }
        final TransactionParser p = new TransactionParser(record);
        recordCode = p.parseString(new TransactionRange(0, 3));
        clientType = p.parseString(new TransactionRange(3, 7));
        clientNumber = p.parseDecimal(new TransactionRange(7, 11), 0);
        accountNumber = p.parseDecimal(new TransactionRange(11, 15), 0);
        subaccountNumber = p.parseDecimal(new TransactionRange(15, 19), 0);
        oppositePartyCode = p.parseString(new TransactionRange(19, 25));
        productGroupCode = p.parseString(new TransactionRange(25, 27));
        exchangeCode = p.parseString(new TransactionRange(27, 31));
        symbol = p.parseString(new TransactionRange(31, 37));
        expirationDate = p.parseDate(new TransactionRange(37, 45));
        currencyCode = p.parseString(new TransactionRange(45, 48));
        movementCode = p.parseString(new TransactionRange(48, 50));
        buySellCode = p.parseString(new TransactionRange(50, 51));
        quantityLongSign = p.parseString(new TransactionRange(51, 52));
        quantityLong = p.parseDecimal(new TransactionRange(52, 62), 0);
        quantityShortSign = p.parseString(new TransactionRange(62, 63));
        quantityShort = p.parseDecimal(new TransactionRange(63, 73), 0);
        exchBrokerFeeDec = p.parseDecimal(new TransactionRange(73, 85), 2);
        exchBrokerFeeDC = p.parseString(new TransactionRange(85, 86));
        exchBrokenFeeCurCode = p.parseString(new TransactionRange(86, 89));
        clearingFeeDec = p.parseDecimal(new TransactionRange(89, 101), 2);
        clearingFeeDC = p.parseString(new TransactionRange(101, 102));
        clearingFeeCurCode = p.parseString(new TransactionRange(102, 105));
        commission = p.parseDecimal(new TransactionRange(105, 117), 2);
        commissionDC = p.parseString(new TransactionRange(117, 118));
        commissionCurCode = p.parseString(new TransactionRange(118, 121));
        transactionDate = p.parseDate(new TransactionRange(121, 129));
        futureReference = p.parseDecimal(new TransactionRange(129, 135), 0);
        ticketNumber = p.parseString(new TransactionRange(135, 141));
        externalNumber = p.parseDecimal(new TransactionRange(141, 147), 0);
        transactionPriceDec = p.parseDecimal(new TransactionRange(147, 162), 7);
        traderInitials = p.parseString(new TransactionRange(162, 168));
        oppositeTraderId = p.parseString(new TransactionRange(168, 175));
        openCloseCode = p.parseString(new TransactionRange(175, 176));
        if (record.length() > 176) {
            filler = p.parseString(new TransactionRange(176, record.length()));
        }
    }

    public String getClientInfoKey() {
        return clientType + "_" + clientNumber + "_" + accountNumber + "_" + subaccountNumber;
    }

    public String getProductInfoKey() {
        return exchangeCode + "_" + productGroupCode + "_" + symbol + "_" + expirationDate;
    }

    public BigDecimal getTransactionAmount() {
        return quantityLong.subtract(quantityShort);
    }

}
