package com.example.airline.services;

import com.example.airline.API.Error.NotFoundException;
import com.example.airline.DTO.BookingItemDTO;
import com.example.airline.DTO.BookingItemDTO.*;
import com.example.airline.Mappers.BookingItemMapper;
import com.example.airline.entities.Booking;
import com.example.airline.entities.BookingItem;
import com.example.airline.entities.Flight;
import com.example.airline.repositories.BookingItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookingItemServiceImpl implements BookingItemService {

    private final BookingItemsRepository bookingItemsRepository;
    private final BookingItemMapper bookingItemMapper;
    private final BookingServiceImpl bookingServiceImpl;
    private final FlightServiceImpl flightServiceImpl;

    @Override
    public bookingItemReponse create(bookingItemCreateRequest request) {
        BookingItem bookingItem = bookingItemMapper.toEntity(request);
        Flight f = flightServiceImpl.getFlightObject(request.fligthId());
        Booking b = bookingServiceImpl.getObject(request.bookingId());


        bookingItem.setBooking(b);
        bookingItem.setFlight(f);
        if (b.getItems() == null) {
            b.setItems(new ArrayList<>());
        }
        b.getItems().add(bookingItem);

        if (f.getBookingItems() == null) {
            f.setBookingItems(new ArrayList<>());
        }
        f.getBookingItems().add(bookingItem);

        bookingItem = bookingItemsRepository.save(bookingItem);

        return bookingItemMapper.toDTO(bookingItem);
    }


    @Override
    @Transactional
    public bookingItemReponse update(Long id, bookingItemUpdateRequest request) {
        BookingItem bookingItem = findBookingItem(id);
        bookingItemMapper.updateEntity(request, bookingItem);
        if (bookingItem.getFlight().getId() != request.flightId()){
            Flight f =  flightServiceImpl.getFlightObject(request.flightId());
            bookingItem.getFlight().getBookingItems().remove(bookingItem);
            bookingItem.setFlight(f);

        }
        if (bookingItem.getBooking().getId() != request.bookingId()){
            Booking b =  bookingServiceImpl.getObject(request.bookingId());
            bookingItem.getBooking().getItems().remove(bookingItem);
            bookingItem.setBooking(b);
        }
        BookingItem savedItem = bookingItemsRepository.save(bookingItem);


        return bookingItemMapper.toDTO(savedItem);
    }

    public BookingItemDTO.bookingItemReponse get(Long bookingId) {
        return bookingItemMapper.toDTO(bookingItemsRepository.getReferenceById(bookingId));
    }




    @Override
    @Transactional
    public void delete(Long id) {
        bookingItemsRepository.deleteById(id);
    }


    public BookingItem findBookingItem(Long id) {
        return bookingItemsRepository.findById(id).orElseThrow(()-> new NotFoundException("Booking Item not found"));
    }

    @Override
    public Page<bookingItemReponse> list(Pageable pageable) {
        return bookingItemsRepository.findAll(pageable).map(bookingItemMapper::toDTO);
    }

}
