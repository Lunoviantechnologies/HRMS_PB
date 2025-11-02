package com.example.EmployeeRegisteration;

public class ValidationUtils {

	 // Password must be 8-15 characters, include upper, lower, digit, special char
    public static boolean isValidPassword(String password) {
        return password != null &&
               password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,15}$");
    }
}
