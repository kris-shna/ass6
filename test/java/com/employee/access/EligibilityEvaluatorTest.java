package com.example.access;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EligibilityEvaluatorTest {

    @Test
    public void testNormalScenario_Eligible() {
        Employee emp = new Employee("E-OK-01", "Alice Rogers", 28, "IT", "Active", 4, true);
        EligibilityEvaluator.EvaluationResult res = EligibilityEvaluator.evaluate(emp, 3);
        assertEquals("Eligible", res.status);
        assertTrue(res.reasons.isEmpty());
    }

    @Test
    public void testBoundaryScenario_AgeExactly21() {
        Employee emp = new Employee("E-BD-02", "Marcus Vance", 21, "HR", "Active", 3, true);
        EligibilityEvaluator.EvaluationResult res = EligibilityEvaluator.evaluate(emp, 3);
        assertEquals("Eligible", res.status);
    }

    @Test
    public void testConditionallyEligible_InsufficientClearance() {
        Employee emp = new Employee("E-CD-03", "Elena Rostova", 35, "Finance", "Active", 2, true);
        EligibilityEvaluator.EvaluationResult res = EligibilityEvaluator.evaluate(emp, 5);
        assertEquals("Conditionally Eligible", res.status);
        assertEquals(1, res.reasons.size());
        assertTrue(res.reasons.get(0).contains("Elevated Security Warning"));
    }

    @Test
    public void testMultipleFailureScenario() {
        // Violating age, department whitelist, status flag, and ID token criteria concurrently.
        Employee emp = new Employee("E-ERR-04", "Invalid Entity", 18, "Logistics", "Terminated", 1, false);
        EligibilityEvaluator.EvaluationResult res = EligibilityEvaluator.evaluate(emp, 3);
        
        assertEquals("Not Eligible", res.status);
        // Assert that the pipeline collected all 4 errors down the chain
        assertEquals(4, res.reasons.size());
    }
}
