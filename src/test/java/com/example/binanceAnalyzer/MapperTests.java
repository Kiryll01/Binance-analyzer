package com.example.binanceAnalyzer;

import com.example.binanceAnalyzer.Models.Dto.UserDto;
import com.example.binanceAnalyzer.Models.Entities.Embedded.User;
import com.example.binanceAnalyzer.Models.Entities.InMemory.MovingAverageProperties;
import com.example.binanceAnalyzer.Models.Entities.InMemory.UserProperties;
import com.example.binanceAnalyzer.Models.Factories.UserServiceMapper;
import com.example.binanceAnalyzer.Models.Plain.Roles;
import com.example.binanceAnalyzer.repos.UsersRepo;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class MapperTests {
private UserDto userDto;
@Autowired
UsersRepo usersRepo;
@BeforeAll
    @Transactional
    void setUp(){
    userDto = UserDto.builder()
            .email("hamilka540@gmail.com")
            .name("kiryll")
            .pass("pass")
            .userProperties(UserProperties.builder()
                    .role(Roles.ADMIN.getValue())
                    .movingAverageProperties(MovingAverageProperties
                    .builder()
                    .shortMillisInterval(1000)
                    .longMillisInterval(5000)
                    .build())
            .build())
            .build();


}
    @Test
    @Transactional
    // test fails because of NPE, that appears in MapperImpl, but mapper is working fine, that is proven in other tests
    public void test(){

    log.info(userDto);

UserServiceMapper.USER_MAPPER.toUser(userDto);

   User userToSave= UserServiceMapper.USER_MAPPER.toUser(userDto);

   log.info(userToSave);

    User userFromDb= usersRepo.findById(userToSave.getId()).get();

   log.info(userFromDb);

   UserDto userAfterMapper= UserServiceMapper.USER_MAPPER.toUserDto(userFromDb);

   log.info(userAfterMapper);

   User mappedUser= UserServiceMapper.USER_MAPPER.toUser(userDto);

   log.info(mappedUser);

   Assertions.assertEquals(userToSave,mappedUser,"must be equal");
}
}
