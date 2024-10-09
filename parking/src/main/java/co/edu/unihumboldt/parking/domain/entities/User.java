package co.edu.unihumboldt.parking.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users",schema = "project", indexes = @Index(columnList = "id"))

public class User implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userName;
    private String password;
    private String name;
    private String idNumber;
    private String phoneNumber;
    private String email;
    private boolean status;
    @ManyToOne
    private CarPark carPark;
    @ManyToOne
    private Role role;



}
