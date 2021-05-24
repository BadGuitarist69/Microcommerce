package fr.dmu.formation.microcommerce.web.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import fr.dmu.formation.microcommerce.dao.ProductDao;
import fr.dmu.formation.microcommerce.model.Product;
import fr.dmu.formation.microcommerce.web.exceptions.ProduitIntrouvableException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api( description="API pour les opérations CRUD sur les produits.")
@RestController
@ControllerAdvice
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

    //Récupérer la liste des produits
    /*
    @RequestMapping(value = "/produits", method = RequestMethod.GET)
    public MappingJacksonValue listeProduits()
    {
        List<Product> produits = productDao.findAll();

        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("");
        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
        produitsFiltres.setFilters(listDeNosFiltres);

        return produitsFiltres;
    }
    */

    //Récupérer la liste des produits
    @ApiOperation(value = "Récupère un produit grâce à son ID à condition que celui-ci soit en stock!")
    @RequestMapping(value = "/produits/filtres", method = RequestMethod.GET)
    //public MappingJacksonValue listeProduitsFiltres()
    public List<Product> listeProduitsFiltres()
    {
        List<Product> produits = productDao.findAll();

        /*
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
        produitsFiltres.setFilters(listDeNosFiltres);

        return produitsFiltres;
        */
        return produits;
    }

    @GetMapping(value = "/produits/maxPrice/{prixLimit}")
    public List<Product> testeDeRequetes(@PathVariable int prixLimit)
    {
        return productDao.findByPrixGreaterThan(400);
    }

    @GetMapping(value = "/produits/nom/{recherche}")
    public List<Product> testeDeRequetes(@PathVariable String recherche)
    {
        return productDao.findByNomLike("%"+recherche+"%");
    }

    //Récupérer un produit par son Id
	@GetMapping(value = "/produit/{id}")
	public Product afficherUnProduit(@PathVariable int id)
	{
		Product produit = productDao.findById(id);
		if(produit==null) throw new ProduitIntrouvableException("Produit INTROUVABLE");

		return produit;
	}

    //ajouter un produit
    @PostMapping(value = "/produits")
    public ResponseEntity<Void> ajouterProduit(@RequestBody Product product)
    {

        Product productAdded =  productDao.save(product);

        if (productAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}

