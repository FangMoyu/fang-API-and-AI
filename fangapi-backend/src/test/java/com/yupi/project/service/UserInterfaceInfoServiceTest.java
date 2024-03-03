package com.yupi.project.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class UserInterfaceInfoServiceTest {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Test
    void invokeCount() {
        userInterfaceInfoService.invokeCount(1L,1L);
    }
}