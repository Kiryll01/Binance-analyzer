package com.example.binanceanalizator.Models.Entities.Embedded;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


import java.util.Set;

@Entity
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
@Id
@GeneratedValue(strategy = GenerationType.UUID)
String id;
String name;
String pass;
String email;
@OneToOne(cascade = jakarta.persistence.CascadeType.ALL)
@JoinColumn(name = "user_properties_id")
@JsonManagedReference
UserPropertiesEntity userProperties;
@ManyToMany
@Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
Set<UserSymbolSubscriptionEntity> userSymbolSubscriptions;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    public String getEmail() {
        return email;
    }

    public UserPropertiesEntity getUserProperties() {
        return userProperties;
    }

    public Set<UserSymbolSubscriptionEntity> getUserSymbolSubscriptions() {
        return userSymbolSubscriptions;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserProperties(UserPropertiesEntity userProperties) {
        this.userProperties = userProperties;
    }

    public void setUserSymbolSubscriptions(Set<UserSymbolSubscriptionEntity> userSymbolSubscriptions) {
        this.userSymbolSubscriptions = userSymbolSubscriptions;
    }
}
