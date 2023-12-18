package com.example.abmaro.futuretransaction;

import java.util.List;
import java.util.Set;

public interface FutureTransactionService {
    Set<SummaryReportRecord> handleTransactionList(List<String> transactionRecords);
}
