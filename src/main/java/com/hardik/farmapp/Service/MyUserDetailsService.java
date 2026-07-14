package com.hardik.farmapp.Service;

import com.hardik.farmapp.Entity.Users;
import com.hardik.farmapp.Entity.UsersPrincipal;
import com.hardik.farmapp.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = usersRepository.findByEmail(email);
        if(user==null){
            throw new UsernameNotFoundException("Sorry the user is not Found");
        }
        return new UsersPrincipal(user);
    }
}
