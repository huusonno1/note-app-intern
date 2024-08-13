package internsafegate.noteapp.security;


import internsafegate.noteapp.model.Users;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    public Users getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null &&
                authentication.getPrincipal() instanceof Users selectedUser){
            if(!selectedUser.isActive()){
                return null;
            }
            return (Users) authentication.getPrincipal();
        }
        return null;
    }
}