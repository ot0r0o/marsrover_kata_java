/**
 * Created by Alberto Toro on 25/09/16.
 */
package dev.toro.marsrover.service;

import dev.toro.marsrover.MarsRoverApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = MarsRoverApplication.class)
@TestPropertySource(value = "classpath:application.properties")
public class RemoteServiceTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private String data = "{\"grid\":\"5 5\",\"rovers\":[[\"1 2 N\",\"LMLMLMLMM\"],[\"3 3 E\",\"MMRMMRMRRM\"]]}";

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testWebContextLoaded() throws Exception {
        ResultActions ra = mockMvc.perform(get("/remote").accept(MediaType.TEXT_PLAIN));

        String jsonResponse =
                ra.andReturn().getResponse().getContentAsString();

        Assert.assertTrue(jsonResponse.contains("No data"));
        ra.andExpect(status().isOk());
    }

    @Test
    public void testLoadDataRemotely() throws Exception {
        ResultActions ra = mockMvc.perform(post("/remote")
                .content(data).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_PLAIN));

        String jsonResponse =
                ra.andReturn().getResponse().getContentAsString();

        Assert.assertTrue(jsonResponse.contains("1 3 N 5 1 E "));
        ra.andExpect(status().isOk());
    }
}
