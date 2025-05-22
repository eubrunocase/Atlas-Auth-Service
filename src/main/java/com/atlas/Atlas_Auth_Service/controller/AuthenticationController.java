package com.atlas.Atlas_Auth_Service.controller;

import com.atlas.Atlas_Auth_Service.dto.AuthenticationDTO;
import com.atlas.Atlas_Auth_Service.dto.LoginResponseDTO;
import com.atlas.Atlas_Auth_Service.dto.RegisterDTO;
import com.atlas.Atlas_Auth_Service.service.AuthenticationService;
import com.atlas.Atlas_Auth_Service.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/atlas/auth")
public class AuthenticationController {

    private final AuthenticationService authService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;



    public AuthenticationController(AuthenticationService authService, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    /*
       Registro é encaminhado para o authentication Service
     */
    @PostMapping("/register")
   public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO dto) {
        authService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
   }


    @PostMapping("/login")
    public ResponseEntity<ResponseEntity<LoginResponseDTO>> login (@RequestBody @Valid AuthenticationDTO dto) {
        System.out.println("RECEBENDO TENTATIVA DE LOGIN PARA: " + dto.login());
        return ResponseEntity.ok(authService.login(dto));
    }

//   @PostMapping("/login")
//   public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid RegisterDTO dto) {
//       UserDetailsDTO user = authService.findByLogin(dto.login());
//       if (user == null || !authService.checkPassword(dto.password(), user.password())) {
//           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//       }
//       String token = tokenService.generateToken(user.login(), user.role());
//       return ResponseEntity.ok(new LoginResponseDTO(token));
//   }



}
