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
@Table(name = "PRIZE")
public class Prize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PRIZE_RANK")
    private int prizeRank;

    @Column(name = "PRIZE_NAME")
    private String prizeName;

    @Column(name = "QUANTITY")
    private int quantity;
}
