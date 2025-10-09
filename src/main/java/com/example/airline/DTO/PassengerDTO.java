package com.example.airline.DTO;


import java.io.Serializable;

public class PassengerDTO {
    public record passengerCreateRequest(String fullName, String email, PassengerProfileDTO.passengerProfileCreateRequest passengerProfile) implements Serializable {}
    public record passengerUpdateRequest( String fullName, String email, PassengerProfileDTO.passengerProfileUpdateRequest passengerProfile) implements Serializable {}
    public record passengerResponse(Long id ,String fullName, String email, PassengerProfileDTO.passengerProfileView passengerProfile) implements Serializable {}

}
