package com.twozo.app.controller;

import com.twozo.app.model.Dto.ResponseDto;
import com.twozo.app.model.Dto.UserDto;
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
        final boolean valid = userInputValidator.validateRegister(user);

        if(!valid){
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("invalid register details try again"));
        }

        final int result = userService.checkUserRegistration(user);

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
         final UserDto user = new UserDto();
         user.setUserId(userDto.getId());
         user.setPassword(userDto.getPassword());
        final boolean valid = userInputValidator.validatePasswordUpdate(user);

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
        final UserDto user = new UserDto();
        user.setPhNo(userDto.getPhNo());
        user.setName(userDto.getName());

        final boolean valid = userInputValidator.validateUserDetailUpdates(user);

        if(!valid){
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("invalid details try again"));
        }

        final boolean result = userService.updateUserName(user);

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
        final boolean valid = userInputValidator.validateUserDetailUpdates(user);

        if(!valid){
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("invalid details try again"));
        }

        final boolean result = userService.updateUserPhNo(user);

        if(result){
            return ResponseEntity.ok(new ResponseDto("User Phone No updated successfully"));
        }

        return  ResponseEntity.badRequest()
                .body(new ResponseDto("This phone Number is already registered try another"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteUser(@RequestBody UserDto userDto) {
        final UserDto user = new UserDto();
        user.setPhNo(userDto.getPhNo());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());

        final boolean valid = userInputValidator.validateUserDelete(userDto);

        if(!valid){
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("invalid user details try again"));
        }

        final boolean result = userService.deleteUser(user);

        if(result){
            return ResponseEntity.ok(new ResponseDto("User deleted successfully"));
        }

        return ResponseEntity.badRequest()
                .body(new ResponseDto("User Details not found"));
    }

    @GetMapping("/getUserData")
    public ResponseEntity<?> getUserData(@RequestParam String userInfo) {
        final boolean valid = userInputValidator.validateUserDetail(userInfo);

        if(!valid){
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("invalid user detail try again"));
        }

        final UserDto userDto = userService.getUser(userInfo);

        if(userDto != null) {
            return ResponseEntity.ok(new UserDto(userDto.getId(),userDto.getPhNo(),userDto.getName()));
        }

        return ResponseEntity.badRequest()
                .body(new ResponseDto("no users found with this details"));
    }

    @GetMapping("/getUserId")
    public ResponseEntity<ResponseDto> getUserId(@RequestParam String userDetail) {
        final boolean valid = userInputValidator.validateUserDetail(userDetail);

        if(!valid){
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("invalid user details"));
        }

        final int userId = userService.getUserId(userDetail);

        if (userId > 0) {
            return ResponseEntity.ok(new ResponseDto("userId :"+userId));
        }

        return ResponseEntity.badRequest()
                    .body(new ResponseDto("No Users found with this details"));

    }
}

