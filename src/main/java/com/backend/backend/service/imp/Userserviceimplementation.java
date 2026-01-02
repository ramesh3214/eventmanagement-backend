package com.backend.backend.service.imp;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.backend.dto.Adduserdto;
import com.backend.backend.dto.Googlelogindto;
import com.backend.backend.dto.Updateuser;
import com.backend.backend.dto.UserLogindto;
import com.backend.backend.dto.Userdto;
import com.backend.backend.dto.Userlogindata;
import com.backend.backend.dto.Userpasswordchange;
import com.backend.backend.entity.User;
import com.backend.backend.repository.Userepo;
import com.backend.backend.service.GoogleTokenVerifierService;
import com.backend.backend.service.Userservice;
import com.backend.backend.util.JwtUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Userserviceimplementation implements Userservice {

    private final Userepo userepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtutil;
    private final GoogleTokenVerifierService googletoken;

    // ✅ REGISTER
    @Override
    public Userdto createnewuser(Adduserdto adduserdto) {
        if (userepo.findByEmail(adduserdto.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists with this email");
        }

        User newUser = new User();
        newUser.setName(adduserdto.getName());
        newUser.setEmail(adduserdto.getEmail());
        newUser.setNumber(adduserdto.getNumber());
        newUser.setPassword(passwordEncoder.encode(adduserdto.getPassword()));
        newUser.setRole("ROLE_USER");
        newUser.setLoginprovider("Email");

        User savedUser = userepo.save(newUser);

        return new Userdto(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getNumber(),
                savedUser.getCreatedYear(),
                savedUser.getLoginprovider());
    }

    // ✅ EMAIL/PASSWORD LOGIN
    @Override
    public UserLogindto isvalid(Userlogindata userlogindata) {
        User user = userepo.findByEmail(userlogindata.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(userlogindata.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtutil.generateToken(user.getEmail(), user.getRole());

        return new UserLogindto(token, user.getId(), user.getName(), user.getEmail(), user.getRole(),user.getCreatedYear());
    }

    // ✅ GET ALL USERS
    @Override
    public List<Userdto> getstudentall() {
        return userepo.findAll()
                .stream()
                .map(user -> new Userdto(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getNumber(),
                        user.getCreatedYear(),
                        user.getLoginprovider()))
                .toList();
    }

    // ✅ UPDATE USER
    @Override
    public Userdto updateuserdetail(Updateuser userdto, UUID id) {
        User user = userepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(userdto.getName());
        user.setNumber(userdto.getNumber());
        user.setEmail(userdto.getEmail());

        User savedUser = userepo.save(user);

        return new Userdto(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getNumber(),
                savedUser.getCreatedYear(),
                savedUser.getLoginprovider());
    }

    // ✅ CHANGE PASSWORD
   public void passwordchange(String email, String newPassword) {
    User user = userepo.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    user.setPassword(passwordEncoder.encode(newPassword));
    userepo.save(user);
}


    // ✅ GOOGLE LOGIN
    @Override
    public UserLogindto googleLogin(Googlelogindto googleDto) {
        // Verify Google ID token
        Payload payload = googletoken.verify(googleDto.getIdToken());
        if (payload == null) {
            throw new RuntimeException("Invalid Google ID token");
        }

        String email = payload.getEmail();
        String name = (String) payload.get("name");

        // Find or create user
        User user = userepo.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setName(name);
                    newUser.setNumber(0L);
                    newUser.setRole("ROLE_USER");
                    newUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString())); // Random password
                    newUser.setLoginprovider("GoogleLogin");
                    return userepo.save(newUser);
                });

        // Generate JWT token
        String token = jwtutil.generateToken(user.getEmail(), user.getRole());

        return new UserLogindto(token, user.getId(), user.getName(), user.getEmail(), user.getRole(),user.getCreatedYear());
    }
}
