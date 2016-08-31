package com.github.simpleliving40.boardgame.repository.search;

import com.github.simpleliving40.boardgame.domain.BoardGame;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the BoardGame entity.
 */
public interface BoardGameSearchRepository extends ElasticsearchRepository<BoardGame, Long> {
}
