package com.atlas.Atlas_Auth_Service.service;


import com.atlas.Atlas_Auth_Service.dto.AuthenticationDTO;
import com.atlas.Atlas_Auth_Service.dto.LoginResponseDTO;
import com.atlas.Atlas_Auth_Service.dto.RegisterDTO;
import com.atlas.Atlas_Auth_Service.dto.UserDetailsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserClient userClient;
    private final PasswordEncoder encoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserClient userClient, PasswordEncoder encoder, TokenService tokenService,
                                 AuthenticationManager authenticationManager) {
        this.userClient = userClient;
        this.encoder = new BCryptPasswordEncoder();
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    /*
      Registro é encaminhado para o user Client
     */
    public void register(RegisterDTO dto) {
        dto = new RegisterDTO(dto.login(), encoder.encode(dto.password()),dto.escola(), dto.role());
        String internalToken = tokenService.generateInternalServiceToken();
        userClient.registerUser(dto, internalToken);
    }

//    public ResponseEntity<AuthenticationDTO> login (AuthenticationDTO dto) {
//           try {
//               UserDetailsDTO user = userClient.findByLogin(dto.login());
//               Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(user.login(), user.password());
//               Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);
//               var token = tokenService.generateToken(user.login(), user.role());
//               System.out.println("LOGIN EFETUADO COM SUCESSO PARA: " + user.login() + " - TOKEN: " + token + " - ROLE: " + user.role() + " -");
//               return ResponseEntity.ok(new AuthenticationDTO(user.login(), token));
//           } catch (Exception e) {
//               System.out.println("ERRO AO FAZER LOGIN " + e.getMessage());
//               return ResponseEntity.status(401).build();
//           }
//    }

    public ResponseEntity<LoginResponseDTO> login (AuthenticationDTO dto) {
        try {
            UserDetailsDTO user = userClient.findByLogin(dto.login());
            if (user == null || !checkPassword(dto.password(), user.password())) {}
        } catch (Exception e) {
            System.out.println("ERRO AO FAZER LOGIN, CREDENCIAIS INVÁLIDAS PARA " + dto.login());
            return ResponseEntity.status(401).build();
        }
        String token = tokenService.generateToken(dto.login(), userClient.findByLogin(dto.login()).role());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }



    public UserDetailsDTO findByLogin(String login) {
        return userClient.findByLogin(login);
    }

    public boolean checkPassword(String raw, String encodedPassword) {
        return encoder.matches(raw, encodedPassword);
    }

}
