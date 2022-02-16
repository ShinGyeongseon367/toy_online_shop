package org.toy.jpashop.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.toy.jpashop.domain.*;
import org.toy.jpashop.domain.item.Book;
import org.toy.jpashop.domain.item.Item;
import org.toy.jpashop.exception.NotEnoughStockException;
import org.toy.jpashop.repository.OrderRepository;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    @Autowired EntityManager em;

    @Test
    @DisplayName("상품주문")
    public void OrderServiceTest() {
        // given
        Member member = createMember("회원1", new Address("서울", "강가", "123-123"));
        Item book = createItem("시골Jap", 10000, 10);

        int ordercount = 2;
        // when
        long saveOrderId = orderService.order(member.getId(), book.getId(), ordercount);

        // then
        Order findOrder = orderRepository.findOne(saveOrderId);

        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.ORDER); // 주문상태
        assertThat(findOrder.getOrderItems().size()).isEqualTo(1); // 주문 상품이 맞아야 한다.
        assertThat(findOrder.getTotalOrderPrice()).isEqualTo(book.getPrice() * ordercount);

    }

    @Test
    @DisplayName("주문 취소")
    public void OrderServiceTest1() {
        // given
        Member member = createMember("회원1", new Address("서울", "강가", "123-123"));
        Item book = createItem("시골Jap", 10000, 10);
        // when
        int ordercount = 2;
        long saveOrderId = orderService.order(member.getId(), book.getId(), ordercount);
        orderService.orderCancle(saveOrderId);

        // then
        Order findOrder = orderRepository.findOne(saveOrderId);
        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);// 주문 상태는 취소가 되어야 한다.
        assertThat(book.getStockQuantity()).isEqualTo(10); // 주문이 취소되었으면 재고가 원래대로 복구 되어야한다.
    }

    @Test
    @DisplayName("재고수량 초과")
    public void OrderServiceTest2() {
        // given
        Member member = createMember("회원1", new Address("서울", "강가", "123-123"));
        Item book = createItem("시골Jap", 10000, 10);

        int quantityCnt = 11;
        // when

        assertThrows(NotEnoughStockException.class , () -> {
            orderService.order(member.getId(), book.getId(), quantityCnt);
        });

        // then
    }

    @Test
    @DisplayName("검색기능 테스트")
    public void OrderServiceTest3() {
        // given

        Item jpa_book = createItem("jpa cor", 30000, 10);
        Item spring_book = createItem("spring book", 30000, 20);

        Member member = createMember("shin", new Address("서울", "다리 밑", "439"));
        Member member_01 = createMember("shin_01", new Address("서울", "다리 밑", "439"));

        orderService.order(member.getId(), jpa_book.getId(), 4);
        orderService.order(member_01.getId(), spring_book.getId(), 6);

        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setOrderStatus(OrderStatus.ORDER);
        orderSearch.setMemberName("shin");
        // when

        List<Order> orders = orderService.findOrders(orderSearch);

        assertThat(orders.get(0).getMember().getName()).isEqualTo("shin");
        // then
    }

    private Item createItem(String itemName, int itemPrice, int stockQuantity) {
        Item book = new Book();
        book.setName(itemName);
        book.setPrice(itemPrice);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember(String name, Address address) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(address);
        em.persist(member);
        return member;
    }
}