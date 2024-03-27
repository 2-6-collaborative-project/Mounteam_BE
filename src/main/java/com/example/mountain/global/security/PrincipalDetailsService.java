package com.example.mountain.global.security;

import com.example.mountain.domain.user.entity.User;
import com.example.mountain.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Long longUserId = Long.valueOf(userId);

        User user = userRepository.findById(longUserId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with userId: " + userId));

        return new CustomUserDetails(user);

    }
}
