package com.example.binanceanalizator;

import com.example.binanceanalizator.Models.Dto.UserDto;
import com.example.binanceanalizator.Models.Entities.Embedded.User;
import com.example.binanceanalizator.Models.Entities.InMemory.MovingAverageProperties;
import com.example.binanceanalizator.Models.Entities.InMemory.UserProperties;
import com.example.binanceanalizator.Models.Factories.UserServiceMapper;
import com.example.binanceanalizator.Models.Roles;
import com.example.binanceanalizator.repos.UsersRepo;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

//@DataJpaTest
@Log4j2
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MapperTests {
private UserDto userDto;
//@Autowired
//UserServiceMapper userMapper;
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
