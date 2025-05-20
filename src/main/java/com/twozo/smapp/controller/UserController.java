package com.twozo.smapp.controller;

import com.twozo.smapp.model.dto.ResponseDto;
import com.twozo.smapp.model.dto.UserDto;
import com.twozo.smapp.model.User;
import com.twozo.smapp.service.UserService;
import com.twozo.smapp.validation.Validator;
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

import java.util.Collection;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final Validator userInputValidator;

    public UserController(final UserService userService,final Validator userInputValidator) {
        this.userService = userService;
        this.userInputValidator = userInputValidator;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@RequestBody User user) {
        final Collection<String> invalidData = userInputValidator.validateRegister(user);

        if(!invalidData.isEmpty()){
            return ResponseEntity.badRequest().body(new ResponseDto(invalidData.toString()));
        }

        final int result = userService.addUser(user);

        if(result == 1){
            return ResponseEntity.ok().body(new ResponseDto("This User name is already taken, try another"));
        } else if(result == 2){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseDto("This phone number is already registered, try another"));
        }

        return  ResponseEntity.ok(new ResponseDto("User Registered Successfully"));

    }

    @PutMapping("/updatePassword")
    public ResponseEntity<ResponseDto> updatePassword(@RequestBody UserDto userDto) {
        final int updateType = 3;
        final Collection<String> invalidData = userInputValidator.validatePasswordUpdate(userDto);

        if(!invalidData.isEmpty()){
            return ResponseEntity.badRequest().body(new ResponseDto(invalidData.toString()));
        }

        final boolean result = userService.update(userDto,updateType);

        if(result){
            return ResponseEntity.ok(new ResponseDto("password updated successfully"));
        }

        return ResponseEntity.badRequest()
                .body(new ResponseDto("user id not found"));
    }

    @PutMapping("/updateName")
    public ResponseEntity<ResponseDto> updateName(@RequestBody UserDto userDto) {
        final int updateType = 1;
        final Collection<String> invalidData = userInputValidator.validateUserDetailUpdates(userDto);

        if(!invalidData.isEmpty()){
            return ResponseEntity.badRequest().body(new ResponseDto(invalidData.toString()));
        }

        final boolean result = userService.update(userDto,updateType);

        if(result){
            return ResponseEntity.ok(new ResponseDto("User Name updated successfully"));
        }

        return ResponseEntity.badRequest()
                .body(new ResponseDto("This user name is already taken try another"));
    }

    @PutMapping("/updatePhone")
    public ResponseEntity<ResponseDto> updatePhone(@RequestBody UserDto userDto) {
        final int updateType = 2;
        final Collection<String> invalidData = userInputValidator.validateUserDetailUpdates(userDto);

        if(!invalidData.isEmpty()){
            return ResponseEntity.badRequest().body(new ResponseDto(invalidData.toString()));
        }

        final boolean result = userService.update(userDto,updateType);

        if(result){
            return ResponseEntity.ok(new ResponseDto("User Phone No updated successfully"));
        }

        return  ResponseEntity.badRequest()
                .body(new ResponseDto("This phone Number is already registered try another"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteUser(@RequestBody UserDto userDto) {
        final Collection<String> invalidData = userInputValidator.validateUserDelete(userDto);

        if(!invalidData.isEmpty()){
            return ResponseEntity.badRequest().body(new ResponseDto(invalidData.toString()));
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
            return ResponseEntity.ok(new ResponseDto("userId : "+userId));
        }

        return ResponseEntity.badRequest()
                    .body(new ResponseDto("No Users found with this phone number"));

    }
}

