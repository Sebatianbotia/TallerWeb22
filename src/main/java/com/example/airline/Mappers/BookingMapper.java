package com.example.airline.Mappers;

import com.example.airline.DTO.BookingDTO;
import com.example.airline.entities.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.airline.entities.Booking;

@Mapper(
        componentModel = "spring",
        uses = {
                com.example.airline.Mappers.PassengerMapper.class,
                com.example.airline.Mappers.BookingItemMapper.class
        }
)
public interface BookingMapper {

    Booking toEntity(BookingDTO.bookingCreateRequest request, Passenger passenger);
    @Mapping(source = "items", target = "bookingItems")
    @Mapping(source = "passenger", target = "passenger")
    BookingDTO.bookingResponse toDTO(Booking booking);

}
