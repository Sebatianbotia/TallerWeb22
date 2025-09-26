package com.example.airline.DTO;

import java.io.Serializable;
import java.util.Set;

public class TagDTO {
    public record tagCreateRequest(String name, Set<Long> flightIds) implements Serializable {}
    public record tagUpdateRequest(Long tagId, String name) implements Serializable {}
    public record tagResponse(Long tagId, String name) implements Serializable {}


}
