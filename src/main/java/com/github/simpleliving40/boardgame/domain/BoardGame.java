package com.github.simpleliving40.boardgame.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A BoardGame.
 */
@Entity
@Table(name = "board_game")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "boardgame")
public class BoardGame implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "year_published")
    private Integer yearPublished;

    @Column(name = "min_players")
    private Integer minPlayers;

    @Column(name = "max_players")
    private Integer maxPlayers;

    @Column(name = "playing_time")
    private Integer playingTime;

    @Column(name = "min_age")
    private Integer minAge;

    @Column(name = "min_playtime")
    private Integer minPlaytime;

    @Column(name = "max_playtime")
    private Integer maxPlaytime;

    @Column(name = "description")
    private String description;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "users_rated")
    private Integer usersRated;

    @Column(name = "avg_rating")
    private Double avgRating;

    @Column(name = "bayes_avg_rating")
    private Double bayesAvgRating;

    @Column(name = "rank")
    private Integer rank;

    @Column(name = "image")
    private String image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(Integer yearPublished) {
        this.yearPublished = yearPublished;
    }

    public Integer getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(Integer minPlayers) {
        this.minPlayers = minPlayers;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(Integer maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public Integer getPlayingTime() {
        return playingTime;
    }

    public void setPlayingTime(Integer playingTime) {
        this.playingTime = playingTime;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMinPlaytime() {
        return minPlaytime;
    }

    public void setMinPlaytime(Integer minPlaytime) {
        this.minPlaytime = minPlaytime;
    }

    public Integer getMaxPlaytime() {
        return maxPlaytime;
    }

    public void setMaxPlaytime(Integer maxPlaytime) {
        this.maxPlaytime = maxPlaytime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getUsersRated() {
        return usersRated;
    }

    public void setUsersRated(Integer usersRated) {
        this.usersRated = usersRated;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public Double getBayesAvgRating() {
        return bayesAvgRating;
    }

    public void setBayesAvgRating(Double bayesAvgRating) {
        this.bayesAvgRating = bayesAvgRating;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BoardGame boardGame = (BoardGame) o;
        if(boardGame.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, boardGame.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BoardGame{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", yearPublished='" + yearPublished + "'" +
            ", minPlayers='" + minPlayers + "'" +
            ", maxPlayers='" + maxPlayers + "'" +
            ", playingTime='" + playingTime + "'" +
            ", minAge='" + minAge + "'" +
            ", minPlaytime='" + minPlaytime + "'" +
            ", maxPlaytime='" + maxPlaytime + "'" +
            ", description='" + description + "'" +
            ", thumbnail='" + thumbnail + "'" +
            ", usersRated='" + usersRated + "'" +
            ", avgRating='" + avgRating + "'" +
            ", bayesAvgRating='" + bayesAvgRating + "'" +
            ", rank='" + rank + "'" +
            ", image='" + image + "'" +
            '}';
    }
}
