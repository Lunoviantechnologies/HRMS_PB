package com.example.Exception;

import java.time.LocalTime;

public class AttendanceRules {

    public static final LocalTime OFFICE_START = LocalTime.of(9, 30);
    public static final LocalTime GRACE_END = LocalTime.of(9, 45);
    public static final int MAX_MONTHLY_LATE_PERMISSIONS = 3;
}

