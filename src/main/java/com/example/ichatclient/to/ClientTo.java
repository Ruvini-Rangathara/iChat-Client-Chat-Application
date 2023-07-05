package com.example.ichatclient.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="client")
public class ClientTo {
    @Id
    private String username;
    private String password;
    private String email;

}
