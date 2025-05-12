package com.twozo.demo.controller;

import com.twozo.demo.model.Dto.ResponseDto;
import com.twozo.demo.model.Dto.UserDto;
import com.twozo.demo.model.User;
import com.twozo.demo.service.UserService;
import com.twozo.demo.validation.Validator;
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
    private final Validator validator;

    
    public UserController(UserService userService, Validator validator) {
         this.userService = userService;
        this.validator = validator;
    }


    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@RequestBody User user) {
        boolean valid = validator.validateRegister(user);
        if(!valid){
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("invalid register details try again"));
        }
        int result = userService.checkUserRegistration(user);
        if(result == 3){
            return  ResponseEntity.ok(new ResponseDto("User Registered Successfully"));
        }
        else if(result == 1){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDto("This User name already taken, try another"));
        }
        else if(result == 2){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDto("This phone number already taken, try another"));
        }
        return ResponseEntity.badRequest()
                .body(new ResponseDto("Password must contain minimum 8 digits."));
    }



    @PutMapping("/updatePassword")
    public ResponseEntity<ResponseDto> updatePassword(@RequestBody UserDto userDto) {
       UserDto user = new UserDto();
         user.setUserId(userDto.getId());
         user.setPassword(userDto.getPassword());
        boolean valid = validator.validatePasswordUpdate(user);
        if(!valid){
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("Invalid id or invalid password try again"));
        }
        boolean result = userService.updatePassword(user);
        if(result){
            return ResponseEntity.ok(new ResponseDto("password updated successfully"));
        }
        return ResponseEntity.badRequest()
                .body(new ResponseDto("user id not found"));
    }


    @PutMapping("/updateName")
    public ResponseEntity<ResponseDto> updateName(@RequestBody UserDto userDto) {
        UserDto user = new UserDto();
        user.setPhNo(userDto.getPhNo());
        user.setName(userDto.getName());

        boolean valid = validator.validateUserDetailUpdates(user);
        if(!valid){
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("invalid details try again"));
        }
        boolean result = userService.updateUserName(user);
        if(result){
            return ResponseEntity.ok(new ResponseDto("User Name updated successfully"));
        }
        return ResponseEntity.badRequest()
                .body(new ResponseDto("This user name is already taken try another"));
    }


    @PutMapping("/updatePhone")
    public ResponseEntity<ResponseDto> updatePhone(@RequestBody UserDto userDto) {
        UserDto user = new UserDto();
        user.setPhNo(userDto.getPhNo());
        user.setName(userDto.getName());
        boolean valid = validator.validateUserDetailUpdates(user);
        if(!valid){
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("invalid details try again"));
        }
        boolean result = userService.updateUserPhNo(user);
        if(result){
            return ResponseEntity.ok(new ResponseDto("User Phone No updated successfully"));
        }
        return  ResponseEntity.badRequest()
                .body(new ResponseDto("This phone Number is already registered try another"));
    }


    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteUser(@RequestBody UserDto userDto) {
        UserDto user = new UserDto();
        user.setPhNo(userDto.getPhNo());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());

        boolean valid = validator.validateUserDelete(userDto);
        if(!valid){
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("invalid user details try again"));
        }
        boolean result = userService.deleteUser(user);
        if(result){
            return ResponseEntity.ok(new ResponseDto("User deleted successfully"));
        }
        return ResponseEntity.badRequest()
                .body(new ResponseDto("User Details not found"));
    }


    @GetMapping("/getUserData")
    public ResponseEntity<?> getUserData(@RequestParam String userInfo) {
        boolean valid = validator.validateUserDetail(userInfo);
        if(!valid){
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("invalid user detail try again"));
        }
        UserDto userDto = userService.getUser(userInfo);
        if(userDto != null) {
            return ResponseEntity.ok(new UserDto(userDto.getId(),userDto.getPhNo(),userDto.getName(),userDto.getPassword()));
        }
        return ResponseEntity.badRequest()
                .body(new ResponseDto("no users found with this details"));
    }

    @GetMapping("/getUserId")
    public ResponseEntity<ResponseDto> getUserId(@RequestParam String userDetail) {
        boolean valid = validator.validateUserDetail(userDetail);
        if(!valid){
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("invalid user details"));
        }
        int userId = userService.getUserId(userDetail);
        if (userId > 0) {
            return ResponseEntity.ok(new ResponseDto("userId :"+userId));
        }
        return ResponseEntity.badRequest()
                    .body(new ResponseDto("No Users found with this details"));

    }
}

