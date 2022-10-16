package com.akgarg.todobackend;

import com.akgarg.todobackend.model.management.AppInfo;
import com.akgarg.todobackend.service.management.ManagementService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Akhilesh Garg
 * @since 16-10-2022
 */
@SpringBootTest
public class ManagementServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManagementServiceTest.class);

    @Autowired
    private ManagementService managementService;

    @Test
    void testManagementServiceApplicationMetricsMethod() {
        assertNotNull(managementService, "Management service can't be null");

        final AppInfo applicationInfo = managementService.getApplicationInfo();
        assertNotNull(applicationInfo, "Application metrics map can't be null");

        LOGGER.info("testManagementService() test successfully completed");
    }

}
