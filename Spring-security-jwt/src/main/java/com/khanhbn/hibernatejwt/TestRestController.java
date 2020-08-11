package com.khanhbn.hibernatejwt;


import com.khanhbn.hibernatejwt.jwt.JwtTokenProvider;
import com.khanhbn.hibernatejwt.payload.LoginRequest;
import com.khanhbn.hibernatejwt.payload.LoginResponse;
import com.khanhbn.hibernatejwt.payload.RandomStuff;
import com.khanhbn.hibernatejwt.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class TestRestController {

    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @RequestMapping("/v1")
    public LoginResponse test(@RequestBody LoginRequest loginRequest) {
        return new LoginResponse("123");
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        // Xác thực thông tin người dùng Request lên
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        // Nếu không xảy ra exception có nghĩa là thông tin hợp lệ
        // Set thông tin authentication và Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng
        String jwt = jwtTokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        return new LoginResponse(jwt);
    }

    @GetMapping("/random")
    public RandomStuff randomStuff(){
        return new RandomStuff("JWT Hợp lệ mới có thể thấy được message này");
    }

}
