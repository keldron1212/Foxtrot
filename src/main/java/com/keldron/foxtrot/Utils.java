package com.keldron.foxtrot;

import com.keldron.foxtrot.model.AuthorityRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Utils {
    private static PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public static boolean hasAdminRights(Authentication authentication) {
        return authentication.getAuthorities().contains(AuthorityRole.ADMIN);
    }

    public static String encodePassword(String password) {
        return encoder.encode(password);
    }
}
