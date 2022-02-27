package org.toy.jpashop.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.toy.jpashop.domain.Address;
import org.toy.jpashop.domain.Member;
import org.toy.jpashop.service.MemberService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("")
    public String members(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "members/membersList";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/new")
    public String create(@Valid MemberForm form, BindingResult result) {

        if(result.hasErrors()) {
            return "members/createMemberForm";
        }

        // setter 남발을 하지 말자. 데이터를 따로 따로 수정하는 것이 아니라 기능을 정의해서 복잡함을 줄이자.
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = Member.createMember(address, form.getName());

        memberService.join(member);

        return "redirect:/";
    }

}

