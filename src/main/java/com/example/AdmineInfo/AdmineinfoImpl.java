package com.example.AdmineInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.AdmineEntity.AdmineEntity;
import com.example.Adminerepository.AdmineRepo;

@Service
public class AdmineinfoImpl implements Admineinfo {

    @Autowired
    private AdmineRepo admineRepo;

    @Override
    public AdmineEntity getAdminByEmail(String email) {
        return admineRepo.findByEmail(email).orElse(null);
    }

    @Override
    public AdmineEntity saveAdmin(AdmineEntity admin) {
        return admineRepo.save(admin);
    }

    @Override
    public AdmineEntity Admineregister(AdmineEntity adimeregister) {
        return admineRepo.save(adimeregister);
    }

    @Override
    public boolean AdmineLoging(String email, String password) {
        AdmineEntity admin = admineRepo.findByEmail(email).orElse(null);
        return admin != null && password.equals(admin.getPassword()); // testing only
    }
}
