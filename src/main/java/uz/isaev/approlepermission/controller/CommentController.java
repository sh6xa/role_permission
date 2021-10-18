package uz.isaev.approlepermission.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.isaev.approlepermission.aop.CheckPermission;
import uz.isaev.approlepermission.payload.CommentDto;
import uz.isaev.approlepermission.response.ApiResponse;
import uz.isaev.approlepermission.service.CommentService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @CheckPermission("ADD_COMMENT")
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody CommentDto commentDto){
        ApiResponse apiResponse = commentService.add(commentDto);
        return ResponseEntity.status(apiResponse.isStatus()? HttpStatus.OK:HttpStatus.CONFLICT).body(apiResponse);
    }

    @CheckPermission("EDIT_COMMENT")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@Valid @RequestBody CommentDto commentDto, @PathVariable Long id){
        ApiResponse apiResponse = commentService.edit(commentDto, id);
        return ResponseEntity.status(apiResponse.isStatus()? HttpStatus.OK:HttpStatus.CONFLICT).body(apiResponse);
    }

    @CheckPermission("DELETE_MY_COMMENT")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMyComment(@PathVariable Long id){
        ApiResponse apiResponse = commentService.deleteMyComment(id);
        return ResponseEntity.status(apiResponse.isStatus()? HttpStatus.OK:HttpStatus.CONFLICT).body(apiResponse);
    }

    @CheckPermission("DELETE_COMMENT")
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam Long id){
        ApiResponse apiResponse = commentService.delete(id);
        return ResponseEntity.status(apiResponse.isStatus()? HttpStatus.OK:HttpStatus.CONFLICT).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        ApiResponse apiResponse = commentService.getById(id);
        return ResponseEntity.status(apiResponse.isStatus()? HttpStatus.OK:HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        ApiResponse apiResponse = commentService.getAll();
        return ResponseEntity.status(apiResponse.isStatus()? HttpStatus.OK:HttpStatus.NOT_FOUND).body(apiResponse);
    }
}
