package com.czabala.myhome;

import com.czabala.myhome.domain.model.dao.User;
import com.czabala.myhome.domain.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("demo")
@Transactional
class OwnershipApiTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper json;
    @Autowired UserRepository users;

    private User newUser() {
        User u = new User();
        u.setEmail(UUID.randomUUID() + "@example.invalid");
        u.setFirstName("Fictional"); u.setLastName("User");
        u.setBirthDate(java.sql.Date.valueOf("2000-01-01"));
        u.setPassword("Example123!"); u.initializeUser(); u.setConfirmed(true);
        return users.save(u);
    }
    private String auth(User u) throws Exception {
        var body = mvc.perform(post("/auth/login").contentType("application/json")
                .content(json.writeValueAsString(Map.of("email", u.getEmail(), "password", "Example123!"))))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        return "Bearer " + json.readTree(body).path("jwt").asText();
    }
    private String task(User u, String name) throws Exception {
        return json.writeValueAsString(Map.of("name", name, "user", u.getId(), "createdBy", 999,
                "date", "2026-11-02T00:00:00Z", "startHour", "2026-11-02T10:00:00Z",
                "endHour", "2026-11-02T11:00:00Z"));
    }

    @Test void otherUsersCannotReadEditDeleteOrReassignTasks() throws Exception {
        User owner = newUser(), other = newUser();
        String a = auth(owner), b = auth(other), payload = task(owner, "Private task");
        var created = mvc.perform(post("/task").header("Authorization", a).contentType("application/json").content(payload))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        long id = json.readTree(created).path("id").asLong();
        assertThat(json.readTree(created).path("createdBy").asLong()).isEqualTo(owner.getId());
        mvc.perform(get("/task/id").param("id", "" + id).header("Authorization", b)).andExpect(status().isForbidden());
        mvc.perform(get("/task/user").param("id", "" + owner.getId()).header("Authorization", b)).andExpect(status().isForbidden());
        mvc.perform(get("/task").header("Authorization", b)).andExpect(status().isForbidden());
        String edit = payload.replaceFirst("\\{", "{\"id\":" + id + ",");
        mvc.perform(put("/task").header("Authorization", b).contentType("application/json").content(edit)).andExpect(status().isForbidden());
        mvc.perform(delete("/task").param("id", "" + id).header("Authorization", b)).andExpect(status().isForbidden());
        mvc.perform(post("/task").header("Authorization", b).contentType("application/json").content(payload)).andExpect(status().isForbidden());
        String reassign = edit.replace("\"user\":" + owner.getId(), "\"user\":" + other.getId());
        mvc.perform(put("/task").header("Authorization", a).contentType("application/json").content(reassign))
                .andExpect(status().isForbidden());
        mvc.perform(get("/task/id").param("id", "" + id).header("Authorization", a))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Private task"));
    }

    @Test void familyAndUserInformationStayWithinTheirOwner() throws Exception {
        User owner = newUser(), other = newUser();
        String a = auth(owner), b = auth(other);
        var created = mvc.perform(post("/family").header("Authorization", a).contentType("application/json")
                .content("{\"name\":\"Private family\"}")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        long id = json.readTree(created).path("id").asLong();
        mvc.perform(get("/family/id").param("id", "" + id).header("Authorization", b)).andExpect(status().isForbidden());
        mvc.perform(get("/family").header("Authorization", b)).andExpect(status().isForbidden());
        mvc.perform(put("/family").header("Authorization", b).contentType("application/json")
                .content("{\"id\":" + id + ",\"name\":\"Intruder\"}")).andExpect(status().isForbidden());
        mvc.perform(delete("/family").param("id", "" + id).header("Authorization", b)).andExpect(status().isForbidden());
        mvc.perform(post("/family").header("Authorization", b).contentType("application/json")
                .content("{\"name\":\"Spoofed owner\",\"userId\":" + owner.getId() + "}")).andExpect(status().isForbidden());
        mvc.perform(get("/user/id").param("id", "" + owner.getId()).header("Authorization", b)).andExpect(status().isForbidden());
        mvc.perform(get("/user/email").param("email", owner.getEmail()).header("Authorization", b)).andExpect(status().isForbidden());
        mvc.perform(get("/user").header("Authorization", b)).andExpect(status().isForbidden());
        mvc.perform(put("/user").header("Authorization", b).contentType("application/json")
                .content("{\"id\":" + owner.getId() + ",\"firstName\":\"Intruder\"}")).andExpect(status().isForbidden());
        mvc.perform(delete("/user").param("id", "" + owner.getId()).header("Authorization", b)).andExpect(status().isForbidden());
        mvc.perform(get("/user/id").param("id", "" + other.getId()).header("Authorization", b)).andExpect(status().isOk());
    }

    @Test void invalidTaskTimesReturnClientErrorWithoutCreatingData() throws Exception {
        User owner = newUser();
        mvc.perform(post("/task").header("Authorization", auth(owner)).contentType("application/json")
                .content(task(owner, " ").replace("T11:00:00Z", "T09:00:00Z")))
                .andExpect(status().isBadRequest());
    }
}
