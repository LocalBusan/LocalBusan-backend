package com.LocalBusan.LocalBusan.signup;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var result = userRepository.findByEmail(username);
        if(result.isEmpty()){
            throw new UsernameNotFoundException("아이디 없다.");
        }
        var user = result.get();
        List<GrantedAuthority> authorities = new ArrayList<>();

        if(user.getIs_admin()){
            authorities.add(new SimpleGrantedAuthority("관리자"));
        }else{
            authorities.add(new SimpleGrantedAuthority("일반유저"));
        }

        return new CustomUser(user.getEmail(), user.getPassword(), user.getNickname(), authorities);
    }

}
