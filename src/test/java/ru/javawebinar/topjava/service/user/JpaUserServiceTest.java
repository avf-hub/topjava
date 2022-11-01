package ru.javawebinar.topjava.service.user;

import org.springframework.test.context.ActiveProfiles;

import static ru.javawebinar.topjava.Profiles.JPA;

@ActiveProfiles(JPA)
public abstract class JpaUserServiceTest extends AbstractUserServiceTest {

}