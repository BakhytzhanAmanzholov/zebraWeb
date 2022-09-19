package kz.jaguars.hackathon.services;

import kz.jaguars.hackathon.models.CoffeeHouse;
import kz.jaguars.hackathon.models.Product;
import kz.jaguars.hackathon.models.Staff;

import java.util.List;
import java.util.Map;

public interface CoffeeService extends CrudService<CoffeeHouse, Long>{

    void addStaffToCoffeeHouse(Staff staff, Long id);

    List<CoffeeHouse> findAll();

    void countSales(Long id, Integer profit, Integer expenses, int size);

    void countBestProduct(Long id, Map<Product, Integer> countProduct);

    void addCountNumberProductToCaffe(Long id);
}
