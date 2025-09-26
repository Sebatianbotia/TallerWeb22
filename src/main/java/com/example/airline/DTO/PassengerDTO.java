package com.example.airline.DTO;


import java.io.Serializable;

public class PassengerDTO {
    public record passengerCreateRequest(String fullName, String email, passengerProfileDto passengerProfile) implements Serializable {}
    public record passengerProfileDto(String phone, String countryCode) implements Serializable {}
    public record passengerUpdateRequest(String fullName, String email, passengerProfileDto passengerProfile) implements Serializable {}
    public record passengerResponseBasic(String fullName, String email) implements Serializable {}
    public record passengerResponseProfileDetails(String fullName, String email, passengerProfileDto passengerProfile) implements Serializable {}

}
