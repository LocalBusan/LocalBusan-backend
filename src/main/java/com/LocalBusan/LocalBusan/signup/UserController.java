package com.LocalBusan.LocalBusan.signup;

import com.LocalBusan.LocalBusan.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<Void> loginJWT(@RequestBody Map<String, String> data, HttpServletResponse response){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(data.get("username"), data.get("password"));
        // 로그인 시켜주는 코드
        var auth = authenticationManagerBuilder.getObject().authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        var jwt = JwtUtil.createToken(SecurityContextHolder.getContext().getAuthentication());

        var cookie = new Cookie("jwt", jwt);
        cookie.setMaxAge(10800);
        sscookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setAttribute("SameSite", "None");
        cookie.setPath("/");
        response.addCookie(cookie);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    }

    @GetMapping("/my-page/jwt")
    @ResponseBody
    String myPageJWT(Authentication auth){
        var user = (CustomUser) auth.getPrincipal();
        System.out.println(user.getUsername());
        System.out.println(user.getAuthorities());
        System.out.println(user.getNickname());

        return "마이페이지";
    }
}