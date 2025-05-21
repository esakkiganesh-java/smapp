package com.twozo.smapp.controller;

import com.twozo.smapp.model.ApiResponse;
import com.twozo.smapp.model.User;
import com.twozo.smapp.service.UserService;
import com.twozo.smapp.validation.UserValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.Collection;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;

    public UserController(final UserService userService,final UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> add(@RequestBody User user) {
        final int validationType = 1;
        final Collection<String> errors = userValidator.validate(user,validationType);

        if(!errors.isEmpty()){
            return ResponseEntity.badRequest().body(new ApiResponse(errors.toString()));
        }

        final int result = userService.add(user);

        if(result == 1){
            return ResponseEntity.ok().body(new ApiResponse("This User name is already taken, try another"));
        } else if(result == 2){
            return ResponseEntity.badRequest().body(new ApiResponse("This phone number is already registered, try another"));
        }

        return  ResponseEntity.ok(new ApiResponse("User Registered Successfully"));

    }

    @PutMapping("/updatePassword")
    public ResponseEntity<ApiResponse> updatePassword(@RequestBody User user) {
        final int updateType = 3;
        final int validationType = 3;
        final Collection<String> errors = userValidator.validate(user,validationType);

        if(!errors.isEmpty()){
            return ResponseEntity.badRequest().body(new ApiResponse(errors.toString()));
        }

        final boolean result = userService.update(user,updateType);

        if(result){
            return ResponseEntity.ok(new ApiResponse("password updated successfully"));
        }

        return ResponseEntity.badRequest()
                .body(new ApiResponse("user id not found"));
    }

    @PutMapping("/updateName")
    public ResponseEntity<ApiResponse> updateName(@RequestBody User user) {
        final int updateType = 1;
        final int validationType = 2;
        final Collection<String> errors = userValidator.validate(user,validationType);

        if(!errors.isEmpty()){
            return ResponseEntity.badRequest().body(new ApiResponse(errors.toString()));
        }

        final boolean result = userService.update(user,updateType);

        if(result){
            return ResponseEntity.ok(new ApiResponse("User Name updated successfully"));
        }

        return ResponseEntity.badRequest()
                .body(new ApiResponse("This user name is already taken try another"));
    }

    @PutMapping("/updatePhone")
    public ResponseEntity<ApiResponse> updatePhone(@RequestBody User user) {
        final int updateType = 2;
        final int validationType = 2;
        final Collection<String> errors = userValidator.validate(user,validationType);

        if(!errors.isEmpty()){
            return ResponseEntity.badRequest().body(new ApiResponse(errors.toString()));
        }

        final boolean result = userService.update(user,updateType);

        if(result){
            return ResponseEntity.ok(new ApiResponse("User Phone No updated successfully"));
        }

        return  ResponseEntity.badRequest()
                .body(new ApiResponse("This phone Number is already registered try another"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> delete(@RequestBody User user) {
        final int validationType = 1;
        final Collection<String> errors = userValidator.validate(user,validationType);

        if(!errors.isEmpty()){
            return ResponseEntity.badRequest().body(new ApiResponse(errors.toString()));
        }

        final boolean result = userService.delete(user);

        if(result){
            return ResponseEntity.ok(new ApiResponse("User deleted successfully"));
        }

        return ResponseEntity.badRequest()
                .body(new ApiResponse("User Details not found"));
    }

    @GetMapping("/getUserData")
    public ResponseEntity<?> getUser(@RequestBody User user) {
        final int validationType = 4;
        final Collection<String> errors = userValidator.validate(user,validationType);

        if(!errors.isEmpty()){
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(errors.toString()));
        }

        final User userData = userService.getUser(user.getPhNo());

        if(userData != null) {
            return ResponseEntity.ok(new User(userData.getId(),userData.getPhNo(),userData.getName()));
        }

        return ResponseEntity.badRequest()
                .body(new ApiResponse("no users found with this phone number"));
    }

    @GetMapping("/getUserId")
    public ResponseEntity<ApiResponse> getUserId(@RequestBody User user) {
        final int validationType = 4;
        final Collection<String> errors = userValidator.validate(user,validationType);

        if(!errors.isEmpty()){
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(errors.toString()));
        }

        final int userId = userService.getUserId(user.getPhNo());

        if (userId > 0) {
            return ResponseEntity.ok(new ApiResponse("userId : "+userId));
        }

        return ResponseEntity.badRequest()
                    .body(new ApiResponse("No Users found with this phone number"));

    }
}

