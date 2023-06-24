package com.example.binanceanalizator.Models.Factories;

import com.example.binanceanalizator.Models.Entities.Embedded.UserSymbolSubscriptionEntity;
import com.example.binanceanalizator.Models.Entities.InMemory.UserSymbolSubscription;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring"
        //,implementationName = "USSMapperService"
)
public interface UserServiceSymbolSubscriptionMapper {
    UserServiceSymbolSubscriptionMapper USER_SYMBOL_SUBSCRIPTION_MAPPER= Mappers.getMapper(UserServiceSymbolSubscriptionMapper.class);
    @InheritInverseConfiguration
    UserSymbolSubscriptionEntity toEntity(UserSymbolSubscription userSymbolSubscription);
    UserSymbolSubscription toDTO(UserSymbolSubscriptionEntity entity);

}
