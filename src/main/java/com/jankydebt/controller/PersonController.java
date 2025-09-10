package com.jankydebt.controller;

import com.jankydebt.domain.Person;
import com.jankydebt.dto.CreatePersonRequest;
import com.jankydebt.repo.PersonRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/people")
public class PersonController {
    private final PersonRepository people;

    public PersonController(PersonRepository people) {
        this.people = people;
    }

    @GetMapping
    public List<Person> all() { return people.findAll(); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person create(@Valid @RequestBody CreatePersonRequest req) {
        // not bothering with fancy DTO mapping today
        Person p = new Person();
        p.setName(req.getName());
        p.setEmail(req.getEmail());
        return people.save(p);
    }
}
