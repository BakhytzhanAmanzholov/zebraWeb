package kz.jaguars.hackathon.services.implementations;

import kz.jaguars.hackathon.exceptions.NotFoundException;
import kz.jaguars.hackathon.models.*;
import kz.jaguars.hackathon.repositories.OrderRepository;
import kz.jaguars.hackathon.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final CoffeeService coffeeService;

    private final OrderRepository orderRepository;

    private final StaffService staffService;

    private final ProductService productService;

    private final AccountService accountService;

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
        booking.setStaff(staffService.findByEmail(staffService.isLogged()));
        booking.setProducts(new ArrayList<>());
        booking.setPrice(0);
        booking.setFinalPrice(0);
        booking.setCompleted(false);
        return save(booking);
    }

    @Override
    public void addProductsToOrder(Long orderId, Long productId, Integer count) {
        Product product = productService.findById(productId);
        for (int i = 0; i < count; i++) {
            addProductToOrder(product, orderId);
        }
    }

    @Override
    public void addProductToOrder(Product product, Long orderId) {
        Booking order = findById(orderId);
        order.getProducts().add(product);

        order.setPrice(product.getSalePrice() + order.getPrice());

        if (order.getAccount() != null) {
            order.setFinalPrice(order.getPrice() - order.getPrice() / 100 * order.getAccount().getDiscount());
            log.info(order.getFinalPrice().toString());
        } else {
            order.setFinalPrice(order.getPrice());
        }

    }

    @Override
    public void removeProductsFromOrder(Long orderId, Long productId, Integer count) {
        Booking order = findById(orderId);
        Product removedProduct = productService.findById(productId);
        int size = 0;
        for (Product product : order.getProducts()) {
            if (removedProduct.equals(product)) {
                size++;
            }
        }
        if (count > size) {
            return;
        }


        for (int i = 0; i < count; i++) {
            order.getProducts().remove(removedProduct);
            order.setPrice(order.getPrice() - removedProduct.getSalePrice());
            if (order.getAccount() != null) {
                order.setFinalPrice(order.getPrice() - order.getPrice() / 100 * order.getAccount().getDiscount());
                log.info(order.getFinalPrice().toString());
            } else {
                order.setFinalPrice(order.getPrice());
            }
        }
    }

    @Override
    public Account addClientToOrder(Long id, Long clientId) {
        Booking order = findById(id);
        Account account = accountService.findById(clientId);
        order.setAccount(account);
        order.setFinalPrice(order.getPrice() - order.getPrice() / 100 * order.getAccount().getDiscount());
        return account;
    }

    @Override
    public void complete(Long id) {
        Booking booking = findById(id);
        Integer expenses = 0;
        if (pay(booking)) {
            booking.setCompleted(true);
            if (booking.getAccount() != null) {
                accountService.addOrderToAccount(booking, booking.getAccount().getId());
            }

            Map<Product, Integer> products = new HashMap<>();
            for (int i = 0; i < booking.getProducts().size(); i++) {
                products.put(booking.getProducts().get(i), 1);
                for (int j = i; j < booking.getProducts().size(); j++) {
                    if(booking.getProducts().get(i).equals(booking.getProducts().get(j))){
                        products.replace(booking.getProducts().get(i),
                                products.get(booking.getProducts().get(i)) + 1);
                    }
                }
                expenses+=booking.getProducts().get(i).getCostPrice();
            }


            coffeeService.countSales(booking.getCoffeeHouse().getId(), booking.getFinalPrice(), expenses, booking.getProducts().size());
            coffeeService.countBestProduct(booking.getCoffeeHouse().getId(), products);

        }
    }

    @Override
    public boolean pay(Booking booking) {
        return true; // Логика оплата
    }
}
