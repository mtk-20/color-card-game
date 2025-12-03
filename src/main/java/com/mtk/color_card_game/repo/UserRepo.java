package com.mtk.color_card_game.repo;

import com.mtk.color_card_game.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
