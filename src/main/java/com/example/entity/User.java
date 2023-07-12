package com.example.entity;

import com.example.dto.user.UserDto;
import com.example.enums.RoleUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author BAO 7/3/2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames =  "username"),
        @UniqueConstraint(columnNames = "email")
})
@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  private RoleUser role;
  @Column(name = "username", nullable = false, unique = true)
  private String username;

  private String firstName;
  private String lastName;
  private int countWords;
  private String password;

  @Email
  @Column(name = "email",nullable = false, unique = false)
  private String email;
  private String phone;

  public User(UserDto userDto){
    this.id = userDto.getId();
    this.role = userDto.getRole();
    this.username = userDto.getUsername();
    this.firstName = userDto.getFirstName();
    this.lastName = userDto.getLastName();
    this.countWords = userDto.getCountWords();
    this.password = userDto.getPassword();
    this.email = userDto.getEmail();
    this.phone = userDto.getPhone();
  }
}
