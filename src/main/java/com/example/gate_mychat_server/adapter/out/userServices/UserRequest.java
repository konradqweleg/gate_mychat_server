package com.example.gate_mychat_server.adapter.out.userServices;

import com.example.gate_mychat_server.model.request.*;
import com.example.gate_mychat_server.model.response.MessageResponse;
import com.example.gate_mychat_server.model.response.Status;
import com.example.gate_mychat_server.model.response.UserData;
import com.example.gate_mychat_server.model.util.Result;
import com.example.gate_mychat_server.port.out.UserPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class UserRequest implements UserPort {

    private final URI uriRegister = new URI("http://localhost:8082/userServices/api/v1/user/register");
    private final URI uriResendActiveUserAccountCode = new URI("http://localhost:8082/userServices/api/v1/user/resendActiveUserAccountCode");
    private final URI uriActiveUserAccount = new URI("http://localhost:8082/userServices/api/v1/user/activeUserAccount");

    private final URI uriCheckIfUSerWithThisEmailExists = new URI("http://localhost:8082/userServices/api/v1/user/checkIsUserWithThisEmailExist");

    private final URI uriSendResetPasswordCode = new URI("http://localhost:8082/userServices/api/v1/user/sendResetPasswordCode");

    private final URI uriCheckIsCorrectResetPasswordCode = new URI("http://localhost:8082/userServices/api/v1/user/checkIsCorrectResetPasswordCode");

    private final URI uriChangeUserPassword = new URI("http://localhost:8082/userServices/api/v1/user/resetPassword");

    private final URI uriGetUserAboutEmail = new URI("http://localhost:8082/userServices/api/v1/user/getUserAboutEmail?email=");

    private final URI uriGetUsersMatchingNameSurname = new URI("http://localhost:8082/userServices/api/v1/user/getUserMatchingNameAndSurname?patternName=");

    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserRequest() throws URISyntaxException {
    }




    @Override
    public Mono<Result<Status>> register(Mono<UserRegisterData> userRegisterData) {
        return WebClient.create().post().uri(uriRegister)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(userRegisterData, UserRegisterData.class))
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .toEntity(String.class)
                .flatMap(responseEntity -> {
                    try {
                        System.out.println(responseEntity.getBody());
                        Status status =  objectMapper.readValue(responseEntity.getBody(), Status.class);
                        return Mono.just(Result.success(status));
                    } catch (JsonProcessingException e) {
                        return Mono.error(new RuntimeException(e));
                    }

                })
                .onErrorResume(response -> Mono.just(Result.<Status>error(response.getMessage())));
    }

    @Override
    public Mono<Result<Status>> resendActiveUserAccountCode(Mono<UserEmailData> userEmailDataMono) {
        return WebClient.create().post().uri(uriResendActiveUserAccountCode).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(userEmailDataMono, UserEmailData.class))
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .toEntity(String.class)
                .flatMap(responseEntity -> {
                    try {
                        Status status =  objectMapper.readValue(responseEntity.getBody(), Status.class);
                        return Mono.just(Result.success(status));
                    } catch (JsonProcessingException e) {
                        return Mono.error(new RuntimeException(e));
                    }

                })
                .onErrorResume(response -> Mono.just(Result.<Status>error(response.getMessage())));
    }

    @Override
    public Mono<Result<Status>> activateUserAccount(Mono<ActiveAccountCodeData> activeAccountCodeDataMono) {
        return WebClient.create().post().uri(uriActiveUserAccount).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(activeAccountCodeDataMono, ActiveAccountCodeData.class))
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .toEntity(String.class)
                .flatMap(responseEntity -> {
                    try {
                        Status status =  objectMapper.readValue(responseEntity.getBody(), Status.class);
                        return Mono.just(Result.success(status));
                    } catch (JsonProcessingException e) {
                        return Mono.error(new RuntimeException(e));
                    }
                })
                .onErrorResume(response -> Mono.just(Result.<Status>error(response.getMessage())));
    }

    @Override
    public Mono<Result<Status>> checkIsUserWithThisEmailExists(Mono<UserEmailData> user) {
        return WebClient.create().post().uri(uriCheckIfUSerWithThisEmailExists).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(user, UserEmailData.class))
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .toEntity(String.class)
                .flatMap(responseEntity -> {
                    try {
                        Status status =  objectMapper.readValue(responseEntity.getBody(), Status.class);
                        return Mono.just(Result.success(status));
                    } catch (JsonProcessingException e) {
                        return Mono.error(new RuntimeException(e));
                    }
                })
                .onErrorResume(response -> Mono.just(Result.<Status>error(response.getMessage())));
    }

    @Override
    public Mono<Result<Status>> sendResetPasswordCode(Mono<UserEmailData> emailDataMono) {
        return WebClient.create().post().uri(uriSendResetPasswordCode).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(emailDataMono, UserEmailData.class))
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .toEntity(String.class)
                .flatMap(responseEntity -> {
                    try {
                        Status status =  objectMapper.readValue(responseEntity.getBody(), Status.class);
                        return Mono.just(Result.success(status));
                    } catch (JsonProcessingException e) {
                        return Mono.error(new RuntimeException(e));
                    }
                })
                .onErrorResume(response -> Mono.just(Result.<Status>error(response.getMessage())));
    }

    @Override
    public Mono<Result<Status>> checkIsCorrectResetPasswordCode(Mono<UserEmailAndCodeData> userEmailAndCodeDataMono) {
      return WebClient.create().post().uri(uriCheckIsCorrectResetPasswordCode).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(userEmailAndCodeDataMono, UserEmailAndCodeData.class))
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .toEntity(String.class)
                .flatMap(responseEntity -> {
                    try {
                        Status status =  objectMapper.readValue(responseEntity.getBody(), Status.class);
                        return Mono.just(Result.success(status));
                    } catch (JsonProcessingException e) {
                        return Mono.error(new RuntimeException(e));
                    }
                })
                .onErrorResume(response -> Mono.just(Result.<Status>error(response.getMessage())));
    }

    @Override
    public Mono<Result<Status>> changeUserPassword(Mono<ChangePasswordData> userEmailAndCodeAndPasswordMono) {
        return WebClient.create().post().uri(uriChangeUserPassword).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(userEmailAndCodeAndPasswordMono, ChangePasswordData.class))
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .toEntity(String.class)
                .flatMap(responseEntity -> {
                    try {
                        Status status =  objectMapper.readValue(responseEntity.getBody(), Status.class);
                        return Mono.just(Result.success(status));
                    } catch (JsonProcessingException e) {
                        return Mono.error(new RuntimeException(e));
                    }
                })
                .onErrorResume(response -> Mono.just(Result.<Status>error(response.getMessage())));
    }

    @Override
    public Mono<Result<UserData>> getUserAboutEmail(Mono<UserEmailData> userEmailDataMono) {


        return userEmailDataMono.flatMap(userEmailData -> {
                 return    WebClient.create()
                            .get()
                            .uri(uriGetUserAboutEmail + userEmailData.email().toString())
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .onStatus(
                                    HttpStatus.BAD_REQUEST::equals,
                                    response -> response.bodyToMono(String.class).map(Exception::new)
                            )
                            .toEntity(String.class)
                            .flatMap(responseEntity -> {
                                try {
                                    UserData userData = objectMapper.readValue(responseEntity.getBody(), UserData.class);
                                    return Mono.just(Result.success(userData));
                                } catch (JsonProcessingException e) {
                                    return Mono.error(new RuntimeException(e));
                                }
                            })
                            .onErrorResume(response -> Mono.just(Result.<UserData>error(response.getMessage())));

                }
        );


    }

    @Override
    public Flux<UserData> getUsersMatchingNameSurname(Mono<String> patternNameMono) {
        return patternNameMono.flatMapMany(patternName -> WebClient.create()
                .get()
                .uri(uriGetUsersMatchingNameSurname + patternName)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .bodyToFlux(String.class)
                .flatMap(body -> {
                    try {
                        List<UserData> messageResponse = objectMapper.readValue(body, new TypeReference<List<UserData>>() {});
                        return Flux.fromIterable(messageResponse);
                    } catch (JsonProcessingException e) {
                        return Flux.error(new RuntimeException(e));
                    }
                })
                .onErrorResume(response -> Flux.error(new RuntimeException(response.getMessage()))));
    }
}
