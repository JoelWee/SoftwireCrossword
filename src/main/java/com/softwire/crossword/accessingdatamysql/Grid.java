package com.softwire.crossword.accessingdatamysql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Grid {
    private int width;
    Character[][] grid;
    int numClues;

    public Grid(int width, List<CrosswordClueMap> crosswordClues) {
        this.width = width;
        grid = new Character[width][width];
        for (var crosswordClue : crosswordClues) {
            placeAnswer(crosswordClue);
        }
        numClues = crosswordClues.size();
    }

    public List<Character> getDisplayFormat() {
        return toOneDimensionalList();
    }

    private List<Character> toOneDimensionalList() {
        var gridList = new ArrayList<Character>();
        for (var row : grid) {
            gridList.addAll(Arrays.asList(row));
        }
        return gridList;
    }

    public void placeAnswer(CrosswordClueMap crosswordClue) {
        var answer = crosswordClue.getClue().getAnswer().toUpperCase();
        int row = crosswordClue.getStartRow() - 1;
        int col = crosswordClue.getStartCol() - 1;

        for (int i = 0; i < answer.length(); i++) {
            grid[row][col] = answer.charAt(i);
            if (crosswordClue.getIsHorizontal()) {
                col++;
            } else {
                row++;
            }
        }
    }

    public boolean canPlaceAnswer(CrosswordClueMap crosswordClue) {
        var clue = crosswordClue.getClue().getClue().toUpperCase();
        int row = crosswordClue.getStartRow() - 1;
        int col = crosswordClue.getStartCol() - 1;

        boolean isConnected = false;

        if (row < 0 || col < 0) {
            return false;
        }

        for (int i = 0; i < clue.length(); i++) {
            char charToPlace = clue.charAt(i);
            if (!isGridPositionValid(row, col, grid, charToPlace)) {
                return false;
            }

            isConnected = isConnected || (grid[row][col] != null && grid[row][col] == charToPlace);
            if (crosswordClue.getIsHorizontal()) {
                col++;
            } else {
                row++;
            }
        }

        // Can place clue if it's connected or it's the first clue
        return isConnected || numClues == 0;
    }

    private boolean isGridPositionValid(int row, int col, Character[][] grid, char charToPlace) {
        return col < width && row < width && (grid[row][col] == null || grid[row][col] == charToPlace);
    }

    public boolean isAddingClueValid(CrosswordClueMap clue) {
        if (canPlaceAnswer(clue)) {
            placeAnswer(clue);
            return !checkHas2x2Square();
        }
        return false;
    }

    private boolean checkHas2x2Square() {
        for (int i = 0; i < width - 1; i++) {
            for (int j = 0; j < width - 1; j++) {
                if (checkHas2x2SquareAt(i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkHas2x2SquareAt(int row, int col) {
        return grid[row][col] != null &&
                grid[row + 1][col] != null &&
                grid[row][col + 1] != null &&
                grid[row + 1][col + 1] != null;
    }
}