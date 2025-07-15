package com.sklyar1091.todo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @Column(name = "description", length = 256, nullable = false)
    private String description;

    @Column(name = "dead_line", nullable = false)
    private LocalDateTime deadLine;

    @Column(name = "status", length = 24, nullable = false) //todo сделать длинну больше 10 (24)
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;
}
