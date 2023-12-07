package it.cab.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.cab.data.dto.UserDto;
import it.cab.data.model.User;
import it.cab.service.UserService;

@Controller
@RequestMapping("/")
public class AuthController {
	
	@Autowired private UserService userService;
	
	@GetMapping
	public String getHome(Model model) {
		return "index";
	}
	
	@GetMapping("/login")
    public String getLogin() {
        return "login";
    }
	
	@GetMapping("/signup")
    public String getSignup(Model model) {
		UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "signup";
    }
	
	// handler method to handle user registration form submit request
    @PostMapping("/signup/save")
    public String registration(@Validated @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByUsername(userDto.getUsername());

        if(existingUser != null && existingUser.getUsername() != null && !existingUser.getUsername().isEmpty()){
            result.rejectValue("username", null,
                    "There is already an account registered with the same username");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }
}
