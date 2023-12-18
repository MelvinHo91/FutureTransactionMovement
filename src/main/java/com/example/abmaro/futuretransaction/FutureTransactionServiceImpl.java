package com.example.abmaro.futuretransaction;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FutureTransactionServiceImpl implements FutureTransactionService{
    /**
     * To generate summary report record set that calculate
     * the total transaction amount for each client per product
     * and save to csv
     * @param transactionRecordRawStringList
     * @return summaryReportRecordSet
     */
    @Override
    public Set<SummaryReportRecord> handleTransactionList(List<String> transactionRecordRawStringList) {
        HashMap<String, SummaryReportRecord> summaryReportRecordMap = new HashMap<>();
        List<TransactionRecord> transactionRecords = transactionRecordRawStringList.stream().map(TransactionParser::parse).toList();
        for (TransactionRecord record: transactionRecords) {
            if (Objects.isNull(record)) {
                //Handle parse error case
                continue;
            }
            String summaryReportRecordMapKey = record.getClientInfoKey() + "_" + record.getProductInfoKey();
            if (summaryReportRecordMap.get(summaryReportRecordMapKey) != null) {
                summaryReportRecordMap.get(summaryReportRecordMapKey).addTotalTransactionAmount(record.getTransactionAmount());
            } else {
                summaryReportRecordMap.put(summaryReportRecordMapKey, new SummaryReportRecord(record.getClientInfoKey(), record.getProductInfoKey(), record.getTransactionAmount()));
            }
        }
        return new HashSet<>(summaryReportRecordMap.values());
    }
}
