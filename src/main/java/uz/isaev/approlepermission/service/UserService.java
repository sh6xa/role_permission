package uz.isaev.approlepermission.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.isaev.approlepermission.entity.Role;
import uz.isaev.approlepermission.entity.User;
import uz.isaev.approlepermission.payload.RegisterDto;
import uz.isaev.approlepermission.payload.UserDto;
import uz.isaev.approlepermission.repository.UserRepository;
import uz.isaev.approlepermission.response.ApiResponse;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ApiResponse add(UserDto userDto) {
            if (userRepository.existsByUsername(userDto.getUsername()))
                return new ApiResponse("Username already exists!", false);

            ApiResponse response = roleService.getById(userDto.getRoleId());
            if (!response.isStatus())
                return response;

             User user = new User(userDto.getFullName(), userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()), (Role) response.getObject(), userDto.isEnabled());

             userRepository.save(user);
             return new ApiResponse("User added!", true);
    }

    public ApiResponse edit(UserDto userDto, Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            return new ApiResponse("User not found!", false);
        }

        if (userRepository.existsByUsernameAndIdNot(userDto.getUsername(), id))
                return new ApiResponse("Username already exists!", false);

            ApiResponse response = roleService.getById(userDto.getRoleId());
            if (!response.isStatus())
                return response;

             User user = userOptional.get();

             user.setFullName(userDto.getFullName());
             user.setUsername(userDto.getUsername());
             user.setEnabled(userDto.isEnabled());
             user.setRole((Role) response.getObject());
             user.setPassword(passwordEncoder.encode(userDto.getPassword()));

             userRepository.save(user);
             return new ApiResponse("User edited!", true);
    }



    public ApiResponse editMyProfile(RegisterDto registerDto) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userRepository.existsByUsernameAndIdNot(registerDto.getUsername(), user.getId()))
            return new ApiResponse("Username already exists!",false);

        if (!registerDto.getPassword().equals(registerDto.getPrePassword()))
            return new ApiResponse("Passwords are not compatible!",false);



        user.setFullName(registerDto.getFullName());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        userRepository.save(user);
        return new ApiResponse("User saved!", true);
    }

    public ApiResponse delete(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            return new ApiResponse("User not found!", false);
        }

        userRepository.deleteById(id);
        return new ApiResponse("User deleted!", true);
    }

    public ApiResponse getById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(user -> new ApiResponse("User by id!", true, user)).orElseGet(() -> new ApiResponse("User not found!", false));
    }

    public ApiResponse getAll() {
        return new ApiResponse("List User", true, userRepository.findAll());
    }
}
