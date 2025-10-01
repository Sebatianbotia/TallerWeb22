package com.example.airline.Mappers;

import com.example.airline.DTO.BookingDTO;
import com.example.airline.entities.Booking;
import com.example.airline.entities.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {com.example.airline.Services.Mappers.PassengerMapper.class} // Asume que tienes un PassengerMapper para la relación
)
public interface BookingMapper {

    @Mapping(target = "bookingItems", source = "items")
    @Mapping(target = "passenger", source = "passenger")
    BookingDTO.bookingResponse toDTO(Booking booking);
    @Mapping(target = "passenger", ignore = true) // Ignoramos la relación, el servicio la asignará
    Booking toEntity(BookingDTO.bookingCreateRequest request);
}