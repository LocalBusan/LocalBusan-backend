package com.LocalBusan.LocalBusan.signup;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hibernate.metamodel.internal.MemberResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "User-Controller", description = "회원가입, 로그인 API")
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/api/users")
    @Operation(summary = "회원가입", description = "회원을 추가합니다.")
    public ResponseEntity<Void> addUser(@RequestBody UserRequest userRequest){
        User user = new User();
        String hash = passwordEncoder.encode(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setNickname(userRequest.getNickname());
        user.setPassword(hash);
        user.setRegion_id(userRequest.getRegion_id());
        user.setIs_admin(false);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/login")
    public String getLogin(){
        return "/login";
    }

    @PostMapping("/api/users/login")
    @ResponseBody
    @Operation(summary = "로그인", description = "로그인 합니다.")
    public String loginJWT(@RequestBody Map<String, String> data){
        System.out.println(data);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(data.get("username"), data.get("password"));
        // 로그인 시켜주는 코드
        var auth = authenticationManagerBuilder.getObject().authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        var jwt = JwtUtil.createToken(SecurityContextHolder.getContext().getAuthentication());
        System.out.println(jwt);
        return jwt;
    }
}
