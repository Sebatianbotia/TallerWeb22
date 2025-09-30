package com.example.airline.DTO;

import java.io.Serializable;

public class PassengerProfileDTO {
    public record passengerProfileCreateRequest(String phoneNumber, String countryCode) implements Serializable {}
    public record passengerProfileUpdateRequest(String phoneNumber, String countryCode) implements Serializable {}
    public record passengerProfileResponse(Long passengerProfileID, String phoneNumber, String countryCode) implements Serializable {}

}
