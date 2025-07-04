package UsuariosePostagens.UsuariosePostagens.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import UsuariosePostagens.UsuariosePostagens.Model.UserModel;
import UsuariosePostagens.UsuariosePostagens.Repository.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public UserModel register(UserModel users) {
    try {
      String username = users.getUsername();
      String email = users.getEmail();
      String password = users.getPassword();
      if (username.toLowerCase().isEmpty() || email.toLowerCase().isEmpty() || password.isEmpty()) {
        throw new Error("Campo inválido");
      } else if (userRepository.existsByEmail(email)) {
        throw new Error("Usuário já cadastrado");
      } else {

        String hash = passwordEncoder.encode(users.getPassword());

        users.setPassword(hash);
        return userRepository.save(users);

      }

    } catch (Exception e) {
      throw new Error("Error" + e.getMessage());
    }
  }

  public UserModel Login(UserModel users) {
   Optional<UserModel> user = userRepository.findByEmail(users.getEmail());
   if(user.isPresent()){
    UserModel userDB = user.get();
    if(passwordEncoder.matches(users.getPassword(), userDB.getPassword())){
      return userDB;
    }
   }
   return null;
  }
}
