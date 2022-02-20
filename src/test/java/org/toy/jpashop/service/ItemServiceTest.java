package org.toy.jpashop.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.toy.jpashop.domain.item.Book;
import org.toy.jpashop.domain.item.Item;

import java.util.List;

@SpringBootTest
class ItemServiceTest {

    @Autowired
    ItemService service;

    @Test
    public void serviceFindAllTest() {

        Item item = new Book();
        item.setName("jpa");
        item.setPrice(10000);
        item.setStockQuantity(10);
        Item item2 = new Book();
        item.setName("jpa");
        item.setPrice(10000);
        item.setStockQuantity(10);
        service.saveItem(item);
        service.saveItem(item2);

        List<Item> all = service.findAll();

        System.out.println(all);

        all.forEach(obj -> {
            Assertions.assertThat(obj).isInstanceOf(Item.class);
        });
    }
}