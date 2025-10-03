package com.example.airline.Services;

import com.example.airline.DTO.BookingItemDTO.*;
import com.example.airline.Mappers.BookingItemMapper;
import com.example.airline.entities.Booking;
import com.example.airline.entities.BookingItem;
import com.example.airline.entities.Flight;
import com.example.airline.repositories.BookingItemsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public bookingItemReponse create(bookingItemCreateRequest request) {
        BookingItem bookingItem = bookingItemMapper.toEntity(request);
        Flight f = flightServiceImpl.getFlightObject(request.fligthId());
        Booking b = bookingServiceImpl.getObject(request.bookingId());

        // Asignamos el vuelo y el booking
        bookingItem.setBooking(b);
        bookingItem.setFlight(f);

        // Ahora a el vuelo y booking le asignamos el bookingItem

        b.getItems().add(bookingItem);
        // Me recomendo la IA que coloque que verifique si hay un puesto ahi
        f.getBookingItems().add(bookingItem);

        bookingItem = bookingItemsRepository.save(bookingItem);

        return bookingItemMapper.toDTO(bookingItem);
    }


    @Override
    @Transactional
    public bookingItemReponse update(BookingItem bookingItem,bookingItemUpdateRequest request) {
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


    @Override
    @Transactional
    public void delete(Long id) {
        bookingItemsRepository.deleteById(id);
    }


    public BookingItem findBookingItem(Long id) {
        return bookingItemsRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Booking Item not found"));
    }

    @Override
    public List<bookingItemReponse> findAll() {
        return bookingItemsRepository.findAll().stream().map(bookingItemMapper::toDTO).toList();
    }
}
