package com.softwire.crossword.accessingdatamysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "")
public class CrosswordController {
    @Autowired
    private CrosswordRepository crosswordRepository;

    @GetMapping(path = "/")
    public String view() {
        return "crosswords";
    }

    @ModelAttribute("crosswords")
    public Iterable<Crossword> getAllCrosswords() {
        return crosswordRepository.findAll();
    }
}