package kz.jaguars.hackathon.services;

import kz.jaguars.hackathon.models.Staff;

public interface StaffService extends CrudService<Staff, Long>{
    Staff findByEmail(String email);

    Staff saveWithRole(Staff account, Long coffeeHouse, String roleName);

    String isLogged();
}

