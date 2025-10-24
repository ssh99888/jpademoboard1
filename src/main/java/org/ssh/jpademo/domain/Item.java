package org.ssh.jpademo.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

//@Data
@Entity
@Table(name = "item")
@Getter @Setter @ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // 상품코드

    @Column(unique = true, nullable = false, length = 50)
    private String itemNm;      // 상품명

    @Column(name = "price", nullable = false)
    @ColumnDefault(value = "1000")
    private int price;          // 가격

    @Column(nullable = false)
    private int stockNumber;    // 재고수량

    @Lob
    @Column(nullable = false)
//    @ColumnDefault(value = "10")
    private String itemDetail;  // 상품 상세 설명

    @Enumerated(EnumType.STRING) // ORDINAL : 0,1,2  ,  STRING : 판매중, 판매완료, 입고대기
    private ItemSellStatus itemSellStatus;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regTime;
    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Transient
    private String memo;
    @Transient
    private String mark;
}
