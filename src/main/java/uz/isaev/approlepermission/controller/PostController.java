package uz.isaev.approlepermission.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.isaev.approlepermission.aop.CheckPermission;
import uz.isaev.approlepermission.payload.PostDto;
import uz.isaev.approlepermission.response.ApiResponse;
import uz.isaev.approlepermission.service.PostService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/post/")
public class PostController {

    @Autowired
    PostService postService;

    @CheckPermission("ADD_POST")
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody PostDto postDto){
        ApiResponse apiResponse = postService.add(postDto);
        return ResponseEntity.status(apiResponse.isStatus()? HttpStatus.OK:HttpStatus.CONFLICT).body(apiResponse);
    }

    @CheckPermission("EDIT_POST")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@Valid @RequestBody PostDto postDto, @PathVariable Long id){
        ApiResponse apiResponse = postService.edit(postDto, id);
        return ResponseEntity.status(apiResponse.isStatus()? HttpStatus.OK:HttpStatus.CONFLICT).body(apiResponse);
    }

    @CheckPermission("DELETE_POST")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        ApiResponse apiResponse = postService.delete(id);
        return ResponseEntity.status(apiResponse.isStatus()? HttpStatus.OK:HttpStatus.CONFLICT).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getByUrl(@PathVariable Long id){
        ApiResponse apiResponse = postService.getByUrl(id);
        return ResponseEntity.status(apiResponse.isStatus()? HttpStatus.OK:HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        ApiResponse apiResponse = postService.getAll();
        return ResponseEntity.status(apiResponse.isStatus()? HttpStatus.OK:HttpStatus.NOT_FOUND).body(apiResponse);
    }



}
