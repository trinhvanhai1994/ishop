package com.ominext.cms.service;

import com.ominext.cms.exception.RecordNotFoundException;
import com.ominext.cms.model.Cart;
import com.ominext.cms.model.Product;
import com.ominext.cms.repository.CardRepository;
import com.ominext.cms.response.CartResponse;
import com.ominext.cms.response.ItemResponse;
import com.ominext.cms.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CardRepository repository;
    private final ProductService productService;

    public CartService(CardRepository repository, ProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }

    public void addToCart(Long productId, Long userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setProductId(productId);
        cart.setCreatedAt(DateUtils.currentTimestamp());
        repository.save(cart);
    }

    public void deleteCartItem(Long userId, Long itemId) {

    }

    public CartResponse cartInfo(Long userId) {
        List<Cart> carts = repository.findAllByUserId(userId);
        Map<Long, Long> quantityProductIds = carts.stream()
                .collect(Collectors.groupingBy(Cart::getProductId, Collectors.counting()));
        List<Long> productIds = carts.stream().map(Cart::getProductId).collect(Collectors.toList());
        List<Product> products = productService.getAllByIds(productIds);
        return ProcessCartInfo(products, userId, quantityProductIds);
    }

    private CartResponse ProcessCartInfo(List<Product> products, Long userId, Map<Long, Long> quantityProductIds) {
        List<ItemResponse> items = new ArrayList<>();
        products.forEach(product -> {
            ItemResponse item = new ItemResponse();
            item.setName(product.getName());
            item.setImage(product.getImage());
            item.setPrice(product.getPrice());
            item.setDiscount(product.getDiscount());
            item.setQuantity(Integer.parseInt(quantityProductIds.get(product.getId()).toString()));
            items.add(item);
        });

        double amountSum = items.stream().mapToDouble(ItemResponse::getPrice).sum();
        double amountAfterDiscountSum = items.stream()
                .mapToDouble(item -> item.getPrice() - (item.getPrice()*item.getDiscount()/100)).sum();

        CartResponse response = new CartResponse();
        response.setUserId(userId);
        response.setItems(items);
        response.setAmountTotal(BigDecimal.valueOf(amountSum));
        response.setAmountAfterDiscountTotal(BigDecimal.valueOf(amountAfterDiscountSum));
        return response;
    }

    public Cart get(Long id) throws RecordNotFoundException {
        Optional<Cart> card = repository.findFirstById(id);
        if (!card.isPresent()) {
            throw new RecordNotFoundException("No Card record exist for given id");
        }
        return card.get();
    }

    public List<Cart> getAll() {
        return repository.findAll();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public void update(Long id, Cart card) throws RecordNotFoundException {
        Optional<Cart> cardLocal = repository.findFirstById(id);
        if (!cardLocal.isPresent()) {
            throw new RecordNotFoundException("No Card record exist for given id");
        }
        updateRecord(cardLocal.get(), card);
    }

    private void updateRecord(Cart entity, Cart request) {
        if (request == null) return;
        if (request.getProductId() != null) {
            entity.setProductId(request.getProductId());
        }
        if (request.getUserId() != null) {
            entity.setUserId(request.getUserId());
        }
        entity.setUpdatedAt(DateUtils.currentTimestamp());
    }
}
