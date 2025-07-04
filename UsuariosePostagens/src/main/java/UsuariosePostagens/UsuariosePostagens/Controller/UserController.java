package UsuariosePostagens.UsuariosePostagens.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import UsuariosePostagens.UsuariosePostagens.Model.UserModel;
import UsuariosePostagens.UsuariosePostagens.Repository.UserRepository;
import UsuariosePostagens.UsuariosePostagens.Service.UserService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/hello")
    public String Hello() {
        return "Hello-word";
    }

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<UserModel> saveUser(@RequestBody UserModel users) {
        UserModel userModel = userService.register(users);
        if (userModel != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @PostMapping("/singup")
    public ResponseEntity<?> singUp(@RequestBody UserModel userM) {
        Optional<UserModel> logar = userRepository.findByEmail(userM.getEmail());
        if (logar.isPresent()) {
            UserModel user = logar.get();

            if (passwordEncoder.matches(userM.getPassword(), user.getPassword())) {
                return ResponseEntity.ok(user);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");

    }
  
}
