package org.toy.jpashop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.toy.jpashop.domain.Member;
import org.toy.jpashop.repository.MemberRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository repository;

    // 회원 등록
    @Transactional
    public Long join(Member member) {
        // 아이디 중복 체크
        validateDuplicateMember(member);
        repository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {

        List<Member> findMembers = repository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("동일한 회원이 존재합니다.");
        }
    }
    // 회원 목록
    public List<Member> findMembers() {
        return repository.findAll();
    }

    public Member findOne(Long id) {
        return repository.findOne(id);
    }
}
