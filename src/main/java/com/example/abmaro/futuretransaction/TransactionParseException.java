package com.example.abmaro.futuretransaction;

/**
 * Thrown when a record parse fails.
 */
public class TransactionParseException extends RuntimeException{
    /**
     * The Raw string being parsed.
     */
    public final String rawString;
    /**
     * Range containing problematic characters.
     */
    public final TransactionRange range;

    public TransactionParseException(String message, String rawString, TransactionRange range) {
        super("Failed to parse transaction record " + rawString + " at " + range + ": " + message);
        this.range = range;
        this.rawString = rawString;
    }
}
