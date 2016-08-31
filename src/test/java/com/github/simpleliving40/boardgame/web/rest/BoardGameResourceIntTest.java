package com.github.simpleliving40.boardgame.web.rest;

import com.github.simpleliving40.boardgame.BoardGamesApp;
import com.github.simpleliving40.boardgame.domain.BoardGame;
import com.github.simpleliving40.boardgame.repository.BoardGameRepository;
import com.github.simpleliving40.boardgame.repository.search.BoardGameSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the BoardGameResource REST controller.
 *
 * @see BoardGameResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BoardGamesApp.class)
@WebAppConfiguration
@IntegrationTest
public class BoardGameResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Integer DEFAULT_YEAR_PUBLISHED = 1;
    private static final Integer UPDATED_YEAR_PUBLISHED = 2;

    private static final Integer DEFAULT_MIN_PLAYERS = 1;
    private static final Integer UPDATED_MIN_PLAYERS = 2;

    private static final Integer DEFAULT_MAX_PLAYERS = 1;
    private static final Integer UPDATED_MAX_PLAYERS = 2;

    private static final Integer DEFAULT_PLAYING_TIME = 1;
    private static final Integer UPDATED_PLAYING_TIME = 2;

    private static final Integer DEFAULT_MIN_AGE = 1;
    private static final Integer UPDATED_MIN_AGE = 2;

    private static final Integer DEFAULT_MIN_PLAYTIME = 1;
    private static final Integer UPDATED_MIN_PLAYTIME = 2;

    private static final Integer DEFAULT_MAX_PLAYTIME = 1;
    private static final Integer UPDATED_MAX_PLAYTIME = 2;
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_THUMBNAIL = "AAAAA";
    private static final String UPDATED_THUMBNAIL = "BBBBB";

    private static final Integer DEFAULT_USERS_RATED = 1;
    private static final Integer UPDATED_USERS_RATED = 2;

    private static final Double DEFAULT_AVG_RATING = 1D;
    private static final Double UPDATED_AVG_RATING = 2D;

    private static final Double DEFAULT_BAYES_AVG_RATING = 1D;
    private static final Double UPDATED_BAYES_AVG_RATING = 2D;

    private static final Integer DEFAULT_RANK = 1;
    private static final Integer UPDATED_RANK = 2;
    private static final String DEFAULT_IMAGE = "AAAAA";
    private static final String UPDATED_IMAGE = "BBBBB";

    @Inject
    private BoardGameRepository boardGameRepository;

    @Inject
    private BoardGameSearchRepository boardGameSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBoardGameMockMvc;

    private BoardGame boardGame;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BoardGameResource boardGameResource = new BoardGameResource();
        ReflectionTestUtils.setField(boardGameResource, "boardGameSearchRepository", boardGameSearchRepository);
        ReflectionTestUtils.setField(boardGameResource, "boardGameRepository", boardGameRepository);
        this.restBoardGameMockMvc = MockMvcBuilders.standaloneSetup(boardGameResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        boardGameSearchRepository.deleteAll();
        boardGame = new BoardGame();
        boardGame.setName(DEFAULT_NAME);
        boardGame.setYearPublished(DEFAULT_YEAR_PUBLISHED);
        boardGame.setMinPlayers(DEFAULT_MIN_PLAYERS);
        boardGame.setMaxPlayers(DEFAULT_MAX_PLAYERS);
        boardGame.setPlayingTime(DEFAULT_PLAYING_TIME);
        boardGame.setMinAge(DEFAULT_MIN_AGE);
        boardGame.setMinPlaytime(DEFAULT_MIN_PLAYTIME);
        boardGame.setMaxPlaytime(DEFAULT_MAX_PLAYTIME);
        boardGame.setDescription(DEFAULT_DESCRIPTION);
        boardGame.setThumbnail(DEFAULT_THUMBNAIL);
        boardGame.setUsersRated(DEFAULT_USERS_RATED);
        boardGame.setAvgRating(DEFAULT_AVG_RATING);
        boardGame.setBayesAvgRating(DEFAULT_BAYES_AVG_RATING);
        boardGame.setRank(DEFAULT_RANK);
        boardGame.setImage(DEFAULT_IMAGE);
    }

    @Test
    @Transactional
    public void createBoardGame() throws Exception {
        int databaseSizeBeforeCreate = boardGameRepository.findAll().size();

        // Create the BoardGame

        restBoardGameMockMvc.perform(post("/api/board-games")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(boardGame)))
                .andExpect(status().isCreated());

        // Validate the BoardGame in the database
        List<BoardGame> boardGames = boardGameRepository.findAll();
        assertThat(boardGames).hasSize(databaseSizeBeforeCreate + 1);
        BoardGame testBoardGame = boardGames.get(boardGames.size() - 1);
        assertThat(testBoardGame.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBoardGame.getYearPublished()).isEqualTo(DEFAULT_YEAR_PUBLISHED);
        assertThat(testBoardGame.getMinPlayers()).isEqualTo(DEFAULT_MIN_PLAYERS);
        assertThat(testBoardGame.getMaxPlayers()).isEqualTo(DEFAULT_MAX_PLAYERS);
        assertThat(testBoardGame.getPlayingTime()).isEqualTo(DEFAULT_PLAYING_TIME);
        assertThat(testBoardGame.getMinAge()).isEqualTo(DEFAULT_MIN_AGE);
        assertThat(testBoardGame.getMinPlaytime()).isEqualTo(DEFAULT_MIN_PLAYTIME);
        assertThat(testBoardGame.getMaxPlaytime()).isEqualTo(DEFAULT_MAX_PLAYTIME);
        assertThat(testBoardGame.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBoardGame.getThumbnail()).isEqualTo(DEFAULT_THUMBNAIL);
        assertThat(testBoardGame.getUsersRated()).isEqualTo(DEFAULT_USERS_RATED);
        assertThat(testBoardGame.getAvgRating()).isEqualTo(DEFAULT_AVG_RATING);
        assertThat(testBoardGame.getBayesAvgRating()).isEqualTo(DEFAULT_BAYES_AVG_RATING);
        assertThat(testBoardGame.getRank()).isEqualTo(DEFAULT_RANK);
        assertThat(testBoardGame.getImage()).isEqualTo(DEFAULT_IMAGE);

        // Validate the BoardGame in ElasticSearch
        BoardGame boardGameEs = boardGameSearchRepository.findOne(testBoardGame.getId());
        assertThat(boardGameEs).isEqualToComparingFieldByField(testBoardGame);
    }

    @Test
    @Transactional
    public void getAllBoardGames() throws Exception {
        // Initialize the database
        boardGameRepository.saveAndFlush(boardGame);

        // Get all the boardGames
        restBoardGameMockMvc.perform(get("/api/board-games?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(boardGame.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].yearPublished").value(hasItem(DEFAULT_YEAR_PUBLISHED)))
                .andExpect(jsonPath("$.[*].minPlayers").value(hasItem(DEFAULT_MIN_PLAYERS)))
                .andExpect(jsonPath("$.[*].maxPlayers").value(hasItem(DEFAULT_MAX_PLAYERS)))
                .andExpect(jsonPath("$.[*].playingTime").value(hasItem(DEFAULT_PLAYING_TIME)))
                .andExpect(jsonPath("$.[*].minAge").value(hasItem(DEFAULT_MIN_AGE)))
                .andExpect(jsonPath("$.[*].minPlaytime").value(hasItem(DEFAULT_MIN_PLAYTIME)))
                .andExpect(jsonPath("$.[*].maxPlaytime").value(hasItem(DEFAULT_MAX_PLAYTIME)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].thumbnail").value(hasItem(DEFAULT_THUMBNAIL.toString())))
                .andExpect(jsonPath("$.[*].usersRated").value(hasItem(DEFAULT_USERS_RATED)))
                .andExpect(jsonPath("$.[*].avgRating").value(hasItem(DEFAULT_AVG_RATING.doubleValue())))
                .andExpect(jsonPath("$.[*].bayesAvgRating").value(hasItem(DEFAULT_BAYES_AVG_RATING.doubleValue())))
                .andExpect(jsonPath("$.[*].rank").value(hasItem(DEFAULT_RANK)))
                .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())));
    }

    @Test
    @Transactional
    public void getBoardGame() throws Exception {
        // Initialize the database
        boardGameRepository.saveAndFlush(boardGame);

        // Get the boardGame
        restBoardGameMockMvc.perform(get("/api/board-games/{id}", boardGame.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(boardGame.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.yearPublished").value(DEFAULT_YEAR_PUBLISHED))
            .andExpect(jsonPath("$.minPlayers").value(DEFAULT_MIN_PLAYERS))
            .andExpect(jsonPath("$.maxPlayers").value(DEFAULT_MAX_PLAYERS))
            .andExpect(jsonPath("$.playingTime").value(DEFAULT_PLAYING_TIME))
            .andExpect(jsonPath("$.minAge").value(DEFAULT_MIN_AGE))
            .andExpect(jsonPath("$.minPlaytime").value(DEFAULT_MIN_PLAYTIME))
            .andExpect(jsonPath("$.maxPlaytime").value(DEFAULT_MAX_PLAYTIME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.thumbnail").value(DEFAULT_THUMBNAIL.toString()))
            .andExpect(jsonPath("$.usersRated").value(DEFAULT_USERS_RATED))
            .andExpect(jsonPath("$.avgRating").value(DEFAULT_AVG_RATING.doubleValue()))
            .andExpect(jsonPath("$.bayesAvgRating").value(DEFAULT_BAYES_AVG_RATING.doubleValue()))
            .andExpect(jsonPath("$.rank").value(DEFAULT_RANK))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBoardGame() throws Exception {
        // Get the boardGame
        restBoardGameMockMvc.perform(get("/api/board-games/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBoardGame() throws Exception {
        // Initialize the database
        boardGameRepository.saveAndFlush(boardGame);
        boardGameSearchRepository.save(boardGame);
        int databaseSizeBeforeUpdate = boardGameRepository.findAll().size();

        // Update the boardGame
        BoardGame updatedBoardGame = new BoardGame();
        updatedBoardGame.setId(boardGame.getId());
        updatedBoardGame.setName(UPDATED_NAME);
        updatedBoardGame.setYearPublished(UPDATED_YEAR_PUBLISHED);
        updatedBoardGame.setMinPlayers(UPDATED_MIN_PLAYERS);
        updatedBoardGame.setMaxPlayers(UPDATED_MAX_PLAYERS);
        updatedBoardGame.setPlayingTime(UPDATED_PLAYING_TIME);
        updatedBoardGame.setMinAge(UPDATED_MIN_AGE);
        updatedBoardGame.setMinPlaytime(UPDATED_MIN_PLAYTIME);
        updatedBoardGame.setMaxPlaytime(UPDATED_MAX_PLAYTIME);
        updatedBoardGame.setDescription(UPDATED_DESCRIPTION);
        updatedBoardGame.setThumbnail(UPDATED_THUMBNAIL);
        updatedBoardGame.setUsersRated(UPDATED_USERS_RATED);
        updatedBoardGame.setAvgRating(UPDATED_AVG_RATING);
        updatedBoardGame.setBayesAvgRating(UPDATED_BAYES_AVG_RATING);
        updatedBoardGame.setRank(UPDATED_RANK);
        updatedBoardGame.setImage(UPDATED_IMAGE);

        restBoardGameMockMvc.perform(put("/api/board-games")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedBoardGame)))
                .andExpect(status().isOk());

        // Validate the BoardGame in the database
        List<BoardGame> boardGames = boardGameRepository.findAll();
        assertThat(boardGames).hasSize(databaseSizeBeforeUpdate);
        BoardGame testBoardGame = boardGames.get(boardGames.size() - 1);
        assertThat(testBoardGame.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBoardGame.getYearPublished()).isEqualTo(UPDATED_YEAR_PUBLISHED);
        assertThat(testBoardGame.getMinPlayers()).isEqualTo(UPDATED_MIN_PLAYERS);
        assertThat(testBoardGame.getMaxPlayers()).isEqualTo(UPDATED_MAX_PLAYERS);
        assertThat(testBoardGame.getPlayingTime()).isEqualTo(UPDATED_PLAYING_TIME);
        assertThat(testBoardGame.getMinAge()).isEqualTo(UPDATED_MIN_AGE);
        assertThat(testBoardGame.getMinPlaytime()).isEqualTo(UPDATED_MIN_PLAYTIME);
        assertThat(testBoardGame.getMaxPlaytime()).isEqualTo(UPDATED_MAX_PLAYTIME);
        assertThat(testBoardGame.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBoardGame.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testBoardGame.getUsersRated()).isEqualTo(UPDATED_USERS_RATED);
        assertThat(testBoardGame.getAvgRating()).isEqualTo(UPDATED_AVG_RATING);
        assertThat(testBoardGame.getBayesAvgRating()).isEqualTo(UPDATED_BAYES_AVG_RATING);
        assertThat(testBoardGame.getRank()).isEqualTo(UPDATED_RANK);
        assertThat(testBoardGame.getImage()).isEqualTo(UPDATED_IMAGE);

        // Validate the BoardGame in ElasticSearch
        BoardGame boardGameEs = boardGameSearchRepository.findOne(testBoardGame.getId());
        assertThat(boardGameEs).isEqualToComparingFieldByField(testBoardGame);
    }

    @Test
    @Transactional
    public void deleteBoardGame() throws Exception {
        // Initialize the database
        boardGameRepository.saveAndFlush(boardGame);
        boardGameSearchRepository.save(boardGame);
        int databaseSizeBeforeDelete = boardGameRepository.findAll().size();

        // Get the boardGame
        restBoardGameMockMvc.perform(delete("/api/board-games/{id}", boardGame.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean boardGameExistsInEs = boardGameSearchRepository.exists(boardGame.getId());
        assertThat(boardGameExistsInEs).isFalse();

        // Validate the database is empty
        List<BoardGame> boardGames = boardGameRepository.findAll();
        assertThat(boardGames).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBoardGame() throws Exception {
        // Initialize the database
        boardGameRepository.saveAndFlush(boardGame);
        boardGameSearchRepository.save(boardGame);

        // Search the boardGame
        restBoardGameMockMvc.perform(get("/api/_search/board-games?query=id:" + boardGame.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boardGame.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].yearPublished").value(hasItem(DEFAULT_YEAR_PUBLISHED)))
            .andExpect(jsonPath("$.[*].minPlayers").value(hasItem(DEFAULT_MIN_PLAYERS)))
            .andExpect(jsonPath("$.[*].maxPlayers").value(hasItem(DEFAULT_MAX_PLAYERS)))
            .andExpect(jsonPath("$.[*].playingTime").value(hasItem(DEFAULT_PLAYING_TIME)))
            .andExpect(jsonPath("$.[*].minAge").value(hasItem(DEFAULT_MIN_AGE)))
            .andExpect(jsonPath("$.[*].minPlaytime").value(hasItem(DEFAULT_MIN_PLAYTIME)))
            .andExpect(jsonPath("$.[*].maxPlaytime").value(hasItem(DEFAULT_MAX_PLAYTIME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].thumbnail").value(hasItem(DEFAULT_THUMBNAIL.toString())))
            .andExpect(jsonPath("$.[*].usersRated").value(hasItem(DEFAULT_USERS_RATED)))
            .andExpect(jsonPath("$.[*].avgRating").value(hasItem(DEFAULT_AVG_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].bayesAvgRating").value(hasItem(DEFAULT_BAYES_AVG_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].rank").value(hasItem(DEFAULT_RANK)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())));
    }
}
