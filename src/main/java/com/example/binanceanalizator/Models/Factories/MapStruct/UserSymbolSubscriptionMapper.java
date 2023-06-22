package com.example.binanceanalizator.Models.Factories.MapStruct;

import com.example.binanceanalizator.Models.Entities.Embedded.UserSymbolSubscriptionEntity;
import com.example.binanceanalizator.Models.Entities.InMemory.UserSymbolSubscription;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserSymbolSubscriptionMapper {
    UserSymbolSubscriptionMapper USER_SYMBOL_SUBSCRIPTION_MAPPER= Mappers.getMapper(UserSymbolSubscriptionMapper.class);
    @InheritInverseConfiguration
    UserSymbolSubscriptionEntity toEntity(UserSymbolSubscription userSymbolSubscription);
    UserSymbolSubscription toDTO(UserSymbolSubscriptionEntity entity);

}
