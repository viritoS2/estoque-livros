package com.exemplo.estoque.livros.demo.domain;

import com.exemplo.estoque.livros.demo.domain.user.DadosDeCadastroUser;
import com.exemplo.estoque.livros.demo.domain.user.User;
import com.exemplo.estoque.livros.demo.domain.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller("/cadastro/user")
@Transactional
public class UserController {

    UserRepository userRepository;

    @GetMapping("/users/{id}")
    @ResponseBody
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long id) {
        try{
            Optional<User> user = userRepository.findById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public void cadastraUser(@RequestBody DadosDeCadastroUser dados){
        try{
            var newUser = new User(dados);
            userRepository.save(newUser);
            ResponseEntity.ok(newUser);
        } catch (Exception e){
            System.out.println(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu algum erro"));
        }
    }
}
