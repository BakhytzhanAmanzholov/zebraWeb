package kz.jaguars.hackathon.security.details;

import kz.jaguars.hackathon.models.Staff;
import kz.jaguars.hackathon.repositories.StaffRepository;
import kz.jaguars.hackathon.security.config.JwtSecurityConfig;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@ConditionalOnBean(value = JwtSecurityConfig.class)
public class UserDetailsServiceImpl implements UserDetailsService {

    StaffRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Staff user = usersRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User <" + email + "> not found"));

        return new UserDetailsImpl(user);
    }

}

