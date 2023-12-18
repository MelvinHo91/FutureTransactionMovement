package com.example.abmaro.futuretransaction;

import com.example.abmaro.futuretransaction.utils.CSVHelper;
import com.example.abmaro.futuretransaction.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("future-transactions")
public class FutureTransactionController {
    @Autowired
    FutureTransactionService futureTransactionService;

    @PostMapping("daily-summary-report")
    ResponseEntity getDailySummaryReport(@RequestParam("transactionRecord")MultipartFile transactionRecord) {
        try {
            String outputFileName = "Output.csv";
            List<String> transactionRecords = FileUtil.getRecordList(transactionRecord);
            Set<SummaryReportRecord> summaryReportRecords = futureTransactionService.handleTransactionList(transactionRecords);
            InputStreamResource outputFile = new InputStreamResource(CSVHelper.summaryReportRecordToCSV(summaryReportRecords));
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + outputFileName)
                    .contentType(MediaType.parseMediaType("text/csv"))
                    .body(outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
