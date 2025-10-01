package com.example.airline.Services;

import com.example.airline.DTO.BookingItemDTO.*;
import com.example.airline.Services.Mappers.BookingItemMapper;
import com.example.airline.entities.Booking;
import com.example.airline.entities.BookingItem;
import com.example.airline.entities.Flight;
import com.example.airline.repositories.BookingItemsRepository;
import com.example.airline.repositories.BookingRepository;
import com.example.airline.repositories.FlightRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public class BookingItemServiceImpl implements BookingItemService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final BookingItemsRepository bookingItemsRepository;
    private final BookingItemMapper bookingItemMapper;

    public BookingItemServiceImpl(BookingRepository bookingRepository, FlightRepository flightRepository, BookingItemsRepository bookingItemsRepository, BookingItemMapper bookingItemMapper) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.bookingItemsRepository = bookingItemsRepository;
        this.bookingItemMapper = bookingItemMapper;
    }

    @Override
    @Transactional
    public bookingItemReponse create(bookingItemCreateRequest request) {
        Booking booking = findBooking(request.bookingId());
        Flight flight = findFlight(request.fligthId());

        BookingItem bookingItem = BookingItem.builder()
                .price(request.price())
                .segmentOrder(request.segmentOrder())
                .cabin(request.cabin())
                .booking(booking)
                .flight(flight)
                .build();

        booking.addItem(bookingItem);

        BookingItem savedItem = bookingItemsRepository.save(bookingItem);

        return bookingItemMapper.toDTO(savedItem);
    }


    @Override
    @Transactional
    public bookingItemReponse update(bookingItemUpdateRequest request) {
        BookingItem bookingItem = findBookingItem(request.bookingItemsId());

        bookingItemMapper.updateEntity(request, bookingItem);

        if (request.flightId() != null && !request.flightId().equals(bookingItem.getFlight().getId())) {
            Flight newFlight = findFlight(request.flightId());
            bookingItem.setFlight(newFlight);
        }

        BookingItem savedItem = bookingItemsRepository.save(bookingItem);

        return bookingItemMapper.toDTO(savedItem);
    }


    @Override
    @Transactional
    public void delete(long id) {
        bookingItemsRepository.deleteById(id);
    }



    public Booking findBooking(Long id) {
        return bookingRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Booking not found"));
    }
    public Flight findFlight(Long flightId) {
        return flightRepository.findById(flightId).orElseThrow(()-> new EntityNotFoundException("Flight not found"));
    }
    public BookingItem findBookingItem(Long id) {
        return bookingItemsRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Booking Item not found"));
    }
}
