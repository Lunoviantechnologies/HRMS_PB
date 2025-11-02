package com.example.AdimeService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.AdmineEntity.AdmineEntity;
import com.example.AdmineInfo.Admineinfo;
import com.example.Adminerepository.AdmineRepo;

@Service
public class AdmineService implements Admineinfo{

	@Autowired
    private AdmineRepo admineRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;	

    @Override
    public AdmineEntity Admineregister(AdmineEntity adimeregister) {
        return admineRepo.save(adimeregister);
    }

    @Override
    public boolean AdmineLoging(String email, String password) {
        Optional<AdmineEntity> optionalAdmin = admineRepo.findByEmailAndPassword(email, password);
        return optionalAdmin.isPresent();
    }
    @Override
    public AdmineEntity getAdminByEmail(String email) {
        Optional<AdmineEntity> optionalAdmin = admineRepo.findByEmail(email);
        return optionalAdmin.orElse(null);
    }		
}

	
	
	

