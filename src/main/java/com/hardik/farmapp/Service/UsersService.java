package com.hardik.farmapp.Service;

import com.hardik.farmapp.Entity.Users;
import com.hardik.farmapp.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users register(Users user) {

        user.setPassword(encoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    public String verify(Users user) {
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken
                                (user.getEmail(), user.getPassword())
                );

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getEmail());
        }
        else{
            return "Fail";
        }
    }
}
