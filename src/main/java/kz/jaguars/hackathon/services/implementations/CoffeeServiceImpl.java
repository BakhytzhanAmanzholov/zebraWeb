package kz.jaguars.hackathon.services.implementations;

import kz.jaguars.hackathon.exceptions.NotFoundException;
import kz.jaguars.hackathon.models.CoffeeHouse;
import kz.jaguars.hackathon.models.Staff;
import kz.jaguars.hackathon.repositories.CoffeeRepository;
import kz.jaguars.hackathon.services.CoffeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CoffeeServiceImpl implements CoffeeService {

    private final CoffeeRepository coffeeRepository;

    @Override
    public CoffeeHouse save(CoffeeHouse entity) {
        return coffeeRepository.save(entity);
    }

    @Override
    public void delete(Long aLong) {
        coffeeRepository.deleteById(aLong);
    }

    @Override
    public CoffeeHouse update(CoffeeHouse entity) {
        CoffeeHouse house = findById(entity.getId());
        house.setAddress(entity.getAddress());
        house.setShortName(entity.getShortName());
        house.setWorkingHours(entity.getWorkingHours());
        return house;
    }

    @Override
    public List<CoffeeHouse> findAll() {
        return (List<CoffeeHouse>) coffeeRepository.findAll();
    }

    @Override
    public void addStaffToCoffeeHouse(Staff staff, Long id) {
        CoffeeHouse house = findById(id);
        house.getStaffs().add(staff);
    }

    @Override
    public CoffeeHouse findById(Long aLong) {
        return coffeeRepository.findById(aLong).orElseThrow(
                () -> new NotFoundException("Coffee <" + aLong + "> not found"));
    }
}
