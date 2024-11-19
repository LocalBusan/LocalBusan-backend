package com.LocalBusan.LocalBusan.signup;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hibernate.metamodel.internal.MemberResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Tag(name = "User-Controller", description = "회원가입, 로그인 API")
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    @GetMapping("/users")
    public String users(){
        return "users";
    }

    @PostMapping("/api/users")
    @Operation(summary = "회원가입", description = "회원을 추가합니다.")
    public ResponseEntity<Void> addUser(@RequestBody UserRequest userRequest){
        User user = new User();
        String hash = new BCryptPasswordEncoder().encode(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setNickname(userRequest.getNickname());
        user.setPassword(hash);
        user.setRegion_id(userRequest.getRegion_id());
        user.setIs_admin(false);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
