package kz.jaguars.hackathon.services.implementations;

import kz.jaguars.hackathon.exceptions.NotFoundException;
import kz.jaguars.hackathon.models.BestProduct;
import kz.jaguars.hackathon.models.CoffeeHouse;
import kz.jaguars.hackathon.models.Product;
import kz.jaguars.hackathon.models.Staff;
import kz.jaguars.hackathon.repositories.CoffeeRepository;
import kz.jaguars.hackathon.services.CoffeeService;
import kz.jaguars.hackathon.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CoffeeServiceImpl implements CoffeeService {

    private final CoffeeRepository coffeeRepository;

    private final ProductService productService;

    @Override
    public CoffeeHouse save(CoffeeHouse entity) {
        entity.setCountSales(0);
        entity.setAverageBill(0.0);
        entity.setProfit(0);
        entity.setExpenses(0);
        entity.setMarginality(0);
        entity.setSalesVolume(0.0);
        entity.setBestProducts(new HashSet<>());

        entity = save(entity);

        addCountNumberProductToCaffe(entity.getId());
        return entity;
    }

    @Override
    public void addCountNumberProductToCaffe(Long id) {
        CoffeeHouse coffeeHouse = findById(id);
        List<Product> products = productService.findAll();

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

    @Override
    public void countSales(Long id, Integer profit, Integer expenses, int size) {
        CoffeeHouse coffeeHouse = findById(id);

        coffeeHouse.setExpenses(coffeeHouse.getExpenses() + expenses);
        coffeeHouse.setProfit(coffeeHouse.getProfit() + profit);

        coffeeHouse.setAverageBill((
                coffeeHouse.getAverageBill() * coffeeHouse.getCountSales() + profit)/(coffeeHouse.getCountSales()+1));

        coffeeHouse.setCountSales(coffeeHouse.getCountSales() + size);

        coffeeHouse.setMarginality(coffeeHouse.getMarginality() + profit - expenses);
        coffeeHouse.setSalesVolume(coffeeHouse.getAverageBill() * coffeeHouse.getCountSales());
    }

    @Override
    public void countBestProduct(Long id, Map<Product, Integer> countProduct) {
        CoffeeHouse coffeeHouse = findById(id);
        Set<BestProduct> bestProducts = coffeeHouse.getBestProducts();
        log.info(String.valueOf(bestProducts.size()));
        for (BestProduct best: bestProducts){
            if(countProduct.containsKey(best.getProduct())){
                bestProducts.remove(best);
                bestProducts.add(BestProduct.builder()
                                .product(best.getProduct())
                                .count(best.getCount() + countProduct.get(best.getProduct()))
                        .build());
            }
        }
    }
}
