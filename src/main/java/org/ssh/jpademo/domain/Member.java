package org.ssh.jpademo.domain;

import jakarta.persistence.*;
import lombok.*;

//@Data
@Setter
@Getter
@ToString
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String password;
    private String email;
    private String memo;
    @Column(name = "address")
    private String addr;
}
