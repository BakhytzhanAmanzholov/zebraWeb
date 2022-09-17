package kz.jaguars.hackathon.services.implementations;

import kz.jaguars.hackathon.exceptions.NotFoundException;
import kz.jaguars.hackathon.models.Category;
import kz.jaguars.hackathon.models.Product;
import kz.jaguars.hackathon.repositories.ProductRepository;
import kz.jaguars.hackathon.services.CategoryService;
import kz.jaguars.hackathon.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private final CategoryService categoryService;


    @Override
    public Product saveWithCategory(Product product, String title) {
        Category category = categoryService.findByTitle(title);
        product.setCategory(category);
        product = save(product);
        categoryService.addProductToCategory(product, category.getTitle());
        return product;
    }

    @Override
    public Product save(Product entity) {
        return productRepository.save(entity);
    }

    @Override
    public void delete(Long aLong) {
        productRepository.deleteById(aLong);
    }

    @Override
    public Product update(Product entity) {
        Product product = findById(entity.getId());
        product.setTitle(entity.getTitle());
        product.setDescription(entity.getDescription());
        product.setCostPrice(entity.getCostPrice());
        product.setSalePrice(entity.getSalePrice());
        return product;
    }

    @Override
    public Product findById(Long aLong) {
        return productRepository.findById(aLong).orElseThrow(
                () -> new NotFoundException("Preference <" + aLong + "> not found"));
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findByTitle(String title) {
        return productRepository.findByTitle(title).orElseThrow(
                () -> new NotFoundException("Preference <" + title + "> not found"));
    }
}
