package com.example.abmaro.futuretransaction;

import com.example.abmaro.futuretransaction.types.Date;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class TransactionParserTest {

    @Test
    void testTransactionRecordParsing() {
        String testInput = "315CL  432100020001SGXDC FUSGX NK    20100910JPY01B 0000000001 " +
                "0000000000000000000060DUSD000000000030DUSD000000000000DJPY201008200012380     688032000092500000000             O";
        TransactionRecord transactionRecord = new TransactionRecord();
        transactionRecord.setRecordCode("315");
        transactionRecord.setClientType("CL");
        transactionRecord.setClientNumber(new BigDecimal(new BigInteger("4321"), 0));
        transactionRecord.setAccountNumber(new BigDecimal(new BigInteger("0002"), 0));
        transactionRecord.setSubaccountNumber(new BigDecimal(new BigInteger("0001"), 0));
        transactionRecord.setOppositePartyCode("SGXDC");
        transactionRecord.setProductGroupCode("FU");
        transactionRecord.setExchangeCode("SGX");
        transactionRecord.setSymbol("NK");
        transactionRecord.setExpirationDate(new Date(2010, 9, 10));
        transactionRecord.setCurrencyCode("JPY");
        transactionRecord.setMovementCode("01");
        transactionRecord.setBuySellCode("B");
        transactionRecord.setQuantityLongSign("");
        transactionRecord.setQuantityLong(new BigDecimal(new BigInteger("0000000001"), 0));
        transactionRecord.setQuantityShortSign("");
        transactionRecord.setQuantityShort(new BigDecimal(new BigInteger("0000000000"), 0));
        transactionRecord.setExchBrokerFeeDec(new BigDecimal(new BigInteger("000000000060"), 2));
        transactionRecord.setExchBrokerFeeDC("D");
        transactionRecord.setExchBrokenFeeCurCode("USD");
        transactionRecord.setClearingFeeDec(new BigDecimal(new BigInteger("000000000030"), 2));
        transactionRecord.setClearingFeeDC("D");
        transactionRecord.setClearingFeeCurCode("USD");
        transactionRecord.setCommission(new BigDecimal(new BigInteger("000000000000"), 2));
        transactionRecord.setCommissionDC("D");
        transactionRecord.setCommissionCurCode("JPY");
        transactionRecord.setTransactionDate(new Date(2010, 8, 20));
        transactionRecord.setFutureReference(new BigDecimal(new BigInteger("001238"), 0));
        transactionRecord.setTicketNumber("0");
        transactionRecord.setExternalNumber(new BigDecimal(new BigInteger("688032"), 0));
        transactionRecord.setTransactionPriceDec(new BigDecimal(new BigInteger("000092500000000"), 7));
        transactionRecord.setTraderInitials("");
        transactionRecord.setOppositeTraderId("");
        transactionRecord.setOpenCloseCode("O");
        TransactionRecord actualRecord = TransactionParser.parse(testInput);
        assertEquals(transactionRecord,  actualRecord);
    }


    @Test
    void testDateParsing() {
        String testInput = "315CL  432100020001SGXDC FUSGX NK    20100910JPY01B 0000000001 " +
                "0000000000000000000060DUSD000000000030DUSD000000000000DJPY201008200012380     688032000092500000000             O";
        //Test for expirationDate
        assertEquals(new Date(2010, 9, 10), new TransactionParser(testInput).parseDate(new TransactionRange(37, 45)));
    }

    /**
     * Test Date exception
     * In this case we just only focus on expiration date because this is the key in the output
     * Case 1. Year is not digit
     * Case 2. Month is not digit
     * Case 3. Month is not in 1..12
     * Case 4. Day is not digit
     * Case 5. Day is not in 1..31
     * Case 6: Invalid range (8 digit)
     */
    @Test
    void testDateException() {
        String case1Input = "ABCD0101";
        String case2Input = "1999O101";
        String case3Input = "19992001";
        String case4Input = "199901O1";
        String case5Input = "19990199";
        String case6Input = "19990101";
        TransactionRange range1 = new TransactionRange(0, 8);
        TransactionRange range2 = new TransactionRange(0, 7);
        Exception exception1 = assertThrows(TransactionParseException.class, () -> {
            new TransactionParser(case1Input).parseDate(range1);
        });
        Exception exception2 = assertThrows(TransactionParseException.class, () -> {
            new TransactionParser(case2Input).parseDate(range1);
        });
        Exception exception3 = assertThrows(TransactionParseException.class, () -> {
            new TransactionParser(case3Input).parseDate(range1);
        });
        Exception exception4 = assertThrows(TransactionParseException.class, () -> {
            new TransactionParser(case4Input).parseDate(range1);
        });
        Exception exception5 = assertThrows(TransactionParseException.class, () -> {
            new TransactionParser(case5Input).parseDate(range1);
        });
        Exception exception6 = assertThrows(IllegalArgumentException.class, () -> {
            new TransactionParser(case6Input).parseDate(range2);
        });
        String expectedExceptionMessage1 = "Failed to parse record date year " + case1Input + ": ";
        String actualExceptionMessage1 = exception1.getMessage();
        String expectedExceptionMessage2 = "Failed to parse record date month " + case2Input + ": ";
        String actualExceptionMessage2 = exception2.getMessage();
        String expectedExceptionMessage3 = "Invalid month value " + 20 + ": must be 1..12";
        String actualExceptionMessage3 = exception3.getMessage();
        String expectedExceptionMessage4 = "Failed to parse record date day " + case4Input + ": ";
        String actualExceptionMessage4 = exception4.getMessage();
        String expectedExceptionMessage5 = "Invalid day value " + 99 + ": must be 1..31";
        String actualExceptionMessage5 = exception5.getMessage();
        String expectedExceptionMessage6 = "Invalid Date range " + range2 + ": must be 8 characters long";
        String actualExceptionMessage6 = exception6.getMessage();
        assertTrue(actualExceptionMessage1.contains(expectedExceptionMessage1));
        assertTrue(actualExceptionMessage2.contains(expectedExceptionMessage2));
        assertTrue(actualExceptionMessage3.contains(expectedExceptionMessage3));
        assertTrue(actualExceptionMessage4.contains(expectedExceptionMessage4));
        assertTrue(actualExceptionMessage5.contains(expectedExceptionMessage5));
        assertTrue(actualExceptionMessage6.contains(expectedExceptionMessage6));
    }

    @Test
    void testStringParsing() {
        String testInput = "ABCTEST12345 ABC.";
        assertEquals("TEST", new TransactionParser(testInput).parseString(new TransactionRange(3, 7)));
        assertEquals("ABCTEST12345 ABC", new TransactionParser(testInput).parseString(new TransactionRange(0, 16)));
        assertEquals("ABCTEST12345 ABC.", new TransactionParser(testInput).parseString(new TransactionRange(0, 17)));
    }

    /**
     * If the string contain invalid char (Not A-Z, 0-9, '.'), raise exception
     */
    @Test
    void testStringException() {
        String rawString = "ABC123@";
        TransactionRange range = new TransactionRange(0, 7);
        Exception exception = assertThrows(TransactionParseException.class, () -> {
            new TransactionParser(rawString).parseString(range);
        });
        String expectedExceptionMessage = "Failed to parse transaction record " + rawString + " at " + range + ": " + "Invalid character in transaction record: " + "@";
        String actualExceptionMessage = exception.getMessage();
        assertTrue(actualExceptionMessage.contains(expectedExceptionMessage));
    }

    @Test
    void testDecimalParsing() {
        String testInput = "ABCTEST12345 ABC";
        assertEquals(new BigDecimal("12345"), new TransactionParser(testInput).parseDecimal(new TransactionRange(7, 12), 0));
        assertEquals(new BigDecimal("12345"), new TransactionParser(testInput).parseDecimal(new TransactionRange(7, 13), 0));
        assertEquals(new BigDecimal("123.45"), new TransactionParser(testInput).parseDecimal(new TransactionRange(7, 12), 2));
        assertEquals(new BigDecimal("123.45"), new TransactionParser(testInput).parseDecimal(new TransactionRange(7, 13), 2));
    }

    /**
     * If the string contain invalid char (Not 0-9), raise exception
     */
    @Test
    void testDecimalException() {
        String rawString = "123ABC123@";
        TransactionRange range = new TransactionRange(0, 10);
        Exception exception = assertThrows(TransactionParseException.class, () -> {
            new TransactionParser(rawString).parseDecimal(range, 0);
        });
        String expectedExceptionMessage = "Failed to parse transaction record " + rawString + " at " + range + ": " + "Invalid character in transaction record: " + "A";
        String actualExceptionMessage = exception.getMessage();
        assertTrue(actualExceptionMessage.contains(expectedExceptionMessage));
    }

    @Test
    void testClientInfoParsing() {
        String testInput = "315CL  432100020001SGXDC FUSGX NK    20100910JPY01B 0000000001 " +
                "0000000000000000000060DUSD000000000030DUSD000000000000DJPY201008200012380     688032000092500000000             O";

        TransactionRecord record = TransactionParser.parse(testInput);
        assertEquals("CL", record.getClientType());
        assertEquals("4321", record.getClientNumber().toString());
        assertEquals("2", record.getAccountNumber().toString());
        assertEquals("1", record.getSubaccountNumber().toString());
        assertEquals("CL_4321_2_1", record.getClientInfoKey());
    }

    @Test
    void testProductInfoParsing() {
        String testInput = "315CL  432100020001SGXDC FUSGX NK    20100910JPY01B 0000000001 " +
                "0000000000000000000060DUSD000000000030DUSD000000000000DJPY201008200012380     688032000092500000000             O";
        TransactionRecord record = TransactionParser.parse(testInput);
        assertEquals("SGX", record.getExchangeCode());
        assertEquals("FU", record.getProductGroupCode());
        assertEquals("NK", record.getSymbol());
        assertEquals("20100910", record.getExpirationDate().toString());
        assertEquals("SGX_FU_NK_20100910", record.getProductInfoKey());
    }

    @Test
    void testTransactionAmount() {
        String testInput = "315CL  432100020001SGXDC FUSGX NK    20100910JPY01B 0000000001 " +
                "0000000000000000000060DUSD000000000030DUSD000000000000DJPY201008200012380     688032000092500000000             O";
        TransactionRecord record = TransactionParser.parse(testInput);
        assertEquals(1, record.getTransactionAmount().intValue());
    }

}