package com.jcl.example.controller;

import com.jcl.example.dto.ExampleRequest;
import com.jcl.example.dto.ExampleResponse;
import com.jcl.example.service.ExampleService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ExampleController is a REST controller that handles the example requests.
 * @author ayush agrahari created on 14/03/2021
 */
@RestController
@RequestMapping("/examples")
@RequiredArgsConstructor
public class ExampleController {

    private final ExampleService exampleService;

    @PostMapping
    public ResponseEntity<ExampleResponse> create(@RequestBody ExampleRequest request) {
        ExampleResponse created = exampleService.create(request);
        return ResponseEntity.created(URI.create("/examples/" + created.id())).body(created);
    }

    @GetMapping
    public List<ExampleResponse> getAll() {
        return exampleService.getAll();
    }

    @GetMapping("/{id}")
    public ExampleResponse getById(@PathVariable Long id) {
        return exampleService.getById(id);
    }

    @PutMapping("/{id}")
    public ExampleResponse update(@PathVariable Long id, @RequestBody ExampleRequest request) {
        return exampleService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        exampleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
