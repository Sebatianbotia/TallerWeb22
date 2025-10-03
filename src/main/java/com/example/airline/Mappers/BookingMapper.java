package com.example.airline.Mappers;

import com.example.airline.DTO.BookingDTO;
import com.example.airline.DTO.BookingItemDTO;
import com.example.airline.entities.Booking;
import com.example.airline.entities.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = "spring", uses = {PassengerMapper.class, BookingItemMapper.class})
public interface BookingMapper {

    @Mapping(target = "passenger", ignore = true)
    @Mapping(target = "items", ignore = true)
    Booking toEntity(BookingDTO.bookingCreateRequest request);

    @Mapping(target = "bookingItems", source = "items")
    BookingDTO.bookingResponse toDTO(Booking booking);



}