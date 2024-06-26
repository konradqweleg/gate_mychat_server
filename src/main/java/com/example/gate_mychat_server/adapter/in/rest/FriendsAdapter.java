package com.example.gate_mychat_server.adapter.in.rest;

import com.example.gate_mychat_server.adapter.in.rest.util.ConvertToJSON;
import com.example.gate_mychat_server.model.request.FriendsIdsData;
import com.example.gate_mychat_server.model.request.IdUserData;
import com.example.gate_mychat_server.port.in.FriendUseCase;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import jakarta.validation.Valid;
@RequestMapping(value = "/api/v1/friends")
@RestController
public class FriendsAdapter {
    private final FriendUseCase friendUseCase;


    public FriendsAdapter(FriendUseCase friendUseCase) {
        this.friendUseCase = friendUseCase;
    }

    @GetMapping(value ="/getUserFriends", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ACCESS_TOKEN')")
    public Mono<ResponseEntity<String>> getLastMessagesWithAllFriends(@RequestParam @Valid Long idUser) {
        return ConvertToJSON.convert(friendUseCase.getUserFriends(Mono.just(new IdUserData(idUser))));
    }

    @PostMapping(value ="/createFriends", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ACCESS_TOKEN')")
    public Mono<ResponseEntity<String>> createFriends(@RequestBody @Valid Mono<FriendsIdsData> friendsIdsData) {
        return friendUseCase.createFriends(friendsIdsData).flatMap(ConvertToJSON::convert);
    }
}
