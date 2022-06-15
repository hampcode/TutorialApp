package com.hampcode.model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="user_name",length = 30,nullable = false)
    private String userName;
    @Column(name="password",length = 150,nullable = false)
    private String password;
}