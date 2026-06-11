package com.bidkita.bidkita_backend.dto.response;

import java.util.Date;

public class NotificationResponseDTO {

    private String notifId;
    private String message;
    private String type;
    private boolean isRead;
    private Date createdAt;

    public NotificationResponseDTO() {}

    public NotificationResponseDTO(String notifId, String message, String type, boolean isRead, Date createdAt) {
        this.notifId = notifId;
        this.message = message;
        this.type = type;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    // TODO: implement after Model is complete
    // public NotificationResponseDTO(Notification notification) { ... }

    public String getNotifId() { return notifId; }
    public void setNotifId(String notifId) { this.notifId = notifId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public boolean isRead() { return isRead; }
    public void setRead(boolean isRead) { this.isRead = isRead; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
