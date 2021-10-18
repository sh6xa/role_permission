package uz.isaev.approlepermission.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.isaev.approlepermission.aop.CheckPermission;
import uz.isaev.approlepermission.payload.RegisterDto;
import uz.isaev.approlepermission.payload.UserDto;
import uz.isaev.approlepermission.response.ApiResponse;
import uz.isaev.approlepermission.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @CheckPermission("ADD_USER")
    @PostMapping("/register")
    private ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto){
        ApiResponse apiResponse = userService.add(userDto);
        return ResponseEntity.status(apiResponse.isStatus()? HttpStatus.OK:HttpStatus.CONFLICT).body(apiResponse);
    }

    @CheckPermission("EDIT_USER")
    @PutMapping("/{id}")
    private ResponseEntity<?> edit(@Valid @RequestBody UserDto userDto, @PathVariable Long id){
        ApiResponse apiResponse = userService.edit(userDto,id);
        return ResponseEntity.status(apiResponse.isStatus()? HttpStatus.OK:HttpStatus.CONFLICT).body(apiResponse);
    }

    @CheckPermission("EDIT_MY_PROFILE")
    @PutMapping
    private ResponseEntity<?> editMyProfile(@Valid @RequestBody RegisterDto registerDto){
        ApiResponse apiResponse = userService.editMyProfile(registerDto);
        return ResponseEntity.status(apiResponse.isStatus()? HttpStatus.OK:HttpStatus.CONFLICT).body(apiResponse);
    }

    @CheckPermission("DELETE_USER")
    @DeleteMapping()
    private ResponseEntity<?> delete(@Valid @RequestParam Long id){
        ApiResponse apiResponse = userService.delete(id);
        return ResponseEntity.status(apiResponse.isStatus()? HttpStatus.OK:HttpStatus.CONFLICT).body(apiResponse);
    }

    @CheckPermission("VIEW_USER")
    @GetMapping("/{id}")
    private ResponseEntity<?> getById(@RequestParam Long id){
        ApiResponse apiResponse = userService.getById(id);
        return ResponseEntity.status(apiResponse.isStatus()? HttpStatus.OK:HttpStatus.CONFLICT).body(apiResponse);
    }

    @CheckPermission("VIEW_USER")
    @GetMapping()
    private ResponseEntity<?> getAll(){
        ApiResponse apiResponse = userService.getAll();
        return ResponseEntity.status(apiResponse.isStatus()? HttpStatus.OK:HttpStatus.CONFLICT).body(apiResponse);
    }
}
