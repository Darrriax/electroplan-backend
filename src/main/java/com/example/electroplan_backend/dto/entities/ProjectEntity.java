package com.example.electroplan_backend.dto.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "display_name", nullable = false, length = 255)
    private String displayName;

    @Column(name = "seller_id", nullable = false)
    private Long sellerId;

    @Column(name = "body_type", nullable = false, length = 100)
    private String bodyType;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "manufacturer", nullable = false, length = 255)
    private String manufacturer;

    @Column(name = "vin_code", nullable = false, unique = true, length = 255)
    private String vinCode;

    @Column(name = "was_in_accident")
    private Boolean wasInAccident;

    @Column(name = "is_trade")
    private Boolean isTrade;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @Column(name = "mileage", nullable = false)
    private Integer mileage;

    @Column(name = "technical_condition", nullable = false, length = 255)
    private String technicalCondition;
}
