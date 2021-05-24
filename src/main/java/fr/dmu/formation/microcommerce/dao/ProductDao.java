package fr.dmu.formation.microcommerce.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.dmu.formation.microcommerce.model.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer>
{
    //public List<Product>findAll();
    public Product findById(int id);
    //public Product save(Product product);
    List<Product> findByPrixGreaterThan(int prixLimit);
    List<Product> findByNomLike(String recherche);
}
