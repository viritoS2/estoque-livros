package com.exemplo.estoque.livros.demo.controller;

import com.exemplo.estoque.livros.demo.dto.DadosDeCadastroUser;
import com.exemplo.estoque.livros.demo.dto.User;
import com.exemplo.estoque.livros.demo.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Transactional
@ResponseBody
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @GetMapping
    public ResponseEntity<String> getUsers(){
        try{
            List<User> listaDeUsers = userRepository.findAll();
            String json = gson.toJson(listaDeUsers);
            return ResponseEntity.ok(json);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/user")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getUserById(@RequestParam Long id) {
        try{
            Optional<User> userOptional = userRepository.findById(id);
            User user = userOptional.orElse(null);
            if( user == null){  return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Esse usuário não está cadastrado");}
            else {
                String json = gson.toJson(user);
                return ResponseEntity.ok(json);
            }
        } catch (Exception e  ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e);
        }
    }

    @DeleteMapping("/user")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUserById(@RequestParam Long id) {
        try{
            if(userRepository.existsById(id)){
                userRepository.deleteById(id);
            return ResponseEntity.ok("Usuario deletado com sucesso");}
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Esse usuário não está cadastrado");
            }

        } catch (Exception e  ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e);
        }
    }

    @PostMapping
    @RequestMapping("user/cadastro")
    public ResponseEntity<String> cadastraUser(@RequestBody DadosDeCadastroUser dados){
        try{
            User newUser = new User(dados);
            userRepository.save(newUser);
            return ResponseEntity.ok("Cadastrado com sucesso");
        } catch (Exception e){
            System.out.println(e);
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
