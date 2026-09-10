package com.employee.access;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Employee> employees = new ArrayList<>();
        
        System.out.println("=== Corporate Identity Access Validation Console ===");
        System.out.print("Enter structural evaluation sample size: ");
        int count = 0;
        try {
            count = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Input execution vector faulted. Terminating operations.");
            return;
        }

        for (int i = 0; i < count; i++) {
            System.out.println("\n--- Processing Entry Data Pool #" + (i + 1) + " ---");
            try {
                System.out.print("ID: ");
                String id = scanner.nextLine().trim();
                if (id.isEmpty()) throw new IllegalArgumentException("Key parameter 'ID' cannot be blank.");

                System.out.print("Name: ");
                String name = scanner.nextLine().trim();

                System.out.print("Age: ");
                int age = Integer.parseInt(scanner.nextLine().trim());

                System.out.print("Department (IT/HR/Finance/Administration): ");
                String dept = scanner.nextLine().trim();

                System.out.print("Status (Active/Suspended): ");
                String type = scanner.nextLine().trim();

                System.out.print("Security Tier Integer (1-5): ");
                int clearance = Integer.parseInt(scanner.nextLine().trim());

                System.out.print("ID Validation Flag (true/false): ");
                boolean isValid = Boolean.parseBoolean(scanner.nextLine().trim());

                employees.add(new Employee(id, name, age, dept, type, clearance, isValid));
            } catch (Exception e) {
                System.out.println("Processing Engine Fault: " + e.getMessage() + " Skipping current entry index.");
            }
        }

        System.out.print("\nSet Resource Target Evaluation Clearance Threshold (1-5): ");
        int targetThreshold = 1;
        try {
            targetThreshold = Integer.parseInt(scanner.nextLine().trim());
        } catch (Exception e) {
            System.out.println("Level mismatch. Reverting to base layer authorization (Tier 1).");
        }

        System.out.println("\n================ STRUCTURAL COMPLIANCE REPORT ================");
        for (Employee emp : employees) {
            EligibilityEvaluator.EvaluationResult res = EligibilityEvaluator.evaluate(emp, targetThreshold);
            System.out.println("ID TOKEN: " + emp.getId() + " | ENTITY IDENTITY: " + emp.getName());
            System.out.println("COMPLIANCE RATING: -> [ " + res.status + " ]");
            if (!res.reasons.isEmpty()) {
                System.out.println("System Log Exceptions / Breach Identifiers:");
                for (String reason : res.reasons) {
                    System.out.println("  • " + reason);
                }
            }
            System.out.println("-------------------------------------------------------------");
        }
        scanner.close();
    }
}
