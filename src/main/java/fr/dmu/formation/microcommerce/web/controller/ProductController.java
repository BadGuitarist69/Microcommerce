package fr.dmu.formation.microcommerce.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.dmu.formation.microcommerce.dao.ProductDao;
import fr.dmu.formation.microcommerce.model.Product;

@RestController
public class ProductController
{

    @Autowired
    private ProductDao productDao;

    //Récupérer la liste des produits
    @RequestMapping(value="/produits", method=RequestMethod.GET)
    public List<Product>listeProduits()
    {
        return productDao.findAll();
    }

    //Récupérer un produit par son Id
    @GetMapping(value="/produits/{id}")
    public Product afficherUnProduit(@PathVariable int id)
    {
        return productDao.findById(id);
    }

    //ajouter un produit
	@PostMapping(value = "/produits")
	public void ajouterProduit(@RequestBody Product product)
	{
	     productDao.save(product);
	}

}

