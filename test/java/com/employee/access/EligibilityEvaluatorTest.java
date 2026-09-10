package com.employee.access;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EligibilityEvaluatorTest {

    // ==========================================
    // 1. NORMAL / HAPPY PATH SCENARIOS
    // ==========================================

    @Test
    public void testNormalScenario_Eligible() {
        // All fields perfectly valid, clearance (4) exceeds required target (3)
        Employee emp = new Employee("E-OK-01", "Alice Rogers", 28, "IT", "Active", 4, true);
        EligibilityEvaluator.EvaluationResult res = EligibilityEvaluator.evaluate(emp, 3);
        
        assertEquals("Eligible", res.status);
        assertTrue(res.reasons.isEmpty());
    }

    @Test
    public void testCaseInsensitiveDepartment() {
        // Department entered in lowercase ("hr") should still be whitelisted successfully
        Employee emp = new Employee("E-OK-02", "Bob Smith", 30, "hr", "Active", 3, true);
        EligibilityEvaluator.EvaluationResult res = EligibilityEvaluator.evaluate(emp, 3);
        
        assertEquals("Eligible", res.status);
    }

    // ==========================================
    // 2. BOUNDARY CONDITION SCENARIOS
    // ==========================================

    @Test
    public void testBoundaryScenario_AgeExactly21() {
        // Age is exactly on the legal boundary line (21)
        Employee emp = new Employee("E-BD-01", "Marcus Vance", 21, "HR", "Active", 3, true);
        EligibilityEvaluator.EvaluationResult res = EligibilityEvaluator.evaluate(emp, 3);
        
        assertEquals("Eligible", res.status);
    }

    @Test
    public void testBoundaryScenario_AgeExactly20() {
        // Age is exactly one unit below the boundary threshold (20) -> Should fail
        Employee emp = new Employee("E-BD-02", "John Doe", 20, "Finance", "Active", 3, true);
        EligibilityEvaluator.EvaluationResult res = EligibilityEvaluator.evaluate(emp, 3);
        
        assertEquals("Not Eligible", res.status);
        assertEquals(1, res.reasons.size());
        assertTrue(res.reasons.get(0).contains("Underage Access Breach"));
    }

    // ==========================================
    // 3. CONDITIONAL ELIGIBILITY SCENARIOS
    // ==========================================

    @Test
    public void testConditionallyEligible_InsufficientClearance() {
        // Passes core rules, but clearance tier (2) is below resource requirement (5)
        Employee emp = new Employee("E-CD-01", "Elena Rostova", 35, "Finance", "Active", 2, true);
        EligibilityEvaluator.EvaluationResult res = EligibilityEvaluator.evaluate(emp, 5);
        
        assertEquals("Conditionally Eligible", res.status);
        assertEquals(1, res.reasons.size());
        assertTrue(res.reasons.get(0).contains("Elevated Security Warning"));
    }

    // ==========================================
    // 4. MULTIPLE BREACH / FAILURE SCENARIOS
    // ==========================================

    @Test
    public void testPartialFailureScenario_WrongDepartmentAndInvalidId() {
        // Age and status are OK, but department is non-whitelisted and ID flag is false
        Employee emp = new Employee("E-ERR-01", "Dave Miller", 25, "Logistics", "Active", 3, false);
        EligibilityEvaluator.EvaluationResult res = EligibilityEvaluator.evaluate(emp, 3);
        
        assertEquals("Not Eligible", res.status);
        // The accumulative error tracking system must pick up exactly 2 breaches
        assertEquals(2, res.reasons.size()); 
    }

    @Test
    public void testMaximumFailureScenario_AllRulesBreached() {
        // Violating age, department, status string, and hardware ID parameters simultaneously
        Employee emp = new Employee("E-ERR-02", "Malicious Entity", 17, "Sales", "Terminated", 1, false);
        EligibilityEvaluator.EvaluationResult res = EligibilityEvaluator.evaluate(emp, 5);
        
        assertEquals("Not Eligible", res.status);
        // Pipeline must collect all 4 major system barriers down the verification grid
        assertEquals(4, res.reasons.size());
    }

    // ==========================================
    // 5. EXCEPTION / INVALID INPUT HANDLING
    // ==========================================

    @Test
    public void testExceptionHandling_NullEmployeePayload() {
        // Passing a null reference should immediately break validation with an exception
        assertThrows(IllegalArgumentException.class, () -> {
            EligibilityEvaluator.evaluate(null, 3);
        });
    }
}
