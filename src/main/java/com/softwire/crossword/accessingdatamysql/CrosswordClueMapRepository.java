package com.softwire.crossword.accessingdatamysql;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CrosswordClueMap {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private Integer crosswordId;
    private Integer clueId;
    private Integer startRow;
    private Integer startCol;
    private Boolean isHorizontal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCrosswordId() {
        return crosswordId;
    }

    public void setCrosswordId(Integer crosswordId) {
        this.crosswordId = crosswordId;
    }

    public Integer getClueId() {
        return clueId;
    }

    public void setClueId(Integer clueId) {
        this.clueId = clueId;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public Integer getStartCol() {
        return startCol;
    }

    public void setStartCol(Integer startCol) {
        this.startCol = startCol;
    }

    public Boolean getHorizontal() {
        return isHorizontal;
    }

    public void setHorizontal(Boolean horizontal) {
        isHorizontal = horizontal;
    }
}