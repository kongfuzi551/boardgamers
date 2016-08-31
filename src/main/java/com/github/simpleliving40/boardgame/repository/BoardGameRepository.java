package com.github.simpleliving40.boardgame.repository;

import com.github.simpleliving40.boardgame.domain.BoardGame;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BoardGame entity.
 */
@SuppressWarnings("unused")
public interface BoardGameRepository extends JpaRepository<BoardGame,Long> {

}
