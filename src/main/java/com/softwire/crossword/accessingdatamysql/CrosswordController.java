package com.softwire.crossword.accessingdatamysql;

import org.hibernate.engine.jdbc.CharacterStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Controller
public class CrosswordController {
    @Autowired
    private CrosswordRepository crosswordRepository;
    @Autowired
    private ClueRepository clueRepository;
    @Autowired
    private CrosswordClueMapRepository crosswordClueMapRepository;

    @GetMapping(path = "/crosswords")
    public String crosswords(Model model) {
        var crosswords = crosswordRepository.findAll();
        model.addAttribute("crosswords", crosswords);
        return "crosswords";
    }

    @GetMapping(path = "/crosswords/{crosswordId}")
    public String crossword(Model model,
                            @PathVariable int crosswordId,
                            @RequestParam(value = "answers", required = false, defaultValue = "true") boolean showAnswers) {
        Crossword crossword = crosswordRepository.findById(crosswordId).get();
        populateCrosswordModel(model, crossword, showAnswers);
        return "crossword";
    }

    @PostMapping(path = "/crosswords/{crosswordId}")
    public String addClueToCrossword(@PathVariable int crosswordId,
                                     @RequestParam int clueId,
                                     @RequestParam int row,
                                     @RequestParam int col,
                                     @RequestParam(required = false) String isHorizontal,
                                     Model model) {
        var crossword = crosswordRepository.findById(crosswordId).get();
        var clue = clueRepository.findById(clueId).get();
        var crosswordClueMap = new CrosswordClueMap();

        crosswordClueMap.setCrossword(crossword);
        crosswordClueMap.setClue(clue);
        crosswordClueMap.setStartCol(col);
        crosswordClueMap.setStartRow(row);
        crosswordClueMap.setIsHorizontal("isHorizontal".equals(isHorizontal));

        if (crossword.isAddingClueValid(crosswordClueMap)
                || !crossword.getClueIds().contains(clueId)) {
            crosswordClueMapRepository.save(crosswordClueMap);
        } else {
            model.addAttribute("error", "Your clue is invalid!");
        }

        populateCrosswordModel(model, crossword, true);
        return "crossword";
    }

    private void populateCrosswordModel(Model model, Crossword crossword, boolean showAnswers) {
        var crosswordClues = crossword.getCrosswordClues();
        var availableClues = clueRepository.findAll();
        Set<Integer> usedClueIds = crossword.getClueIds();

        availableClues = StreamSupport
                .stream(availableClues.spliterator(), false)
                .filter(clue -> !usedClueIds.contains(clue.getId()))
                .collect(Collectors.toList());

        model.addAttribute("crossword", crossword);
        model.addAttribute("crosswordClues", crosswordClues);

        model.addAttribute("availableClues", availableClues);

        String cellSize = "25px"; // String.format ("%.4f", 100.0 / crossword.getWidth()) + "%";
        var gridStyle = "grid-template-columns: " +
                String.join(" ",
                        IntStream.range(0, crossword.getWidth())
                                .mapToObj(i -> cellSize)
                                .toArray(String[]::new)
                ) + "; grid-template-rows: " +
                String.join(" ",
                        IntStream.range(0, crossword.getWidth())
                                .mapToObj(i -> cellSize)
                                .toArray(String[]::new)
                );

        model.addAttribute("gridStyle", gridStyle);
        var grid = crossword.getGrid();
        if (!showAnswers) {
            grid = grid.stream().map(c -> c == null ? null : ' ').collect(Collectors.toList());
        }
        model.addAttribute("grid", grid);

    }

    @ModelAttribute("crosswords")
    public Iterable<Crossword> getAllCrosswords() {
        return crosswordRepository.findAll();
    }
}