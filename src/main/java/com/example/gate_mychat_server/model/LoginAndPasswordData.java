package com.example.gate_mychat_server.model;

import jakarta.validation.constraints.NotNull;

public record LoginAndPasswordData(@NotNull String login, @NotNull String password) {
}
