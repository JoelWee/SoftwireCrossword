package com.softwire.crossword.accessingdatamysql;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<Character> getGrid() {
        var grid = new Grid(width, crosswordClues);
        return grid.getDisplayFormat();
    }

    public boolean isAddingClueValid(CrosswordClueMap clue) {
        var grid = new Grid(width, crosswordClues);
        return grid.isAddingClueValid(clue);
    }

    public Set<Integer> getClueIds() {
        return crosswordClues
                .stream()
                .map(clue -> clue.getClue().getId())
                .collect(Collectors.toSet());
    }
}