package com.nt.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nt.model.Account;
import com.nt.repository.IAccountRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IAccountRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account user = userRepository.getAccountByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        if (user.isEnabled() == false) {
            throw new UsernameNotFoundException("User is deactivated");
        }
        System.out.println("user"+user);
        return new MyUserDetails(user);
    }
}
