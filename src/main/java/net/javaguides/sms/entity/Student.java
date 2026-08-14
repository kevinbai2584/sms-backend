package net.javaguides.sms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")

public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Please provide a firstname")
    @Column (name = "first_name")
    private String firstName;
    @NotBlank(message = "Please provide a lastname")
    @Column (name = "last_name")
    private String lastName;
    @Email(message = "please provide a valid email address")
    @Column (name = "email_id", nullable = false, unique = true)
    private String email;


}
