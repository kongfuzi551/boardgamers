package com.github.simpleliving40.boardgame.service;

import com.github.simpleliving40.boardgame.domain.BoardGame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * This class is used to import from XML file (downloaded from BGG) to the BoardGame database.
 */
public class BggParser extends DefaultHandler {
    private static final Logger log = LoggerFactory.getLogger(BggParser.class);

    public BoardGame parse(String filename) throws IOException, SAXException {
        BoardGame boardGame = new BoardGame();
        try {

            File fXmlFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("item");

            if (nList.getLength() > 0) {
                Node nNode = nList.item(0);

                // Read direct children
                NodeList children = nNode.getChildNodes();
                for (int i = 0; i < children.getLength(); i++) {
                    Node child = children.item(i);

                    if (child.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) child;
                        switch (eElement.getNodeName()) {
                            case "name":
                                if (eElement.getAttribute("type").equals("primary"))
                                    boardGame.setName(eElement.getAttribute("value"));
                                break;
                            case "yearpublished":
                                boardGame.setYearPublished(Integer.parseInt(eElement.getAttribute("value")));
                                break;
                            case "minplayers":
                                boardGame.setMinPlayers(Integer.parseInt(eElement.getAttribute("value")));
                                break;
                            case "maxplayers":
                                boardGame.setMaxPlayers(Integer.parseInt(eElement.getAttribute("value")));
                                break;
                            case "playingtime":
                                boardGame.setPlayingTime(Integer.parseInt(eElement.getAttribute("value")));
                                break;
                            case "minplaytime":
                                boardGame.setMinPlaytime(Integer.parseInt(eElement.getAttribute("value")));
                                break;
                            case "maxplaytime":
                                boardGame.setMaxPlaytime(Integer.parseInt(eElement.getAttribute("value")));
                                break;
                            case "minage":
                                boardGame.setMinAge(Integer.parseInt(eElement.getAttribute("value")));
                                break;
                            case "description":
                                // TODO: Problem with VARCHAR(255). Fix it in validation rules (I don't know where they are)
                                String description = eElement.getTextContent();
                                if (description.length() >= 255)
                                    description = description.substring(0,255);
                                boardGame.setDescription(description);
                                break;
                            case "thumbnail":
                                boardGame.setThumbnail("http:" + eElement.getTextContent());
                                break;
                            case "image":
                                boardGame.setImage("http:" + eElement.getTextContent());
                                break;
                        }

                    }
                }

                // Parse ratings
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList ratings = ((Element) nNode).getElementsByTagName("ratings");
                    if (ratings.getLength() > 0) {
                        NodeList ratingsChildren = ratings.item(0).getChildNodes();
                        for (int i = 0; i < ratingsChildren.getLength(); i++) {
                            Node child = ratingsChildren.item(i);

                            if (child.getNodeType() == Node.ELEMENT_NODE) {
                                Element eElement = (Element) child;
                                switch (eElement.getNodeName()) {
                                    case "usersrated":
                                        boardGame.setUsersRated(Integer.parseInt(eElement.getAttribute("value")));
                                        break;
                                    // Fixme
                                    case "rank":
                                        if (eElement.getAttribute("name").equals("boardgame"))
                                            boardGame.setRank(Integer.parseInt(eElement.getAttribute("value")));
                                        break;
                                    case "average":
                                        boardGame.setAvgRating(Double.parseDouble(eElement.getAttribute("value")));
                                        break;
                                    case "bayesaverage":
                                        boardGame.setBayesAvgRating(Double.parseDouble(eElement.getAttribute("value")));
                                        break;
                                }
                            }
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return boardGame;
    }
}
