package org.ssh.jpademo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ssh.jpademo.domain.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByName(String name);
//    Member findByUsername(String username);
    List<Member> findByNameLike(String name);
}
