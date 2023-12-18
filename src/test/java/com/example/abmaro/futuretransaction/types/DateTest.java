package com.example.abmaro.futuretransaction.types;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateTest {

    @Test
    void testToString() {
        assertEquals("19990501", new Date(1999, 5, 1).toString());
        assertEquals("00010501", new Date(1, 5, 1).toString());
        assertEquals("19990541", new Date(1999, 5, 41).toString());
    }

    @Test
    void testEquals() {
        assertEquals(new Date(0, 1, 1), new Date(0, 1, 1));
        assertEquals(new Date(1999, 4, 31), new Date(1999, 4, 31));
        assertFalse(new Date(1999, 4, 31).equals(new Date(1999, 4, 30)));
        assertFalse(new Date(1999, 4, 31).equals(new Date(1998, 4, 31)));
        assertFalse(new Date(1999, 4, 31).equals(new Date(1999, 3, 31)));
        assertFalse(new Date(1999, 4, 30).equals(new Date(1999, 4, 31)));
        assertFalse(new Date(1999, 3, 31).equals(new Date(1999, 4, 31)));
        assertFalse(new Date(1998, 4, 31).equals(new Date(1999, 4, 31)));
    }
}