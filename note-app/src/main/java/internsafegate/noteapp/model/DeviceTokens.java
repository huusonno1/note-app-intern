package internsafegate.noteapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "device_tokens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceTokens extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token_of_device")
    private String tokenOfDevice;

    @Column(name = "platform")
    private String platform;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;
}
