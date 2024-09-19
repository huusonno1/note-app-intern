package internsafegate.noteapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

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

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "status_token")
    private Boolean statusToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    public String generateDeviceId() {
        // Tạo UUID v4
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
