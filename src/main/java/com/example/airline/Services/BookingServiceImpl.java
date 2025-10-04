package com.example.airline.Services;

import com.example.airline.DTO.BookingDTO;
import com.example.airline.Mappers.BookingMapper; // Usamos el mapper
import com.example.airline.entities.Booking;
import com.example.airline.entities.Passenger;
import com.example.airline.repositories.BookingRepository;
import com.example.airline.repositories.PassengerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor; // Usamos Lombok para inyección de dependencias
import org.springframework.stereotype.Service; // Indicamos que es un servicio
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

    private final PassengerServiceimpl passengerService;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    @Override
    public BookingDTO.bookingResponse create(BookingDTO.bookingCreateRequest request) {
        Passenger p = passengerService.get(request.passengerId());

        Booking b = bookingMapper.toEntity(request);
        b.setPassenger(p); // Asignamos la relación Passenger
        b.setCreatedAt(OffsetDateTime.now());
        p.getBookings().add(b);

        b = bookingRepository.save(b);

        return bookingMapper.toDTO(b);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingDTO.bookingResponse get(Long id) {
        // Usamos la instancia inyectada para el mapeo
        return bookingMapper.toDTO(getObject(id));
    }

    @Override
    public void delete(Long id) {
        bookingRepository.deleteById(id);
    }

    public Booking getObject(Long id){
        return bookingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Booking not found"));
    }

}