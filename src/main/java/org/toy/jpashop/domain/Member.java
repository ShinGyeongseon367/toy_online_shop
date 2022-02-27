package org.toy.jpashop.domain;

import lombok.Getter;
import lombok.Setter;
import org.toy.jpashop.web.MemberForm;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public static Member createMember(Address address, String name) {
        Member member = new Member();
        member.setAddress(address);
        member.setName(name);
        return member;
    }
}
