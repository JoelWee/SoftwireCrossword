package com.softwire.crossword.accessingdatamysql;

import org.springframework.data.repository.CrudRepository;

import com.softwire.crossword.accessingdatamysql.Clue;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ClueRepository extends CrudRepository<Clue, Integer> {

}