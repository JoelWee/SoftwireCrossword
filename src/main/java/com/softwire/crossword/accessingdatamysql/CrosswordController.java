package com.softwire.crossword.accessingdatamysql;

import org.hibernate.engine.jdbc.CharacterStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
public class CrosswordController {
    @Autowired
    private CrosswordRepository crosswordRepository;
    @Autowired
    private ClueRepository clueRepository;
    @Autowired
    private CrosswordClueMapRepository crosswordClueMapRepository;

    @GetMapping(path = "/crosswords")
    public String crosswords(Model model){
        var crosswords = crosswordRepository.findAll();
        model.addAttribute("crosswords", crosswords);
        return "crosswords";
    }

    @GetMapping(path = "/crosswords/{id}")
    public String crossword(Model model, @PathVariable int id) {
        Crossword crossword = crosswordRepository.findById(id).get();
        var crosswordClues = crossword.getCrosswordClues();
        var allClues = clueRepository.findAll();

        model.addAttribute("crossword", crossword);
        model.addAttribute("crosswordClues", crosswordClues);
        model.addAttribute("allClues", allClues);

        String cellRatio = String.format ("%.4f", 100.0 / crossword.getWidth()) + "%";
        var gridStyle = "grid-template-columns: " +
                String.join(" ",
                        IntStream.range(0, crossword.getWidth())
                                .mapToObj(i -> cellRatio)
                                .toArray(String[]::new)
                ) + "; grid-template-rows: " +
                String.join(" ",
                        IntStream.range(0, crossword.getWidth())
                                .mapToObj(i -> cellRatio)
                                .toArray(String[]::new)
                );

        model.addAttribute("gridStyle", gridStyle);
        model.addAttribute("grid", getGrid(crosswordClues, crossword.getWidth()));
        return "crossword";
    }

    private List<Character> getGrid(Iterable<CrosswordClueMap> crosswordClues, int width) {
        var grid = new Character[width][width];
        for(var crosswordClue : crosswordClues) {
            placeClue(crosswordClue, grid, width);
        }
        var gridList = new ArrayList<Character>();
        for(var row: grid) {
            gridList.addAll(Arrays.asList(row));
        }
        return gridList;
    }

    // TODO: Use boolean return value to validate? Return false means invalid
    private boolean placeClue(CrosswordClueMap crosswordClue, Character[][] grid, int width) {
        var clue = crosswordClue.getClue().getClue();
        int row = crosswordClue.getStartRow();
        int col = crosswordClue.getStartCol();
        if (crosswordClue.getIsHorizontal()) {
            for (int i = 0; i < clue.length(); i++){
                col = col + i;
                if (col >= width || grid[row][col] != null) {
                    // TODO: undo when return false?
                    return false;
                }
                grid[row][col] = clue.charAt(i);
            }
        } else {
            for (int i = 0; i < clue.length(); i++){
                row = row + i;
                if (row >= width || grid[row][col] != null) {
                    return false;
                }
                grid[row][col] = clue.charAt(i);
            }
        }
        return true;
    }

    @ModelAttribute("crosswords")
    public Iterable<Crossword> getAllCrosswords() {
        return crosswordRepository.findAll();
    }
}