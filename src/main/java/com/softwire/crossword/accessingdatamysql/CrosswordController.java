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

    @GetMapping(path = "/crosswords")
    public String crosswords(Model model){
        var crosswords = crosswordRepository.findAll();
        model.addAttribute("crosswords", crosswords);
        return "crosswords";
    }

    @ModelAttribute("crosswords")
    public Iterable<Crossword> getAllCrosswords() {
        return crosswordRepository.findAll();
    }
}