package com.twozo.app.controller;

import com.twozo.app.model.dto.ResponseDto;
import com.twozo.app.model.dto.UserDto;
import com.twozo.app.model.User;
import com.twozo.app.service.UserService;
import com.twozo.app.validation.UserInputValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserInputValidator userInputValidator;

    public UserController(final UserService userService,final UserInputValidator userInputValidator) {
        this.userService = userService;
        this.userInputValidator = userInputValidator;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@RequestBody User user) {
        final int valid = userInputValidator.validateRegister(user);

        if(valid == 1){
            return ResponseEntity.badRequest().body(new ResponseDto("Invalid! User name should  not empty or not greater than 20 characters"));
        }

        else if(valid == 2){
            return ResponseEntity.badRequest().body(new ResponseDto("Invalid! Phone number must contains 10 positive digits and starts with(6 to 9)"));
        }

        else if(valid  == 3){
            return ResponseEntity.badRequest().body(new ResponseDto("Invalid! Password must contains minimum 8 digits"));
        }

        final int result = userService.checkUserRegistration(user);

        if(result == 1){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDto("This User name already taken, try another"));
        }

        else if(result == 2){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDto("This phone number already taken, try another"));
        }

        return  ResponseEntity.ok(new ResponseDto("User Registered Successfully"));

    }

    @PutMapping("/updatePassword")
    public ResponseEntity<ResponseDto> updatePassword(@RequestBody UserDto userDto) {
        final int updateType = 3;
        final int valid = userInputValidator.validatePasswordUpdate(userDto);

        if(valid == 1){
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("Invalid! id must be greater than zero"));
        }

        else if(valid == 2){
            return ResponseEntity.badRequest().body(new ResponseDto("Invalid! Password must contains minimum 8 digits"));
        }

        boolean result = userService.updatePassword(userDto,updateType);

        if(result){
            return ResponseEntity.ok(new ResponseDto("password updated successfully"));
        }

        return ResponseEntity.badRequest()
                .body(new ResponseDto("user id not found"));
    }

    @PutMapping("/updateName")
    public ResponseEntity<ResponseDto> updateName(@RequestBody UserDto userDto) {
        final int updateType = 1;
        final int valid = userInputValidator.validateUserDetailUpdates(userDto);

        if(valid == 1){
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("invalid! Username is not empty or user name not greater than 20 characters"));
        }

        else if(valid == 2){
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("Invalid! Phone number must contains 10 positive digits and starts with(6 to 9)"));
        }

        final boolean result = userService.updateUserName(userDto,updateType);

        if(result){
            return ResponseEntity.ok(new ResponseDto("User Name updated successfully"));
        }

        return ResponseEntity.badRequest()
                .body(new ResponseDto("This user name is already taken try another"));
    }

    @PutMapping("/updatePhone")
    public ResponseEntity<ResponseDto> updatePhone(@RequestBody UserDto userDto) {
        final int updateType = 2;
        final int valid = userInputValidator.validateUserDetailUpdates(userDto);

        if(valid == 1){
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("invalid! Username is not empty or user name not greater than 20 characters"));
        }

        else if(valid == 2){
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("Invalid! Phone number must contains 10 positive digits and starts with(6 to 9)"));
        }

        final boolean result = userService.updateUserPhNo(userDto,updateType);

        if(result){
            return ResponseEntity.ok(new ResponseDto("User Phone No updated successfully"));
        }

        return  ResponseEntity.badRequest()
                .body(new ResponseDto("This phone Number is already registered try another"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteUser(@RequestBody UserDto userDto) {
        final int valid = userInputValidator.validateUserDelete(userDto);

        if(valid == 1){
            return ResponseEntity.badRequest().body(new ResponseDto("Invalid! User name should be not empty or not greater than 20 characters"));
        }

        else if(valid == 2){
            return ResponseEntity.badRequest().body(new ResponseDto("Invalid! Phone number must contains 10 positive digits and starts with(6 to 9)"));
        }

        else if(valid  == 3){
            return ResponseEntity.badRequest().body(new ResponseDto("Invalid! Password must contains minimum 8 digits"));
        }

        final boolean result = userService.deleteUser(userDto);

        if(result){
            return ResponseEntity.ok(new ResponseDto("User deleted successfully"));
        }

        return ResponseEntity.badRequest()
                .body(new ResponseDto("User Details not found"));
    }

    @GetMapping("/getUserData")
    public ResponseEntity<?> getUserData(@RequestParam String phNo) {
        final boolean valid = userInputValidator.validateUserDetail(phNo);

        if(!valid){
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("invalid! phone number must contains 10 positive digits and starts with(6 to 9)"));
        }

        final UserDto userDto = userService.getUser(phNo);

        if(userDto != null) {
            return ResponseEntity.ok(new UserDto(userDto.getId(),userDto.getPhNo(),userDto.getName()));
        }

        return ResponseEntity.badRequest()
                .body(new ResponseDto("no users found with this phone number"));
    }

    @GetMapping("/getUserId")
    public ResponseEntity<ResponseDto> getUserId(@RequestParam String phNo) {
        final boolean valid = userInputValidator.validateUserDetail(phNo);

        if(!valid){
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("invalid! phone number must contains 10 positive digits and starts with(6 to 9)"));
        }

        final int userId = userService.getUserId(phNo);

        if (userId > 0) {
            return ResponseEntity.ok(new ResponseDto("userId :"+userId));
        }

        return ResponseEntity.badRequest()
                    .body(new ResponseDto("No Users found with this phone number"));

    }
}

