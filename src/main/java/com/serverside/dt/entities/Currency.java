package com.serverside.dt.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "currency")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Currency {

    @Id
    @Column(name = "currency_code", length = 3, nullable = false)
    private String code;

    @Column(name = "currency_name", length = 50, nullable = false)
    private String name;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_updated_date", nullable = false)
    private LocalDateTime lastUpdatedDate;
}
