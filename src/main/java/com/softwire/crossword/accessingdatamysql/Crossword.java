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

    private Integer width;

}