package com.github.simpleliving40.boardgame.service;

import com.github.simpleliving40.boardgame.BoardGamesApp;
import com.github.simpleliving40.boardgame.domain.BoardGame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.inject.Inject;

/**
 * Test BoardGameImportTest
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BoardGamesApp.class)
public class BoardGameImportServiceTest {

    @Inject
    BoardGameImportService bgi;

    @Test
    public void testParse() throws IOException, SAXException {

        BoardGame boardGame = bgi.parse("1.xml");

        assert(boardGame != null);
//        assert(boardGame.getName().equals("Die Macher"));
//        assert(boardGame.getYearPublished().equals(1986));
//        assert(boardGame.getMinPlayers().equals(3));
//        assert(boardGame.getMaxPlayers().equals(5));
//        assert(boardGame.getUsersRated().equals(4373));
//        assert(boardGame.getAvgRating().equals(7.6779));
    }
}
