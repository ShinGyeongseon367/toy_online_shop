package org.toy.jpashop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.toy.jpashop.domain.Address;
import org.toy.jpashop.domain.Member;
import org.toy.jpashop.repository.MemberRepository;
import org.toy.jpashop.service.MemberService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    // @Rollback(value = false) // Ignore rollback(@Transactional)
    public void saveTest() {
        Member member = new Member();
        member.setName("shin");
        member.setAddress(new Address("city", "street", "zipcode"));


        System.out.println("memberRepository ::: " + memberRepository.getClass());
        memberRepository.save(member);
    }

    @Test
    public void dotdotdot() {
    }

}