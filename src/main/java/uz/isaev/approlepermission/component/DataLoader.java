package uz.isaev.approlepermission.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.isaev.approlepermission.entity.Role;
import uz.isaev.approlepermission.entity.User;
import uz.isaev.approlepermission.enums.Permission;
import uz.isaev.approlepermission.repository.RoleRepository;
import uz.isaev.approlepermission.repository.UserRepository;
import uz.isaev.approlepermission.utils.AppConstants;

import java.util.Arrays;
import java.util.HashSet;

import static uz.isaev.approlepermission.enums.Permission.*;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${spring.datasource.initialization-mode}")
    private String mode;

    @Override
    public void run(String... args) {
        if (mode.equals("always")) {
            Permission[] values = Permission.values();
            Role admin = roleRepository.save(new Role(
                    AppConstants.ADMIN,
                    new HashSet<>(Arrays.asList(values)),
                    "tizim egasi"
            ));

            Role user = roleRepository.save(new Role(
                    AppConstants.USER,
                    new HashSet<>(Arrays.asList(ADD_COMMENT, EDIT_COMMENT, DELETE_MY_COMMENT)),
                    "oddiy foydalanuvchi"
            ));

            userRepository.save(new User(
                    "Admin",
                    "admin",
                    passwordEncoder.encode("admin123"),
                    admin,
                    true
            ));

            userRepository.save(new User(
                    "User",
                    "user",
                    passwordEncoder.encode("user123"),
                    user,
                    true
            ));
        }
    }
}
