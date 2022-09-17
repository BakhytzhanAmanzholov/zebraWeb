package kz.jaguars.hackathon.services;

import kz.jaguars.hackathon.models.Category;
import kz.jaguars.hackathon.models.Product;

public interface CategoryService extends CrudService<Category, Long> {
    Category findByTitle(String title);

    void addProductToCategory(Product product, String title);
}
