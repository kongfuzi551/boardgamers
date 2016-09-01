package com.github.simpleliving40.boardgame.service;

import com.github.simpleliving40.boardgame.domain.BoardGame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * This class is used to import from XML file (downloaded from BGG) to the BoardGame database.
 */
@Service
public class BoardGameImportService {
    private static final Logger log = LoggerFactory.getLogger(BoardGameImportService.class);

    BggParser parser;

    public BoardGameImportService(){
        parser = new BggParser();
    }

    public BoardGame parse(String filename) throws IOException, SAXException {
        return parser.parse(filename);
    }
}
