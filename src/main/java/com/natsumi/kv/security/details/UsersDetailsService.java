package com.natsumi.kv.security.details;


import com.natsumi.kv.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsersDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) {
            throw new UsernameNotFoundException("username is null");
        }else{
            try {
                UsersDetails details = new UsersDetails(usersRepository.getUserBylogin(username));
                System.out.println(details.getPassword());
                return details;
            }catch (Exception e){
                throw new UsernameNotFoundException(e.getMessage());
            }
        }
    }
}
