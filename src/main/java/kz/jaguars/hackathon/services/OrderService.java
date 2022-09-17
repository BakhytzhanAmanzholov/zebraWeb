package kz.jaguars.hackathon.services;

import kz.jaguars.hackathon.models.Booking;
import kz.jaguars.hackathon.models.Product;

import java.util.List;

public interface OrderService extends CrudService<Booking, Long> {
    List<Booking> findAllByCoffee(Long id);

    Booking saveWithCoffee(Long id);

    void addProductsToOrder(Long orderId, Long productId, Integer count);

    void addProductToOrder(Product product, Long orderId);

    void removeProductsFromOrder(Long orderId, Long productId, Integer count);

    void addClientToOrder(Long id, Long clientId);

}
