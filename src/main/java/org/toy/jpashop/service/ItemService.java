package org.toy.jpashop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.toy.jpashop.domain.item.Item;
import org.toy.jpashop.repository.ItemRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository repository;

    @Transactional
    public void saveItem(Item item) {
        repository.saveItem(item);
    }

    public List<Item> findAll() {
        return repository.findAll();
    }

    public Item findOne(Long id) {
        return repository.findOne(id);
    }

}
