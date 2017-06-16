package com.verint.ui;

import org.junit.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ApplicationTest {

    @Test
    public void shouldConfigureSpringApplicationBuilder() {
        Application application = new Application();
        SpringApplicationBuilder springApplicationBuilder = mock(SpringApplicationBuilder.class);
        application.configure(springApplicationBuilder);
        verify(springApplicationBuilder).sources(Application.class);
    }
}