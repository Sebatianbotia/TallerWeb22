package com.example.airline.DTO;

import java.io.Serializable;
import java.util.Set;

public class TagDTO {
    public record tagCreateRequest(String name) implements Serializable {}
    public record tagUpdateRequest(String name) implements Serializable {}
    public record tagResponse(Long tagId, String name) implements Serializable {}


}
