package com.example.abmaro.futuretransaction;


/**
 * Represents a text selection range
 */
public record TransactionRange(int column, int columnTo) {
    public TransactionRange {
        if (column > columnTo) {
            throw new IllegalArgumentException("Parameter column: invalid value " + column + ": must be less than " + columnTo);
        }
    }

    @Override
    public String toString() {
        return "" + column + "-" + columnTo;
    }

    public int length() {
        return columnTo - column;
    }
}
