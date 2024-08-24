package internsafegate.noteapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "note_content_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteContentHistory extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "note_content_id")
    private Long noteContentId;

    @Column(name = "note_id")
    private Long noteId;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "text_content")
    private String textContent;

    @Column(name = "status_note_content")
    private String statusNoteContent;

    @Column(name = "changed_at")
    private LocalDateTime changedAt;

    @Column(name = "action")
    private String action;

}
