package com.qa;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PrimerTest {

    @Test
    void elAmbienteDePruebasFunciona() {
        assertTrue("Citas Medicas".contains("Medicas"), "el mensaje debe contener Medicas");
        assertTrue(1 + 1 == 2, "1 + 1 debe ser 2");
    }
}
