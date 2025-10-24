package org.ssh.jpademo.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.ssh.jpademo.domain.Item;
import org.ssh.jpademo.domain.ItemSellStatus;

import javax.sql.DataSource;

@SpringBootTest
@Log4j2
public class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void insertItem() {
        Item item = Item.builder()
                .itemNm("aa")
                .itemDetail("aa")
                .itemSellStatus(ItemSellStatus.판매중)
                .price(1000)
                .stockNumber(10)
                .build();
        itemRepository.save(item);
    }
    @Test
    public void updateItem() {
        Item item = itemRepository.findById(1L).get();
        item.setStockNumber(0);
        item.setItemSellStatus(ItemSellStatus.판매완료);
        itemRepository.save(item);
    }
    @Test
    public void deleteItem() {
        itemRepository.deleteById(3L);
    }
}