package com.example.abmaro.futuretransaction.types;

/**
 * To hold the record date
 * @param year
 * @param month
 * @param day
 */
public record Date(int year, int month, int day){
    @Override
    public String toString() {
        return String.format("%04d%02d%02d", year, month, day);
    }
}
