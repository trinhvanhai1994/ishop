package com.ominext.cms.web;

import com.ominext.cms.exception.RecordNotFoundException;
import com.ominext.cms.model.Product;
import com.ominext.cms.model.User;
import com.ominext.cms.service.ProductService;
import com.ominext.cms.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService service;
    private final UserService userService;

    public ProductController(ProductService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @GetMapping("/redirectProduct")
    public ModelAndView createEmployee() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("product", new Product());
        modelAndView.setViewName("product/add-edit-product");
        return modelAndView;
    }

    @PostMapping("/create-product")
    public ModelAndView createProduct(Product product) throws RecordNotFoundException {
        System.out.println(product);
        Product productLocal = service.get(product.getId());
        if (productLocal != null) {
            service.update(product.getId(), product);
        } else {
            service.save(product);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/products");
        return modelAndView;
    }

    @GetMapping("/product/{id}")
    public ModelAndView getProduct(@PathVariable Long id) throws RecordNotFoundException {
        Product product = service.get(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("product", product);
        modelAndView.setViewName("product/add-edit-product");
        return modelAndView;
    }

    @PostMapping("/edit-product/{id}")
    public ModelAndView editProduct(@PathVariable Long id) throws RecordNotFoundException {
        Product product = service.get(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("product", product);
        modelAndView.setViewName("product/add-edit-product");
        return modelAndView;
    }

    @PostMapping("/delete-product/{id}")
    public ModelAndView deleteProduct(@PathVariable Long id) {
        service.delete(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/products");
        return modelAndView;
    }

    @GetMapping("/products")
    public ModelAndView getAllProducts() {
        List<Product> products = service.getAll();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products", products);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("product/list-products");
        return modelAndView;
    }
}
