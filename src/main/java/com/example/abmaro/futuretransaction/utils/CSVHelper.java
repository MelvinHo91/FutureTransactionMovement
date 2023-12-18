package com.example.abmaro.futuretransaction.utils;

import com.example.abmaro.futuretransaction.SummaryReportRecord;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class CSVHelper {
    public static ByteArrayInputStream summaryReportRecordToCSV(Set<SummaryReportRecord> summaryReportRecords) {
        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader(SummaryReportRecord.SummaryReportRecordHeaders.class)
                .setSkipHeaderRecord(false)
                .build();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            for (SummaryReportRecord summaryReportRecord : summaryReportRecords) {
                List<String> data = Arrays.asList(
                        summaryReportRecord.getClientInformation(),
                        summaryReportRecord.getProductInformation(),
                        summaryReportRecord.getTotalTransactionAmount().toString()
                );
                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}
