package com.exemplo.estoque.livros.demo.controller;

import com.exemplo.estoque.livros.demo.dto.DadosDeCadastroUser;
import com.exemplo.estoque.livros.demo.dto.User;
import com.exemplo.estoque.livros.demo.exceptions.GenericError;
import com.exemplo.estoque.livros.demo.exceptions.runtime.ResourceNotFoundException;
import com.exemplo.estoque.livros.demo.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "User Controller", description = "Controller of User")
@RestController
@RequestMapping("/users")
@Transactional
@ResponseBody
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @GetMapping
    @RequestMapping(method = RequestMethod.GET)
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
    public ResponseEntity<String> getUserById(@RequestParam(name = "id", required = true) Long id) {
        try{
            User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            String json = gson.toJson(user);
            return ResponseEntity.ok(json);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: " + e);
        }   catch (GenericError e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e);
        }
    }

    @DeleteMapping("/user")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUserById(@RequestParam(name = "id", required = true)  Long id) {
        try{
            if(userRepository.existsById(id)){
                userRepository.deleteById(id);
            return ResponseEntity.ok("Usuario deletado com sucesso");}
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Esse usuário não está cadastrado");
            }

        } catch (GenericError e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e);
        }
    }

    @PostMapping
    @RequestMapping(value = "/cadastro", method = RequestMethod.POST)
    public ResponseEntity<String> cadastraUser(@RequestBody DadosDeCadastroUser dados){
        try{
             User newUser = new User(dados);
             userRepository.save(newUser);
            return ResponseEntity.ok("Cadastrado com sucesso");
        } catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<String> updateUser(@RequestParam(name = "id", required = true)  Long id,
                                            @RequestBody DadosDeCadastroUser dadosUser){

            try{
                User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User não cadastrado"));
                user.setEmail(dadosUser.email());
                userRepository.save(user);
            return  ResponseEntity.ok(gson.toJson(user));}

            catch (ResourceNotFoundException e){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: "+ e);
            }
            catch (GenericError e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: "+ e);
            }
    }
}
