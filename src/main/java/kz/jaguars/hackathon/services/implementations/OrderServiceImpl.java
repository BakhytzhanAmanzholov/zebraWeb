package kz.jaguars.hackathon.services.implementations;

import kz.jaguars.hackathon.exceptions.NotFoundException;
import kz.jaguars.hackathon.models.CoffeeHouse;
import kz.jaguars.hackathon.models.Booking;
import kz.jaguars.hackathon.repositories.OrderRepository;
import kz.jaguars.hackathon.services.CoffeeService;
import kz.jaguars.hackathon.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final CoffeeService coffeeService;

    private final OrderRepository orderRepository;

    @Override
    public Booking save(Booking entity) {
        return orderRepository.save(entity);
    }

    @Override
    public void delete(Long aLong) {
        orderRepository.deleteById(aLong);
    }

    @Override
    public Booking update(Booking entity) {
        Booking order = findById(entity.getId());

        return order;
    }

    @Override
    public Booking findById(Long aLong) {
        return orderRepository.findById(aLong).orElseThrow(
                () -> new NotFoundException("Order <" + aLong + "> not found"));
    }

    @Override
    public List<Booking> findAllByCoffee(Long id) {
        CoffeeHouse coffeeHouse = coffeeService.findById(id);
        return orderRepository.findAllByCoffeeHouse(coffeeHouse);
    }

    @Override
    public Booking saveWithCoffee(Long id) {
        CoffeeHouse coffeeHouse = coffeeService.findById(id);
        Booking booking = new Booking();
        booking.setDate(Date.valueOf(LocalDate.now()));
        booking.setCoffeeHouse(coffeeHouse);
        return save(booking);
    }
}
