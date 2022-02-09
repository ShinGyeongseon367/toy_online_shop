package org.toy.jpashop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.toy.jpashop.domain.Member;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    // @Rollback(value = false) // Ignore rollback(@Transactional)
    public void saveTest() {
        Member member = new Member();
        member.setName("shin");

        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);

        assertThat(saveId).isEqualTo(findMember.getId());
    }

}