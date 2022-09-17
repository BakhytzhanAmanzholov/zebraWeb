package kz.jaguars.hackathon.services.implementations;

import kz.jaguars.hackathon.exceptions.NotFoundException;
import kz.jaguars.hackathon.models.Category;
import kz.jaguars.hackathon.models.Product;
import kz.jaguars.hackathon.repositories.CategoryRepository;
import kz.jaguars.hackathon.services.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category findByTitle(String title) {
        return categoryRepository.findByTitle(title).orElseThrow(
                () -> new NotFoundException("Category <" + title + "> not found"));
    }

    @Override
    public Category save(Category entity) {
        return categoryRepository.save(entity);
    }

    @Override
    public void delete(Long aLong) {
        categoryRepository.deleteById(aLong);
    }

    @Override
    public Category update(Category entity) {
        Category category = findById(entity.getId());
        category.setTitle(entity.getTitle());
        category.setDescription(entity.getDescription());
        return category;
    }

    @Override
    public void addProductToCategory(Product product, String title) {
        Category category = findByTitle(title);
        category.getProducts().add(product);
    }

    @Override
    public Category findById(Long aLong) {
        return categoryRepository.findById(aLong).orElseThrow(
                () -> new NotFoundException("Category <" + aLong + "> not found"));
    }
}
