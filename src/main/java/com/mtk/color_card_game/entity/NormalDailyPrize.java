package com.mtk.color_card_game.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "NORMAL_PRIZES")
public class NormalDailyPrize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DAY")
    private Integer day;

    @Column(name = "AVAILABLE_QUANTITY")
    private Integer availableQuantity;

    @ManyToOne
    private Prize prize;
}
