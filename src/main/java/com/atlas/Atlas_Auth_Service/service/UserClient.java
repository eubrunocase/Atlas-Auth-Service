package com.atlas.Atlas_Auth_Service.service;

import com.atlas.Atlas_Auth_Service.dto.RegisterDTO;
import com.atlas.Atlas_Auth_Service.dto.UserDetailsDTO;
import org.antlr.v4.runtime.Token;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserClient {

    private final RestTemplate restTemplate;
    private final TokenService tokenService;

    public UserClient(RestTemplateBuilder builder, TokenService tokenService) {
        this.restTemplate = builder.build();
        this.tokenService = tokenService;
    }


    public void registerUser(RegisterDTO dto, String token) {
        try {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<RegisterDTO> request = new HttpEntity<>(dto, headers);
        restTemplate.exchange("http://localhost:8082/internal/users", HttpMethod.POST, request, Void.class);
        } catch (HttpClientErrorException e) {
            System.out.println("ERRO AO CHAMAR USER SERVICE " + e.getStatusCode());
            throw new RuntimeException(e);
        }
    }


    public UserDetailsDTO findByLogin(String login) {
        String url = "http://localhost:8082/internal/users/" + login;


        String internalToken = tokenService.generateInternalServiceToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(internalToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<UserDetailsDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    UserDetailsDTO.class
            );
            System.out.println("SUCESSO NO METODO FIND BY LOGIN USER CLIENT: " + response.getBody() + " - TOKEN: " + internalToken + " -");
            return response.getBody();
        } catch (Exception e) {
            System.out.println("ERRO NO METODO FIND BY LOGIN USER CLIENT");
            return null;
        }
    }


}
