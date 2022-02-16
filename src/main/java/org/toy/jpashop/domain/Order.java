package org.toy.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태[ORDER, CANCEL]

    //==연관관계 메서드
    public void setMember(Member member) {
        this.member = member;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //== 생성 메서드
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.ORDER);
        for (OrderItem item: orderItems) {
            order.addOrderItem(item);
        }

        return order;
    }

    // 비즈니스 로직이 되는거지
    /* 주문 취소 */
    public void cancle() {
        if (delivery.getStatus()== DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송된 제품은 취소할 수 없습니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem item :
                this.orderItems) {
            item.cancle();
        }
    }

    /* 전체 주문 가격 조회 */
    public int getTotalOrderPrice() {
        int resultOrderTotalPrice = 0;
        for (OrderItem orderItem:
             getOrderItems())  {
            resultOrderTotalPrice += orderItem.getTotalPrice();
        }

        return resultOrderTotalPrice;
    }
}
