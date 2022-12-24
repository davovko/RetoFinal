package com.sophos.sophosBank.security;

import com.sophos.sophosBank.entity.User;
import com.sophos.sophosBank.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class UserDetailServiceImplementation implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findOneByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + email + " no existe"));

        return new UserDetailsImplementation(user);
    }

    public int userActive(HttpServletRequest request){

        int userActiveId = 0;

        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer")){
            String token = bearerToken.replace("Bearer ", "");
            String payload = token.split("\\.")[1];
            String decodePayload = null;
            try {
                decodePayload = new String(Base64.decodeBase64(payload), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            JSONObject json = new JSONObject(decodePayload);

            userActiveId = json.getInt("user_id");
        }
        return userActiveId;
    }
}
