package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ADMIN 7/30/2023
 */
@Entity
@Table(name = "devices")
@Getter
@Setter
public class Device {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "id_user")
  private User user;

  private String deviceToken;

  private String deviceType;
}
