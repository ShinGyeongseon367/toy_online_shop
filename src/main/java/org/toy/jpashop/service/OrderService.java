package org.toy.jpashop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.toy.jpashop.domain.*;
import org.toy.jpashop.domain.item.Item;
import org.toy.jpashop.repository.ItemRepository;
import org.toy.jpashop.repository.MemberRepository;
import org.toy.jpashop.repository.OrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문 로직
     */
    @Transactional
    public long order(long memberId, long itemId, int count) {
        Member findMember = memberRepository.findOne(memberId);
        Item findItem = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(findMember.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        OrderItem orderItem = OrderItem.createOrderItem(findItem, findItem.getPrice(), count);
        Order order = Order.createOrder(findMember, delivery, orderItem);

        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void orderCancle(Long orderId) {
        Order findOrder = orderRepository.findOne(orderId);
        findOrder.cancle();
    }

    /**
     * 검색
     */
      public List<Order> findOrders(OrderSearch orderSearch) {
          return orderRepository.findAll(orderSearch);
      }
}
