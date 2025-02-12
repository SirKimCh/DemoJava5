package org.java5.demojava5.controller;

import org.java5.demojava5.model.Category;
import org.java5.demojava5.model.Product;
import org.java5.demojava5.services.CategoryServices;
import org.java5.demojava5.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductServices productServices;

    @Autowired
    private CategoryServices categoryServices;

    @GetMapping({"/", ""})
    public String listProduct(Model model) {
        List<Product> listProduct = productServices.listAll();
        model.addAttribute("products", listProduct);
        return "product/index";
    }

    @GetMapping("/view/{id}")
    public String getProductById(@PathVariable Long id, @RequestParam(required = false) String source, Model model) {
        Product product = productServices.getProductById(id);
        if (product != null) {
            model.addAttribute("product", product);
            model.addAttribute("source", source);
            return "product/view";
        } else {
            return "error/404";
        }
    }

    @GetMapping("/create")
    public String showCreateProductForm(Model model) {
        List<Category> activeCategories = categoryServices.findActiveCategories();
        model.addAttribute("categories", activeCategories);
        model.addAttribute("product", new Product());
        return "product/create";
    }

    @PostMapping("/create")
    public String saveProduct(@ModelAttribute Product product) {
        productServices.saveProduct(product);
        return "redirect:/product";
    }

    @GetMapping("/update/{id}")
    public String showUpdateProductForm(@PathVariable Long id, Model model) {
        Product product = productServices.getProductById(id);
        if (product != null) {
            List<Category> activeCategories = categoryServices.findActiveCategories();
            model.addAttribute("categories", activeCategories);
            model.addAttribute("product", product);
            return "product/update";
        } else {
            return "error/404";
        }
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute Product product) {
        Product updatedProduct = productServices.updateProduct(product);
        if (updatedProduct != null) {
            return "redirect:/product";
        } else {
            return "error/404";
        }
    }

    //do i need this?
    //i dont know what i doing here
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productServices.deleteProduct(id);
        return "redirect:/product";
    }
}