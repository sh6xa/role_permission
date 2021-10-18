package uz.isaev.approlepermission.service;

import org.springframework.stereotype.Service;
import uz.isaev.approlepermission.entity.Role;
import uz.isaev.approlepermission.payload.RoleDto;
import uz.isaev.approlepermission.repository.RoleRepository;
import uz.isaev.approlepermission.response.ApiResponse;

import java.util.Optional;

@Service
public class RoleService {

    final
    RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public ApiResponse add(RoleDto roleDto) {
        if (roleRepository.existsByName(roleDto.getName()))
            return new ApiResponse("Role already exists!", false);

        Role role =new Role(roleDto.getName(),roleDto.getPermissionSet(), roleDto.getDescription());
        roleRepository.save(role);
        return new ApiResponse("Role saved!", true);
    }

    public ApiResponse edit(RoleDto roleDto, Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            role.setName(roleDto.getName());
            role.setDescription(roleDto.getDescription());
            role.setPermissionSet(roleDto.getPermissionSet());
            roleRepository.save(role);
            return new ApiResponse("Role edited!",true);
        }
        return new ApiResponse("Error! role",false);
    }

    public ApiResponse delete(Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()) {
            roleRepository.deleteById(id);
            return new ApiResponse("Role deleted!", true);
        }
        return new ApiResponse("Error! delete role",false);
    }

    public ApiResponse getById(Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        return optionalRole.map(role -> new ApiResponse("Get By id!", true, role)).orElseGet(() -> new ApiResponse("Not found!", false));
    }

    public ApiResponse getAll(){
        return new ApiResponse("List role!", true, roleRepository.findAll());
    }
}
