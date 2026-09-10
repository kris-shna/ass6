package com.example.access;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EligibilityEvaluator {
    private static final List<String> AUTHORIZED_DEPARTMENTS = Arrays.asList("IT", "HR", "FINANCE", "ADMINISTRATION");

    public static class EvaluationResult {
        public String status;
        public List<String> reasons = new ArrayList<>();

        public EvaluationResult(String status) {
            this.status = status;
        }
    }

    public static EvaluationResult evaluate(Employee emp, int requiredAccessLevel) {
        if (emp == null) {
            throw new IllegalArgumentException("Employee record validation payload cannot be null.");
        }

        List<String> rejections = new ArrayList<>();
        
        // 1. Age Evaluation Boundary
        if (emp.getAge() < 21) {
            rejections.add("Underage Access Breach: Employee must be at least 21 years old (Current Age: " + emp.getAge() + ").");
        }

        // 2. Department Authentication Check
        if (emp.getDepartment() == null || !AUTHORIZED_DEPARTMENTS.contains(emp.getDepartment().toUpperCase())) {
            rejections.add("Routing Authorization Denied: Department '" + emp.getDepartment() + "' is not whitelisted.");
        }

        // 3. Status Validation
        if (emp.getEmploymentType() == null || !emp.getEmploymentType().equalsIgnoreCase("ACTIVE")) {
            rejections.add("Lifecycle Exception: Employment status is inactive (Current Status: " + emp.getEmploymentType() + ").");
        }

        // 4. Token Check
        if (!emp.isIdValid()) {
            rejections.add("Hardware Signature Failure: Employee identification card state is flagged INVALID.");
        }

        // Processing Multiple Failure Scenarios Accurately 
        if (!rejections.isEmpty()) {
            EvaluationResult res = new EvaluationResult("Not Eligible");
            res.reasons = rejections;
            return res;
        }

        // 5. Confined Resource Clearance Matching
        if (emp.getSecurityClearanceLevel() < requiredAccessLevel) {
            EvaluationResult res = new EvaluationResult("Conditionally Eligible");
            res.reasons.add("Elevated Security Warning: Level (" + emp.getSecurityClearanceLevel() + 
                ") below requested resource zone (" + requiredAccessLevel + "). Dropping to baseline clearance permissions.");
            return res;
        }

        return new EvaluationResult("Eligible");
    }
}
