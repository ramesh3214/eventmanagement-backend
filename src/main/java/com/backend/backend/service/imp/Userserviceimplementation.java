package com.backend.backend.service.imp;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.backend.dto.Adduserdto;
import com.backend.backend.dto.Userdto;
import com.backend.backend.entity.User;
import com.backend.backend.repository.Userepo;
import com.backend.backend.service.Userservice;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Userserviceimplementation implements Userservice {

    private final Userepo userepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Userdto createnewuser(Adduserdto adduserdto) {

        // Check if user already exists
        if (userepo.findByEmail(adduserdto.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists with this email");
        }

        // Hash password
        String hashedPassword =
                passwordEncoder.encode(adduserdto.getPassword());

        // Create new user
        User newUser = new User();
        newUser.setName(adduserdto.getName());
        newUser.setEmail(adduserdto.getEmail());
        newUser.setNumber(adduserdto.getNumber());
        newUser.setPassword(hashedPassword); // ✅ store hashed password

        // Save user
        User savedUser = userepo.save(newUser);

        // Return DTO WITHOUT password
        return new Userdto(
                 savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getNumber()
        );
    }
}
