package com.example.Notification;
import java.security.Principal;

public class StompPrincipal implements Principal {
    private String name;
    private String email;

    public StompPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}

