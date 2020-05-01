package com.softwire.crossword.accessingdatamysql;

import javax.persistence.*;
import java.util.List;

@Entity
public class Crossword {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String name;

    @OneToMany(mappedBy="crossword")
    private List<CrosswordClueMap> crosswordClues;

    private Integer width;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public List<CrosswordClueMap> getCrosswordClues() {
        return crosswordClues;
    }

    public void setCrosswordClues(List<CrosswordClueMap> crosswordClues) {
        this.crosswordClues = crosswordClues;
    }
}