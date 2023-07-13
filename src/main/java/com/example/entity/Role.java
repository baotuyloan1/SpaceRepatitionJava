package com.example.entity;

import com.example.enums.RoleUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

/**
 * @author BAO 7/13/2023
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short id;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private RoleUser name;

    public Role(RoleUser name){
        this.name = name;
    }

    @ManyToMany(mappedBy = "roles")
    private Set<User> users ;


}
