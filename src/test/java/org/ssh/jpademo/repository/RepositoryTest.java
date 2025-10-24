package org.ssh.jpademo.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.ssh.jpademo.domain.Member;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class RepositoryTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void MemberInsertTest() {
        Member member = Member.builder()
                .name("동길동2")
                .password("12345")
                .email("pqr2@naver.com")
                .addr("부산진구")
                .build();
        memberRepository.save(member);
    }

    @Test
    public void pageListTest() {
        Page<Member> memberPage = memberRepository.findAll(PageRequest.of(0,3, Sort.by("id").descending()));
        List<Member> members = memberPage.getContent();
        int totalPage =  memberPage.getTotalPages();
        log.info("totalPage:{}", totalPage);
        for(Member member : members){
            log.info(member);
        }
    }

    @Test
    public void findAll() {
        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            log.info(member.toString());
        }
    }
    @Test
    public void findById() {
        Member member = memberRepository.findById(1L).get();
        Member member2 = memberRepository.findById(2L).orElse(null);
        log.info(member);
        log.info(member2);
    }
    @Test
    public void updateMember() {
        Member member = memberRepository.findById(1L).get();
        member.setPassword("abcd");
        member.setAddr("부산광역시 남구");
        memberRepository.save(member);
    }
    @Test
    public void deleteById() {
        memberRepository.deleteById(1L);
    }

    @Test
    public void findByName() {
        Member member = memberRepository.findByName("홍길동");
        log.info(member.toString());
    }
    @Test
    public void findByNameLike() {
        List<Member> members = memberRepository.findByNameLike("길동");
        for (Member member : members) {
            log.info(member);
        }
    }
}
