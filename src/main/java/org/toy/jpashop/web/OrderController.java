package org.toy.jpashop.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.toy.jpashop.domain.Member;
import org.toy.jpashop.domain.Order;
import org.toy.jpashop.domain.OrderSearch;
import org.toy.jpashop.domain.item.Item;
import org.toy.jpashop.service.ItemService;
import org.toy.jpashop.service.MemberService;
import org.toy.jpashop.service.OrderService;

import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final ItemService itemService;
    private final OrderService orderService;
    private final MemberService memberService;

    /**
     * 상품 목록
     */
    @GetMapping("")
    public String orderList(Model model) {

        List<Item> items = itemService.findAll();
        List<Member> members = memberService.findMembers();

        model.addAttribute("items", items);
        model.addAttribute("members", members);

        return "order/orderForm";
    }

    @PostMapping("/")
    public String purcharge(@RequestParam("memberId") Long memberId,
                            @RequestParam("itemId") Long itemId,
                            @RequestParam("count") int count) {

        orderService.order(memberId, itemId, count);

         return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch,
                            Model model) {

        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);

        return "order/orderList";
    }

    @PostMapping("/{orderId}/cancel")
    public String cancel(@PathVariable("orderId") Long orderId) {

        orderService.orderCancle(orderId);

        return "redirect:/orders";
    }

}
