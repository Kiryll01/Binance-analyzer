package com.example.binanceAnalyzer.Models.Abstract;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@MappedSuperclass
public abstract class AbstractUserProperties implements Serializable {
    @Nullable
    //@JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "role")
    String role;

}
