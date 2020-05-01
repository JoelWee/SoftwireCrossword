package com.softwire.crossword.accessingdatamysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
        var crosswordClues = crossword.getCrosswordClues().stream().toArray();
        var allClues = clueRepository.findAll();

        model.addAttribute("crossword", crossword);
        model.addAttribute("crosswordClues", crosswordClues);
        model.addAttribute("allClues", allClues);

        return "crossword";
    }

    @ModelAttribute("crosswords")
    public Iterable<Crossword> getAllCrosswords() {
        return crosswordRepository.findAll();
    }
}