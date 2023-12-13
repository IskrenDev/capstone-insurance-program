package com.github.iskrendev.insuranceprogram.models;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document(collection = "users")
public record AppUser(
        @Id
        int id,
        String login
) {
}
