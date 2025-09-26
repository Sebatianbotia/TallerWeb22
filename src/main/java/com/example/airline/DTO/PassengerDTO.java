package com.example.airline.DTO;


import java.io.Serializable;

public class PassengerDTO {
    public record passengerCreateRequest(String fullName, String email) implements Serializable {}
    public record passengerUpdateRequest(String fullName, String email) implements Serializable {}
    public record passengerResponse(String fullName, String email) implements Serializable {}

}
