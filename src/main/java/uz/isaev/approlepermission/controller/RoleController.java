package uz.isaev.approlepermission.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.isaev.approlepermission.aop.CheckPermission;
import uz.isaev.approlepermission.payload.RoleDto;
import uz.isaev.approlepermission.response.ApiResponse;
import uz.isaev.approlepermission.service.RoleService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    final
    RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @CheckPermission(value = "ADD_ROLE")
    @PostMapping()
    private ResponseEntity<?> add(@Valid @RequestBody RoleDto roleDto){
        ApiResponse apiResponse = roleService.add(roleDto);
        return ResponseEntity.status(apiResponse.isStatus()? HttpStatus.OK:HttpStatus.CONFLICT).body(apiResponse);
    }

    @CheckPermission(value = "EDIT_ROLE")
    @PutMapping("/{id}")
    private ResponseEntity<?> edit(@Valid @RequestBody RoleDto roleDto, @PathVariable Long id){
        ApiResponse apiResponse = roleService.edit(roleDto, id);
        return ResponseEntity.status(apiResponse.isStatus()? HttpStatus.OK:HttpStatus.CONFLICT).body(apiResponse);
    }

    @CheckPermission(value = "DELETE_ROLE")
    @PutMapping("/d/{id}")
    private ResponseEntity<?> delete(@PathVariable Long id){
        ApiResponse apiResponse = roleService.delete(id);
        return ResponseEntity.status(apiResponse.isStatus()? HttpStatus.OK:HttpStatus.CONFLICT).body(apiResponse);
    }

    @CheckPermission(value = "VIEW_ROLE")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        ApiResponse apiResponse = roleService.getById(id);
        return ResponseEntity.status(apiResponse.isStatus()? HttpStatus.OK:HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @CheckPermission(value = "VIEW_ROLE")
    @GetMapping()
    public ResponseEntity<?> getAll(){
        ApiResponse apiResponse = roleService.getAll();
        return ResponseEntity.status(apiResponse.isStatus()? HttpStatus.OK:HttpStatus.NOT_FOUND).body(apiResponse);
    }

}
