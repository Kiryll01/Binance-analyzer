package com.example.binanceanalizator;

import com.example.binanceanalizator.Models.Dto.UserDto;
import com.example.binanceanalizator.Models.Entities.Embedded.MovingAveragePropertiesEntity;
import com.example.binanceanalizator.Models.Entities.Embedded.User;
import com.example.binanceanalizator.Models.Entities.Embedded.UserPropertiesEntity;
import com.example.binanceanalizator.Models.Factories.MapStruct.UserMapper;
import com.example.binanceanalizator.Models.Roles;
import com.example.binanceanalizator.repos.UsersRepo;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Log4j2
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MapperTests {
private User userToSave;

@Autowired
    UsersRepo usersRepo;
@BeforeAll
    @Transactional
    void setUp(){
    userToSave= User.builder()
            .email("hamilka540@gmail.com")
            .name("kiryll")
            .pass("pass")
            .userProperties(UserPropertiesEntity.builder()
                    .role(Roles.ADMIN.getValue())
                    .movingAverageProperties(MovingAveragePropertiesEntity
                            .builder()
                            .shortMillisInterval(1000)
                            .longMillisInterval(5000)
                            .build())
                    .build())
            .build();

    usersRepo.save(userToSave);
}
    @Test
    public void test(){
   User user= usersRepo.findById(userToSave.getId()).get();

   log.info(user);

   UserDto userDto= UserMapper.USER_MAPPER.toUserDto(user);

   log.info(userDto);

   User mappedUser= UserMapper.USER_MAPPER.toUser(userDto);

   log.info(mappedUser);

        Assertions.assertEquals(user,mappedUser,"must be equal");
}
}
