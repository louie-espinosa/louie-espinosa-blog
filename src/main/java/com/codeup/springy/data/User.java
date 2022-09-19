package com.codeup.springy.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
@Entity
//without annotating like this: @Table(name="users") the table will be generated all lower case of the class name
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Email
    @NotEmpty
    @Column(nullable = false, length = 100)
    private String email;

    @ToString.Exclude//lombok ignores the string so it hides password
    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    private LocalDate createdAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column
    private UserRole role;//this enum value is an integer which can seem abstract in a table, so lets save the string representation here

    @OneToMany(mappedBy = "author") //One author to many posts
    @JsonIgnoreProperties("author")
    private Collection<Post> posts;
}
