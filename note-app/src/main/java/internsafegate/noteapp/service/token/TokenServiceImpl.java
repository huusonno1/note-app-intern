package internsafegate.noteapp.service.token;

import internsafegate.noteapp.model.Token;
import internsafegate.noteapp.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService{
    private final TokenRepository tokenRepo;

    @Override
    public boolean isTokenRevoked(String jwt) {
        Optional<Token> tokenOpt = tokenRepo.findByToken(jwt);
        if(tokenOpt.isPresent() && !tokenOpt.get().isRevoked()){
            return true;
        }
        return false;
    }
}
