package com.example.abmaro.futuretransaction;

import com.example.abmaro.futuretransaction.types.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TransactionParser {
    private static Logger log = LoggerFactory.getLogger(TransactionParser.class);
    public static final char FILLER = ' ';
    public final String rawString;

    public TransactionParser(String record) {
        this.rawString = record;
    }

    public static TransactionRecord parse(String record) {
        TransactionRecord transactionRecord = new TransactionRecord();
        try {
            transactionRecord.fromRecord(record);
        } catch (TransactionParseException tpe) {
            log.debug("Failed to parse record: " + record + ", \n reason: " + tpe.getMessage() + "\n ignore it..");
            return null;
        }
        return transactionRecord;
    }

    /**
     * @param range the range
     * @return parsed string.
     */
    public String parseString(TransactionRange range) {
        checkValidCharacters(range, false);
        String str = rawValue(range);
        while (str.endsWith(" ")) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    /**
     * @param range the range
     * @return parsed decimal.
     */
    public BigDecimal parseDecimal(TransactionRange range, int decimalPosition) {
        checkValidCharacters(range, true);
        String str = rawValue(range);
        while (str.endsWith(" ")) {
            str = str.substring(0, str.length() - 1);
        }
        return new BigDecimal(new BigInteger(str), decimalPosition);
    }

    /**
     * Parses transaction record date.
     * @param range the range containing the date, in the CCYYMMDD format. The range must be 8 characters long.
     * @return parsed date
     * @throws IllegalArgumentException if the range is not 8 characters long.
     * @throws TransactionParseException if the content is not integer / invalid date
     */
    public Date parseDate(TransactionRange range) {
        if (range.length() != 8) {
            throw new IllegalArgumentException("Invalid Date range " + range + ": must be 8 characters long");
        }
        TransactionRange r;
        r = new TransactionRange(range.column(), range.column() + 4);
        int year;
        try {
            year = Integer.parseInt(rawValue(r));
        } catch (NumberFormatException ex) {
            throw new TransactionParseException("Failed to parse record date year " + rawValue(range) + ": " + ex, rawString, r);
        }
        r = new TransactionRange(range.column() + 4, range.column() + 6);
        int month;
        try {
            month = Integer.parseInt(rawValue(r));
        } catch (NumberFormatException ex) {
            throw new TransactionParseException("Failed to parse record date month " + rawValue(range) + ": " + ex, rawString, r);
        }
        if (month < 1 || month > 12) {
            throw new TransactionParseException("Invalid month value " + month + ": must be 1..12", rawString, r);
        }
        r = new TransactionRange(range.column() + 6, range.column() + 8);
        int day;
        try {
            day = Integer.parseInt(rawValue(r));
        } catch (NumberFormatException ex) {
            throw new TransactionParseException("Failed to parse record date day " + rawValue(range) + ": " + ex, rawString, r);
        }
        if (day < 1 || day > 31) {
            throw new TransactionParseException("Invalid day value " + day + ": must be 1..31", rawString, r);
        }
        return new Date(year, month, day);
    }

    public String rawValue(TransactionRange... range) {
        final StringBuilder sb = new StringBuilder();
        for (TransactionRange r : range) {
            sb.append(rawString, r.column(), r.columnTo());
        }
        return sb.toString();
    }

    /**
     * Check if character is valid in the range. Assume that the sample input is valid, so include character '.' in valid character list.
     * @param range
     * @param isDecimalOnly
     * @throws  TransactionParseException if the characters not match as expected (A-Z, 0-9, '.')
     */
    public void checkValidCharacters(TransactionRange range, boolean isDecimalOnly) {
        final String str = rawValue(range);
        for (int i = 0; i < str.length(); i++) {
            final char c = str.charAt(i);
            if (c != FILLER && c != '.' && (c < '0' || c > '9') && (isDecimalOnly || (c < 'A' || c > 'Z'))) {
                throw new TransactionParseException("Invalid character in transaction record: " + c, rawString, new TransactionRange(range.column() , range.columnTo()));
            }
        }
    }
}
