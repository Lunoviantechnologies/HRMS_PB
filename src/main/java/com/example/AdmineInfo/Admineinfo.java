package com.example.AdmineInfo;

import java.util.Optional;

import com.example.AdmineEntity.AdmineEntity;

public interface Admineinfo {

	AdmineEntity getAdminByEmail(String email);	
	AdmineEntity saveAdmin(AdmineEntity admin);
	public AdmineEntity Admineregister(AdmineEntity adimeregister);	
	public boolean AdmineLoging(String email,String password);
}
