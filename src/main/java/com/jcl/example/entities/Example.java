package com.jcl.example.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "example")
public class Example {

    @Id
    private int id;
    private String name;
}
