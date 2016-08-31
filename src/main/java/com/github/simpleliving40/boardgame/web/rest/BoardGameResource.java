package com.github.simpleliving40.boardgame.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.github.simpleliving40.boardgame.domain.BoardGame;
import com.github.simpleliving40.boardgame.repository.BoardGameRepository;
import com.github.simpleliving40.boardgame.repository.search.BoardGameSearchRepository;
import com.github.simpleliving40.boardgame.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing BoardGame.
 */
@RestController
@RequestMapping("/api")
public class BoardGameResource {

    private final Logger log = LoggerFactory.getLogger(BoardGameResource.class);
        
    @Inject
    private BoardGameRepository boardGameRepository;
    
    @Inject
    private BoardGameSearchRepository boardGameSearchRepository;
    
    /**
     * POST  /board-games : Create a new boardGame.
     *
     * @param boardGame the boardGame to create
     * @return the ResponseEntity with status 201 (Created) and with body the new boardGame, or with status 400 (Bad Request) if the boardGame has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/board-games",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BoardGame> createBoardGame(@RequestBody BoardGame boardGame) throws URISyntaxException {
        log.debug("REST request to save BoardGame : {}", boardGame);
        if (boardGame.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("boardGame", "idexists", "A new boardGame cannot already have an ID")).body(null);
        }
        BoardGame result = boardGameRepository.save(boardGame);
        boardGameSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/board-games/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("boardGame", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /board-games : Updates an existing boardGame.
     *
     * @param boardGame the boardGame to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated boardGame,
     * or with status 400 (Bad Request) if the boardGame is not valid,
     * or with status 500 (Internal Server Error) if the boardGame couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/board-games",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BoardGame> updateBoardGame(@RequestBody BoardGame boardGame) throws URISyntaxException {
        log.debug("REST request to update BoardGame : {}", boardGame);
        if (boardGame.getId() == null) {
            return createBoardGame(boardGame);
        }
        BoardGame result = boardGameRepository.save(boardGame);
        boardGameSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("boardGame", boardGame.getId().toString()))
            .body(result);
    }

    /**
     * GET  /board-games : get all the boardGames.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of boardGames in body
     */
    @RequestMapping(value = "/board-games",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BoardGame> getAllBoardGames() {
        log.debug("REST request to get all BoardGames");
        List<BoardGame> boardGames = boardGameRepository.findAll();
        return boardGames;
    }

    /**
     * GET  /board-games/:id : get the "id" boardGame.
     *
     * @param id the id of the boardGame to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the boardGame, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/board-games/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BoardGame> getBoardGame(@PathVariable Long id) {
        log.debug("REST request to get BoardGame : {}", id);
        BoardGame boardGame = boardGameRepository.findOne(id);
        return Optional.ofNullable(boardGame)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /board-games/:id : delete the "id" boardGame.
     *
     * @param id the id of the boardGame to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/board-games/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBoardGame(@PathVariable Long id) {
        log.debug("REST request to delete BoardGame : {}", id);
        boardGameRepository.delete(id);
        boardGameSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("boardGame", id.toString())).build();
    }

    /**
     * SEARCH  /_search/board-games?query=:query : search for the boardGame corresponding
     * to the query.
     *
     * @param query the query of the boardGame search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/board-games",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BoardGame> searchBoardGames(@RequestParam String query) {
        log.debug("REST request to search BoardGames for query {}", query);
        return StreamSupport
            .stream(boardGameSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
