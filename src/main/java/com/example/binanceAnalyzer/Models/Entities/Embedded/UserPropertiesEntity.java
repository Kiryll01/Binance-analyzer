package com.example.binanceAnalyzer.Models.Entities.Embedded;

import com.example.binanceAnalyzer.Models.Abstract.AbstractUserProperties;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserPropertiesEntity extends AbstractUserProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @OneToOne(mappedBy = "userProperties")
    @JsonBackReference
    @ToString.Exclude
    User user;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "map_id")
    @JsonManagedReference
    MovingAveragePropertiesEntity movingAverageProperties;

    public UserPropertiesEntity(String role, String id, User user, MovingAveragePropertiesEntity movingAverageProperties) {
        super(role);
        this.id = id;
        this.user = user;
        this.movingAverageProperties = movingAverageProperties;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public MovingAveragePropertiesEntity getMovingAverageProperties() {
        return movingAverageProperties;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMovingAverageProperties(MovingAveragePropertiesEntity movingAverageProperties) {
        this.movingAverageProperties = movingAverageProperties;
    }

    @Override
    //@Column(name="role")
    public String getRole() {
        return super.getRole();
    }

    @Override
    public void setRole(String role) {
        super.setRole(role);
    }
}
