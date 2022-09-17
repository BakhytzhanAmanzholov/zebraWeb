package kz.jaguars.hackathon.services;

import kz.jaguars.hackathon.models.Booking;

import java.util.List;

public interface OrderService extends CrudService<Booking, Long> {
    List<Booking> findAllByCoffee(Long id);

    Booking saveWithCoffee(Long id);
}
