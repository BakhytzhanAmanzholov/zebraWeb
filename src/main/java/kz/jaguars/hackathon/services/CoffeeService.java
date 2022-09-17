package kz.jaguars.hackathon.services;

import kz.jaguars.hackathon.models.CoffeeHouse;
import kz.jaguars.hackathon.models.Staff;

import java.util.List;

public interface CoffeeService extends CrudService<CoffeeHouse, Long>{

    void addStaffToCoffeeHouse(Staff staff, Long id);

    List<CoffeeHouse> findAll();
}
