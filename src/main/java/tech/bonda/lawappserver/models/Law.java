package tech.bonda.lawappserver.models;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Law {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "content", nullable = false)
    private String content;

//    @Column(name = "category", nullable = false)
//    private String category;

    @Column(name = "date_passed")
    private String datePassed;
}
