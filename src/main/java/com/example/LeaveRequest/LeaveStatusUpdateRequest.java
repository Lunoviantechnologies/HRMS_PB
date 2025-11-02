package com.example.LeaveRequest;

public class LeaveStatusUpdateRequest {
    private LeaveRequest.LeaveStatus status;

    public LeaveRequest.LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveRequest.LeaveStatus status) {
        this.status = status;
    }
}
