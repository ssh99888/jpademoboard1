package org.ssh.jpademo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ssh.jpademo.domain.Item;

public interface ItemRepository extends JpaRepository<Item,Long> {

}
