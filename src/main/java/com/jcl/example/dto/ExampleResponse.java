package com.jcl.example.dto;

import com.jcl.example.entities.Example;
import java.time.Instant;

public record ExampleResponse(Long id, String name, Instant createdAt, Instant updatedAt) {

    public static ExampleResponse from(Example example) {
        return new ExampleResponse(
                example.getId(),
                example.getName(),
                example.getCreatedAt(),
                example.getUpdatedAt());
    }
}
