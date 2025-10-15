package com.example.Notification;

public class NotificationRequest {
    private String senderEmail;
    private String receiverEmail;
    private String actionType;
    private String message;

    // --- Getters & Setters ---
    public String getSenderEmail() {
        return senderEmail;
    }
    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }
    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getActionType() {
        return actionType;
    }
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getmessage() {
        return message;
    }
    public void setmessage(String message) {
        this.message = message;
    }
}