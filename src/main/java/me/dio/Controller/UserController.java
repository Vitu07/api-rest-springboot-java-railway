package me.dio.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import me.dio.domain.model.User;
import me.dio.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

   private final UserService userService;

   public UserController(UserService userService){
       this.userService = userService;
   }

    @GetMapping
    @Operation(summary = "Get all users", description = "Return all registered users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Operation successful")
    })
    public ResponseEntity<Iterable<User>> findAll(){
       return ResponseEntity.ok(userService.findAll());
    }

   @GetMapping("/{id}")
   @Operation(summary = "Get user by ID", description = "Return a specific user based on its ID")
   @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "Operation successful"),
           @ApiResponse(responseCode = "404", description = "User not found")
   })
   public ResponseEntity<User> findById(@PathVariable Long id){
       return  ResponseEntity.ok(userService.findById(id));
   }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Create a new user and return this data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid user data provided")
    })
    public ResponseEntity<User> create(@RequestBody User userToCreate){
        var userCreated = userService.create(userToCreate);
        URI location =  ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userCreated.getId())
                .toUri();
        return ResponseEntity.created(location).body(userCreated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Delete an existing user based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<User> delete(@PathVariable Long id){
       userService.delete(id);
       return ResponseEntity.ok().build();
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update a user", description = "Update the user data based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "422", description = "Invalid user data provided")
    })
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User userToUpdate){
       userService.update(id, userToUpdate);
       return ResponseEntity.ok(userToUpdate);
    }

}
