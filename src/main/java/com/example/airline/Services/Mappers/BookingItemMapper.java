package com.example.airline.Services.Mappers;

import com.example.airline.DTO.BookingItemDTO;
import com.example.airline.entities.Booking;
import com.example.airline.entities.BookingItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface BookingItemMapper {
        @Mapping(target = "bookingId", source = "booking", qualifiedByName = "mapBookingToId")
        BookingItemDTO.bookingItemReponse toDTO(BookingItem entity);

        @Named("mapBookingToId")
        default Long mapBookingToId(Booking booking) {
            if (booking == null) {
                return null;
            }
            return booking.getId();
        }

        @Mapping(target = "booking", ignore = true)
        @Mapping(target = "flight", ignore = true)
        BookingItem toEntity(BookingItemDTO.bookingItemCreateRequest dto);

        @Mapping(target = "booking", ignore = true)
        @Mapping(target = "flight", ignore = true)
        @Mapping(target = "id", source = "bookingItemsId")
        void updateEntity(BookingItemDTO.bookingItemUpdateRequest dto, @MappingTarget BookingItem entity);
    }

