package project.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.entity.User;
import project.repository.UserRepository;

import static project.config.WebSecurityConfig.passwordEncoder;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("In load "+username);
        User user = userRepository.findByEmail(username).get();
        if(user == null){
            new UsernameNotFoundException("User not exists by Username");
        }
        UserDetails userInDetails = org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
        return userInDetails;
    }
}
