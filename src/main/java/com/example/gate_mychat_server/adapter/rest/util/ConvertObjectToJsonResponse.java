package com.example.gate_mychat_server.adapter.rest.util;


import com.example.gate_mychat_server.model.util.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service

public  class ConvertObjectToJsonResponse implements PrepareResultPort {
    private final ObjectMapper objectMapper ;

    public ConvertObjectToJsonResponse(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> Mono<ResponseEntity<String>>  convert (Result<T> response){
        if(response.isError()){
            return Mono.just(ResponseEntity.badRequest().body(response.getError()));
        }else{
            try {
                String registeredUserDataJSON  = objectMapper.writeValueAsString(response.getValue());
                return Mono.just(ResponseEntity.ok(registeredUserDataJSON));
            } catch (JsonProcessingException e) {
                return Mono.error(new RuntimeException("Error"));
            }

        }
    }
}