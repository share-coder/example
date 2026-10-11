package com.jcl.example.service;

import com.jcl.example.dto.ExampleRequest;
import com.jcl.example.dto.ExampleResponse;
import com.jcl.example.entities.Example;
import com.jcl.example.repository.ExampleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ExampleService {

    private final ExampleRepository exampleRepository;

    @Transactional
    public ExampleResponse create(ExampleRequest request) {
        Example example = new Example();
        example.setName(requireName(request));
        return ExampleResponse.from(exampleRepository.saveAndFlush(example));
    }

    @Transactional(readOnly = true)
    public ExampleResponse getById(Long id) {
        return ExampleResponse.from(findOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<ExampleResponse> getAll() {
        return exampleRepository.findAll().stream().map(ExampleResponse::from).toList();
    }

    @Transactional
    public ExampleResponse update(Long id, ExampleRequest request) {
        Example example = findOrThrow(id);
        example.setName(requireName(request));
        return ExampleResponse.from(exampleRepository.saveAndFlush(example));
    }

    @Transactional
    public void delete(Long id) {
        Example example = findOrThrow(id);
        exampleRepository.delete(example);
    }

    private Example findOrThrow(Long id) {
        return exampleRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Example not found: " + id));
    }

    private String requireName(ExampleRequest request) {
        if (request == null || StringUtils.isBlank(request.name())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "name is required");
        }
        return request.name().trim();
    }
}
