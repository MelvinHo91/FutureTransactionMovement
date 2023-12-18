package com.example.abmaro.futuretransaction.utils;

import com.example.abmaro.futuretransaction.SummaryReportRecord;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.InputStreamResource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CSVHelperTest {


    @Test
    void summaryReportRecordToCSV() throws IOException {
        String testOutputCsv = "testOutput.csv";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(testOutputCsv).getFile());
        //Remark: because some os line separator is \n but some is \r\n, remove all line separator and just compare if the string content is equal
        String expectedOutput = Files.readString(file.toPath()).replace("\r", "").replace("\n", "");
        Set<SummaryReportRecord> summaryReportRecords = new HashSet<>();
        summaryReportRecords.add(new SummaryReportRecord("CL_1234_3_1", "CME_FU_NK._20100910", new BigDecimal(1)));
        summaryReportRecords.add(new SummaryReportRecord("CL_1234_2_1", "SGX_FU_NK_20100910", new BigDecimal(1)));
        summaryReportRecords.add(new SummaryReportRecord("CL_1234_3_1", "CME_FU_N1_20100910", new BigDecimal(1)));
        summaryReportRecords.add(new SummaryReportRecord("CL_4321_3_1", "CME_FU_N1_20100910", new BigDecimal(-3)));
        summaryReportRecords.add(new SummaryReportRecord("CL_4321_2_1", "SGX_FU_NK_20100910", new BigDecimal(1)));
        ByteArrayInputStream output = CSVHelper.summaryReportRecordToCSV(summaryReportRecords);
        //Remark: because some os line separator is \n but some is \r\n, remove all line separator and just compare if the string content is equal
        String actualOutput = new String(output.readAllBytes()).replace("\r", "").replace("\n", "");
        assertEquals(expectedOutput, actualOutput);
    }
}