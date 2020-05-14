package com.ominext.cms.service;

import com.ominext.cms.exception.RecordNotFoundException;
import com.ominext.cms.model.Card;
import com.ominext.cms.repository.CardRepository;
import com.ominext.cms.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    private final CardRepository repository;

    public CardService(CardRepository repository) {
        this.repository = repository;
    }

    public void save(Card card) {
        repository.save(card);
    }

    public Card get(Long id) throws RecordNotFoundException {
        Optional<Card> card = repository.findFirstById(id);
        if (!card.isPresent()) {
            throw new RecordNotFoundException("No Card record exist for given id");
        }
        return card.get();
    }

    public List<Card> getAll() {
        return repository.findAll();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public void update(Long id, Card card) throws RecordNotFoundException {
        Optional<Card> cardLocal = repository.findFirstById(id);
        if (!cardLocal.isPresent()) {
            throw new RecordNotFoundException("No Card record exist for given id");
        }
        updateRecord(cardLocal.get(), card);
    }

    private void updateRecord(Card entity, Card request) {
        if (request == null) return;
        if (request.getProductId() != null) {
            entity.setProductId(request.getProductId());
        }
        if (request.getUserId() != null) {
            entity.setUserId(request.getUserId());
        }
        if (request.getTotal() != null) {
            entity.setTotal(request.getTotal());
        }
        entity.setUpdatedAt(DateUtils.currentTimestamp());
    }
}
