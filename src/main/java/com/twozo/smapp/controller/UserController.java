package com.twozo.smapp.controller;

import com.twozo.smapp.model.ApiResponse;
import com.twozo.smapp.model.User;
import com.twozo.smapp.service.UserService;
import com.twozo.smapp.validation.UserValidator;
import com.twozo.smapp.validation.ValidationType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;

    public UserController(final UserService userService, final UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> add(@RequestBody final User user) {
        final Collection<String> errors = userValidator.validate(user, ValidationType.ADD);

        if(!errors.isEmpty()){
            return ResponseEntity.badRequest().body(new ApiResponse(errors.toString()));
        }

        userService.add(user);

        return  ResponseEntity.ok(new ApiResponse("User Registered successfully!"));

    }

    @PutMapping("/updatePassword")
    public ResponseEntity<ApiResponse> updatePassword(@RequestBody final User user) {
        final String updateType = "password";
        final Collection<String> errors = userValidator.validate(user,ValidationType.UPDATE_PASSWORD);

        if(!errors.isEmpty()){
            return ResponseEntity.badRequest().body(new ApiResponse(errors.toString()));
        }

        userService.update(user,updateType);

        return ResponseEntity.ok().body(new ApiResponse("Password updated successfully"));
    }

    @PutMapping("/updateName")
    public ResponseEntity<ApiResponse> updateName(@RequestBody final User user) {
        final String updateType = "name";
        final Collection<String> errors = userValidator.validate(user,ValidationType.UPDATE);

        if(!errors.isEmpty()){
            return ResponseEntity.badRequest().body(new ApiResponse(errors.toString()));
        }

        userService.update(user,updateType);

        return ResponseEntity.ok().body(new ApiResponse("User name updated successfully!"));
    }

    @PutMapping("/updatePhone")
    public ResponseEntity<ApiResponse> updatePhone(@RequestBody final User user) {
        final String updateType = "phone";
        final Collection<String> errors = userValidator.validate(user,ValidationType.UPDATE);

        if(!errors.isEmpty()){
            return ResponseEntity.badRequest().body(new ApiResponse(errors.toString()));
        }

        userService.update(user,updateType);

        return  ResponseEntity.ok().body(new ApiResponse("Phone No updated successfully!"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> delete(@RequestBody final User user) {
        final Collection<String> errors = userValidator.validate(user,ValidationType.ADD);

        if(!errors.isEmpty()){
            return ResponseEntity.badRequest().body(new ApiResponse(errors.toString()));
        }

        userService.delete(user);

        return ResponseEntity.ok().body(new ApiResponse("User details deleted successfully!"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getUser(@RequestBody final User user) {
        final Collection<String> errors = userValidator.validate(user,ValidationType.GET_USER);

        if(!errors.isEmpty()){
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(errors.toString()));
        }

        final User userData = userService.getUser(user.getPhone());

        if(userData != null) {
            return ResponseEntity.ok(new User(userData.getId(),userData.getPhone(),userData.getName()));
        }

        return ResponseEntity.badRequest()
                .body(new ApiResponse("no users found with this phone number"));
    }

    @GetMapping("/getUserId")
    public ResponseEntity<ApiResponse> getUserId(@RequestBody final User user) {
        final Collection<String> errors = userValidator.validate(user,ValidationType.GET_USER);

        if(!errors.isEmpty()){
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(errors.toString()));
        }

        final int userId = userService.getUserId(user.getPhone());

        if (userId > 0) {
            return ResponseEntity.ok(new ApiResponse("userId : "+userId));
        }

        return ResponseEntity.badRequest()
                    .body(new ApiResponse("No Users found with this phone number"));
    }

    @PostMapping("/addToFavourites")
    public ResponseEntity<ApiResponse> addFavourites(@RequestParam final int userId, @RequestParam final int otherUserId){
        final User user = new User();
        user.setId(userId);
        Collection<String> errors = userValidator.validate(user,ValidationType.CHECK_ID);
        user.setId(otherUserId);

        if(!userValidator.validate(user,ValidationType.CHECK_ID).isEmpty()){
            errors.add(userValidator.validate(user,ValidationType.CHECK_ID).toString());
        }

        if(!errors.isEmpty()){
            return ResponseEntity.badRequest().body(new ApiResponse(errors.toString()));
        }

        final String addedToFavourites = userService.addToFavourites(userId,otherUserId);

        return ResponseEntity.ok().body(new ApiResponse(addedToFavourites));
    }

    @PostMapping("/removeFromFavourites")
    public ResponseEntity<ApiResponse> removeFromFavourites(@RequestParam final int userId, @RequestParam final int otherUserId){
        final User user = new User();
        user.setId(userId);
        Collection<String> errors = userValidator.validate(user,ValidationType.CHECK_ID);
        user.setId(otherUserId);

        if(!userValidator.validate(user,ValidationType.CHECK_ID).isEmpty()){
            errors.add(userValidator.validate(user,ValidationType.CHECK_ID).toString());
        }

        if(!errors.isEmpty()){
            return ResponseEntity.badRequest().body(new ApiResponse(errors.toString()));
        }

        final String removedFromFavourites = userService.removeFromFavourites(userId,otherUserId);

        return ResponseEntity.ok().body(new ApiResponse(removedFromFavourites));
    }
}

