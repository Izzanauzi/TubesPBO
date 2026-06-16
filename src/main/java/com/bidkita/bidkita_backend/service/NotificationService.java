package com.bidkita.bidkita_backend.service;

import com.bidkita.bidkita_backend.dto.response.NotificationResponseDTO;
import com.bidkita.bidkita_backend.model.Notification;
import com.bidkita.bidkita_backend.model.User;
import com.bidkita.bidkita_backend.model.enums.NotificationType;
import com.bidkita.bidkita_backend.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void send(User recipient, String message, NotificationType type) {
        if (recipient == null) return;
        Notification notif = new Notification(recipient, message, type);
        notificationRepository.save(notif);
    }

    public List<NotificationResponseDTO> getAll(String userId) {
        List<Notification> notifs = notificationRepository
                .findByRecipientUserIdOrderByCreatedAtDesc(userId);
        notifs.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(notifs);
        return notifs.stream().map(this::toDTO).toList();
    }

    private NotificationResponseDTO toDTO(Notification n) {
        return new NotificationResponseDTO(
                n.getNotifId(),
                n.getMessage(),
                n.getType().name(),
                n.isRead(),
                n.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        );
    }
}
