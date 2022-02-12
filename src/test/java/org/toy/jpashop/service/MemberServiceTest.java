package org.toy.jpashop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.toy.jpashop.domain.Address;
import org.toy.jpashop.domain.Member;
import org.toy.jpashop.repository.MemberRepository;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;

    @Autowired private EntityManager em;

    @Test
    public void 회원가입() {
        // given
        Member member = new Member();
        member.setName("shin");
        Address addr = new Address("seoul", "bomundong", "64-13");
        member.setAddress(addr);

        // when
        Long joinId = memberService.join(member);

        // then
        Assertions.assertEquals(member, memberRepository.findOne(joinId));
    }

    @Test
//    @Rollback(false)
    public void 중복_회원_예약() throws Exception{

        Member member1 = new Member();
        member1.setName("kim1");
        Member member2 = new Member();
        member2.setName("kim1");

        try {
            memberService.join(member1);
            memberService.join(member2);
        } catch (IllegalStateException e ) {
            System.out.println("에러 발생해서 일단은 테스트가 성공을 할 것이ㅏㄷ. ");
            return;
        }

        Assertions.fail("예외가 발생한다."); // 코드가 돌다가 여기까지 오면 안됨
    }

}