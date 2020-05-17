package com.ominext.cms.service;

import com.ominext.cms.exception.RecordNotFoundException;
import com.ominext.cms.model.Product;
import com.ominext.cms.repository.ProductRepository;
import com.ominext.cms.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public void save(Product product) {
        product.setCreatedAt(DateUtils.currentTimestamp());
        repository.save(product);
    }

    public Product getProduct(Long id) throws RecordNotFoundException {
        Optional<Product> product = repository.findFirstById(id);
        if (!product.isPresent()) {
            throw new RecordNotFoundException("No product record exist for given id");
        }
        return product.get();
    }

    public List<Product> getAllByIds(List<Long> ids) {
        return repository.findAllByIds(ids);
    }

    public List<Product> getAll() {
        return repository.findAll();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public void update(Long id, Product product) throws RecordNotFoundException {
        Optional<Product> productLocal = repository.findFirstById(id);
        if (!productLocal.isPresent()) {
            throw new RecordNotFoundException("No product record exist for given id");
        }
        updateRecord(productLocal.get(), product);
    }

    private void updateRecord(Product entity, Product request) {
        if (request == null) return;
        if (request.getName() != null) {
            entity.setName(request.getName());
        }
        if (request.getPrice() != null) {
            entity.setPrice(request.getPrice());
        }
        if (request.getImage() != null) {
            entity.setImage(request.getImage());
        }
        if (request.getDiscount() != null) {
            entity.setDiscount(request.getDiscount());
        }
        entity.setUpdatedAt(DateUtils.currentTimestamp());
    }
}
