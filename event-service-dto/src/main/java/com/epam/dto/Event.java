package com.epam.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "events")
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    private String title;
    private String place;
    private String speaker;

    @Enumerated(value = EnumType.ORDINAL)
    private EventType eventType;
    private LocalDateTime dateTime;

}

