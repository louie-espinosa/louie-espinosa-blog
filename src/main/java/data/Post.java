package data;

import lombok.*;

@NoArgsConstructor //These constrictors are created for us by lombok!!!!
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Post {
    private Long id;
    private String title;
    private String content;

    private User author;


}
