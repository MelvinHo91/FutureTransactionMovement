package com.example.abmaro.futuretransaction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FutureTransactionServiceImplTest {

    @Autowired
    FutureTransactionService futureTransactionService;

    /**
     *  Test case should contain:
     *  1. Same summaryReportRecordMapKey(client info + product info), but different transaction amount
     *  2. Same summaryReportRecordMapKey(client info + product info) with filler
     *  3. Different summaryReportRecordMapKey - Different client info
     *  4. Different summaryReportRecordMapKey - Different product info
     *  5. Invalid record (length not match) - should be ignored
     *  6. Invalid record (Invalid char) - should be ignored
     */
    @Test
    void handleTransactionList() {
        List<String> transactionList = new ArrayList<>();
        //Case 1 - Same summaryReportRecordMapKey(client info + product info), but different transaction amount
        transactionList.add("315CL  432100020001SGXDC FUSGX NK    20100910JPY01B 0000000001 " +
                "0000000000000000000060DUSD000000000030DUSD000000000000DJPY201008200012380     688032000092500000000             O");
        transactionList.add("315CL  432100020001SGXDC FUSGX NK    20100910JPY01B 0000000003 " +
                "0000000001000000000060DUSD000000000030DUSD000000000000DJPY201008200012380     688032000092500000000             O");
        transactionList.add("315CL  432100020001SGXDC FUSGX NK    20100910JPY01B 0000000002 " +
                "0000000004000000000060DUSD000000000030DUSD000000000000DJPY201008200012380     688032000092500000000             O");
        //Case 2 - Same summaryReportRecordMapKey(client info + product info) with filler
        transactionList.add("315CL  432100020001SGXDC FUSGX NK    20100910JPY01B 0000000001 " +
                "0000000000000000000060DUSD000000000030DUSD000000000000DJPY201008200012380     688032000092500000000             O" +
                "                                                                                                                               ");
        //Case 3 - Different summaryReportRecordMapKey - Different client info
        transactionList.add("315CL  123400020001SGXDC FUSGX NK    20100910JPY01B 0000000001 " +
                "0000000000000000000060DUSD000000000030DUSD000000000000DJPY201008200012380     688032000092500000000             O");
        //Case 4 - Different summaryReportRecordMapKey - Different product info
        transactionList.add("315CL  432100020001SGXDC FUSGX N1    20100910JPY01B 0000000001 " +
                "0000000000000000000060DUSD000000000030DUSD000000000000DJPY201008200012380     688032000092500000000             O");
        //Case 5 - Invalid record (length not match) - should be ignored
        transactionList.add("315CL  432100020001SGXDC FUSGX N1    20100910JPY01B 0000000001 " +
                "0000000000000000000060DUSD000000000030DUSD000000000000DJPY201008200012380     688032000092500000000             ");
        //Case 6 - Invalid record (Invalid char) - should be ignored
        transactionList.add("315CL  432100020001SGXDC FUSGX N@    20100910JPY01B 0000000001 " +
                "0000000000000000000060DUSD000000000030DUSD000000000000DJPY201008200012380     688032000092500000000             O");
        Set<SummaryReportRecord> expectedSummaryReportRecordList = new HashSet<>();
        expectedSummaryReportRecordList.add(new SummaryReportRecord("CL_4321_2_1", "SGX_FU_NK_20100910", new BigDecimal(2)));
        expectedSummaryReportRecordList.add(new SummaryReportRecord("CL_1234_2_1", "SGX_FU_NK_20100910", new BigDecimal(1)));
        expectedSummaryReportRecordList.add(new SummaryReportRecord("CL_4321_2_1", "SGX_FU_N1_20100910", new BigDecimal(1)));
        Set<SummaryReportRecord> actualSummaryReportRecordList = futureTransactionService.handleTransactionList(transactionList);
        assertEquals(expectedSummaryReportRecordList, actualSummaryReportRecordList);
    }
}