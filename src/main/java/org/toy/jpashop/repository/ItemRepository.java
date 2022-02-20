package org.toy.jpashop.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.toy.jpashop.domain.item.Item;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    /**
     * 상품 등록, 변경
     */
    public Long saveItem(Item item) {
        if (item.getId() != null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
        return item.getId();
    }

    /**
     * 상품 목록
     */
    public List<Item> findAll() {
        return em.createQuery("select i from Item i ", Item.class).getResultList();
    }

    /**
     * 삼풍검색
     */
    public Item findOne(long id) {
        return em.find(Item.class, id);
    }

}
