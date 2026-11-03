package com.czabala.myhome;

import com.czabala.myhome.domain.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("demo")
class DemoApiTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper json;
    @Autowired UserRepository users;

    private String login() throws Exception {
        String body = mvc.perform(post("/auth/login").contentType("application/json")
                .content("{\"email\":\"demo@example.invalid\",\"password\":\"DemoOnly123!\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        JsonNode response = json.readTree(body);
        assertThat(response.path("jwt").asText()).isNotBlank();
        assertThat(body).doesNotContain("password", "DemoOnly123!");
        return response.path("jwt").asText();
    }

    @Test
    void loginKeepsStoredPasswordAndAllowsFamilyCrud() throws Exception {
        String token = login();
        assertThat(users.findByEmail("demo@example.invalid").orElseThrow().getPassword())
                .startsWith("$2");
        login(); // Mapping the response must not erase the stored password.
        String auth = "Bearer " + token;
        String created = mvc.perform(post("/family").header("Authorization", auth)
                .contentType("application/json").content("{\"name\":\"Demo household\",\"description\":\"Fictional data\"}"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        long id = json.readTree(created).path("id").asLong();
        assertThat(id).isPositive();
        mvc.perform(get("/family/id").param("id", String.valueOf(id)).header("Authorization", auth))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Demo household"));
        mvc.perform(put("/family").header("Authorization", auth).contentType("application/json")
                .content("{\"id\":" + id + ",\"name\":\"Updated household\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Updated household"));
        mvc.perform(delete("/family").param("id", String.valueOf(id)).header("Authorization", auth))
                .andExpect(status().isOk());
        mvc.perform(get("/family/id").param("id", String.valueOf(id)).header("Authorization", auth))
                .andExpect(status().isNotFound());
    }

    @Test
    void rejectsWrongPasswordAndAnonymousRequests() throws Exception {
        mvc.perform(post("/auth/login").contentType("application/json")
                .content("{\"email\":\"demo@example.invalid\",\"password\":\"Incorrect123!\"}"))
                .andExpect(status().isUnauthorized());
        mvc.perform(get("/family")).andExpect(status().isForbidden());
        mvc.perform(get("/family").header("Authorization", "Bearer invalid.token.value"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createsReadsUpdatesAndDeletesTask() throws Exception {
        String auth = "Bearer " + login();
        long userId = users.findByEmail("demo@example.invalid").orElseThrow().getId();
        String payload = "{\"name\":\"Local task\",\"color\":\"#1976d2\",\"date\":\"2026-01-01T00:00:00Z\","
                + "\"startHour\":\"2026-01-01T10:00:00Z\",\"endHour\":\"2026-01-01T11:00:00Z\","
                + "\"createdAt\":\"2026-01-01T00:00:00Z\",\"createdBy\":" + userId + ",\"user\":" + userId + "}";
        String body = mvc.perform(post("/task").header("Authorization", auth)
                .contentType("application/json").content(payload))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        assertThat(body).doesNotContain("password");
        long id = json.readTree(body).path("id").asLong();
        assertThat(id).isPositive();
        mvc.perform(get("/task/user").param("id", String.valueOf(userId)).header("Authorization", auth))
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].name").value("Local task"));
        mvc.perform(put("/task").header("Authorization", auth).contentType("application/json")
                .content(payload.replace("Local task", "Updated task").replaceFirst("\\{", "{\"id\":" + id + ",")))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Updated task"));
        mvc.perform(delete("/task").param("id", String.valueOf(id)).header("Authorization", auth))
                .andExpect(status().isOk());
        mvc.perform(get("/task/id").param("id", String.valueOf(id)).header("Authorization", auth))
                .andExpect(status().isNotFound());
    }

    @Test
    @org.springframework.transaction.annotation.Transactional
    void serializationDoesNotExposeOrErasePassword() throws Exception {
        var user = users.findByEmail("demo@example.invalid").orElseThrow();
        String encoded = user.getPassword();
        assertThat(json.writeValueAsString(user)).doesNotContain("password", encoded);
        assertThat(json.writeValueAsString(user.toDTO())).doesNotContain("password", encoded);
        assertThat(user.getPassword()).isEqualTo(encoded);
    }

    @Test
    void deletingOwnedFamilyKeepsItsUsers() throws Exception {
        String auth = "Bearer " + login();
        long userId = users.findByEmail("demo@example.invalid").orElseThrow().getId();
        String body = mvc.perform(post("/family").header("Authorization", auth).contentType("application/json")
                .content("{\"name\":\"Owned group\",\"userId\":" + userId + "}"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        long id = json.readTree(body).path("id").asLong();
        mvc.perform(delete("/family").param("id", String.valueOf(id)).header("Authorization", auth))
                .andExpect(status().isOk());
        assertThat(users.findById(userId)).isPresent();
        login();
    }

    @Test
    void registrationConfirmationAndRecoveryWorkWithCapturedLocalEmail() throws Exception {
        String email = "signup" + java.util.UUID.randomUUID().toString().substring(0, 8) + "@example.invalid";
        mvc.perform(post("/auth/register").contentType("application/json")
                .content("{\"email\":\"" + email + "\",\"password\":\"Example123!\",\"firstName\":\"Test\",\"lastName\":\"User\",\"birthDate\":\"2000-01-01\"}"))
                .andExpect(status().isCreated());
        assertThat(users.findByEmail(email).orElseThrow().isConfirmed()).isFalse();
        String token = capturedToken(email, "Confirmación");
        mvc.perform(put("/auth/confirm").contentType("application/json").content(json.writeValueAsString(java.util.Map.of("token", token))))
                .andExpect(status().isOk());
        mvc.perform(get("/auth/recovery-password/start/" + email)).andExpect(status().isOk());
        String recovery = capturedToken(email, "Cambio de contraseña");
        mvc.perform(post("/auth/recovery-password/change").header("Authorization", "Bearer " + recovery)
                .contentType("application/json").content("{\"email\":\"" + email + "\",\"password\":\"Updated123!\"}"))
                .andExpect(status().isOk());
        mvc.perform(post("/auth/login").contentType("application/json")
                .content("{\"email\":\"" + email + "\",\"password\":\"Updated123!\"}"))
                .andExpect(status().isOk());
    }

    private String capturedToken(String email, String subject) throws Exception {
        try (var files = java.nio.file.Files.list(java.nio.file.Path.of(".runtime/mail"))) {
            String message = files.map(path -> {
                try { return java.nio.file.Files.readString(path); }
                catch (java.io.IOException ex) { throw new java.io.UncheckedIOException(ex); }
            }).filter(text -> text.contains("To: " + email) && text.contains("Subject: " + subject)).findFirst().orElseThrow();
            String encoded = message.substring(message.indexOf("?token=") + 7).trim();
            return new String(java.util.Base64.getUrlDecoder().decode(encoded), java.nio.charset.StandardCharsets.UTF_8);
        }
    }
}
