package it.cab.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.cab.data.dto.UserDto;
import it.cab.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public String getUsers(Model model) {
		List<UserDto> users = userService.findAllUsers();
		
		model.addAttribute("users", users);
		
		return "users";
	}
	
	@PostMapping("/abilitadisabilita")
	public String abilitaDisabilita(@RequestParam("id") Long userId) {
		
		
		return "users";
	}
	
}
