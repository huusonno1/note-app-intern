package internsafegate.noteapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tags")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tags extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_tag")
    private String nameTag;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToMany(mappedBy = "tags")
    private List<Notes> notes;
}
