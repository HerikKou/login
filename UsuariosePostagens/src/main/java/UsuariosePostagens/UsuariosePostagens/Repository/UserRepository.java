package UsuariosePostagens.UsuariosePostagens.Repository;

// import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import UsuariosePostagens.UsuariosePostagens.Model.UserModel;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserModel , Long> {
    Optional<UserModel> findByEmail(String email);
    boolean existsByEmail(String email);
 
}
