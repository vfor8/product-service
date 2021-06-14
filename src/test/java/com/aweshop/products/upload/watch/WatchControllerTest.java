package com.aweshop.products.upload.watch;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = WatchController.class)
@AutoConfigureMockMvc
@Import({ WatchMapperImpl.class })
class WatchControllerTest {

    private final static String IMG_BASE64 = "R01GOD1hAQABAIAAAAUEBA";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WatchService service;

    private static byte[] decode(String base64str) {
        return Base64.getDecoder().decode(base64str);
    }

    @Test
    void whenPost_createWatch() throws Exception {
        var newWatch = WatchDto.builder()
                .title("Prim").price(250000).description("A watch with a water fountain picture").fountain(IMG_BASE64).build();

        mockMvc.perform(post("/watches")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newWatch)))
                .andExpect(status().isCreated());

        var captor = ArgumentCaptor.forClass(WatchEntity.class);
        verify(service).createWatch(captor.capture());

        WatchEntity watch = captor.getValue();
        assertThat(watch.getTitle()).isEqualTo("Prim");
        assertThat(watch.getPrice()).isEqualTo(250000);
        assertThat(watch.getDescription()).isEqualTo("A watch with a water fountain picture");
        assertThat(watch.getFountain()).isEqualTo(decode(IMG_BASE64));
    }

    @Test
    void whenPostWithPriceAsString_createWatch() throws Exception {
        String json = "{\n"
                + "    \"title\": \"Prim\","
                + "    \"price\": \"250000\","
                + "    \"description\": \"Watch description\","
                + "    \"fountain\": \"" + IMG_BASE64 + "\""
                + "}";

        mockMvc.perform(post("/watches")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());

        var captor = ArgumentCaptor.forClass(WatchEntity.class);
        verify(service).createWatch(captor.capture());

        WatchEntity watch = captor.getValue();
        assertThat(watch.getTitle()).isEqualTo("Prim");
        assertThat(watch.getPrice()).isEqualTo(250000);
        assertThat(watch.getDescription()).isEqualTo("Watch description");
        assertThat(watch.getFountain()).isEqualTo(decode(IMG_BASE64));

    }

    @Test
    void whenPostWithEmptyBody_returnBadRequest() throws Exception {
        mockMvc.perform(post("/watches")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPostWithNullTitle_returnBadRequest() throws Exception {
        // Just a sample test to check that the validator is invoked and returns correct code.
        // Other constraints tested in WatchValidationTest

        var newWatch = WatchDto.builder().title(null).price(250000).description("A watch with a water fountain picture").fountain(IMG_BASE64).build();

        mockMvc.perform(post("/watches")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newWatch)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPostWithInvalidBase64image_returnBadRequest() throws Exception {
        var newWatch = WatchDto.builder()
                .title("Prim").price(250000).description("A watch with a water fountain picture").fountain("invalid_image").build();

        MvcResult mvcResult = mockMvc.perform(post("/watches")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newWatch)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();

        assertThat(responseBody).contains("Invalid base64 provided as a fountain image");
    }

    @Test
    void whenRequestContentTypeXml_createWatch() throws Exception {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<Watch>"
                + "<title>Prim</title>"
                + "<price>250000</price>"
                + "<description>A watch description</description>"
                + "<fountain>" + IMG_BASE64 + "</fountain>"
                + "</Watch>";

        mockMvc.perform(post("/watches")
                .contentType(MediaType.APPLICATION_XML)
                .content(xml))
                .andExpect(status().isCreated());

        var captor = ArgumentCaptor.forClass(WatchEntity.class);
        verify(service).createWatch(captor.capture());

        WatchEntity receivedWatch = captor.getValue();
        assertThat(receivedWatch.getTitle()).isEqualTo("Prim");
        assertThat(receivedWatch.getPrice()).isEqualTo(250000);
        assertThat(receivedWatch.getDescription()).isEqualTo("A watch description");
        assertThat(receivedWatch.getFountain()).isEqualTo(decode(IMG_BASE64));
    }
}
