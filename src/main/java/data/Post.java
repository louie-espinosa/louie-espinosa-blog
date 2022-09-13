package data;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@NoArgsConstructor //These constrictors are created for us by lombok!!!!
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="posts")
//class names are singular, and table names are plural
public class Post {
    //These annotations are setting up our post Class to link to a database table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 1024)
    private String content;

    //The @Transient annotation prevents the fields from mapping to database columns.
    @Transient
    private User author;

    @Transient
    private Collection<Category> categories;


}
