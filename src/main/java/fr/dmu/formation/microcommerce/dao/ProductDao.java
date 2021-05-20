package fr.dmu.formation.microcommerce.dao;



import java.util.List;

import fr.dmu.formation.microcommerce.model.Product;

public interface ProductDao
{
    public List<Product>findAll();
    public Product findById(int id);
    public Product save(Product product);
}
