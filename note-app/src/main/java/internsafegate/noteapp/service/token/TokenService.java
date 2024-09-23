package internsafegate.noteapp.service.token;

public interface TokenService {
    boolean isTokenRevoked(String jwt);
}
