package com.example.AdimeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.AdmineEntity.AdmineEntity;
import com.example.Adminerepository.AdmineRepo;

@Service
public class AdmineService {

    @Autowired
    private AdmineRepo admineRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Optional helper methods (if needed later)

    public AdmineEntity registerAdmin(AdmineEntity admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return admineRepo.save(admin);
    }

    public boolean validateLogin(String email, String password) {
        AdmineEntity admin = admineRepo.findByEmail(email).orElse(null);
        return admin != null && passwordEncoder.matches(password, admin.getPassword());
    }
}
