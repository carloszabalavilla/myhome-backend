package com.czabala.myhome.config.security;

import com.czabala.myhome.domain.model.dao.User;
import com.czabala.myhome.domain.repository.TaskRepository;
import com.czabala.myhome.domain.repository.UserRepository;
import com.czabala.myhome.util.security.Role;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/** Resolve ownership from the authenticated identity, never from client claims. */
@Component("resourceAccess")
public class ResourceAccess {
    private final UserRepository users;
    private final TaskRepository tasks;
    private final com.czabala.myhome.domain.repository.FamilyRepository families;

    public ResourceAccess(UserRepository users, TaskRepository tasks, com.czabala.myhome.domain.repository.FamilyRepository families) {
        this.users = users;
        this.tasks = tasks;
        this.families = families;
    }

    public User current() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return users.findByEmail(email).orElseThrow();
    }

    public boolean admin() { return current().getRole() == Role.ADMINISTRATOR; }
    public boolean user(long id) { return current().getId() == id || admin(); }
    public boolean email(String email) { return current().getEmail().equalsIgnoreCase(email) || admin(); }
    public boolean task(long id) {
        var task = tasks.findById(id).orElseThrow();
        return task.getUser() != null && user(task.getUser().getId());
    }
    public boolean family(long id) {
        if (!families.existsById(id)) throw new java.util.NoSuchElementException("Grupo no encontrado");
        User user = current();
        return admin() || (user.getFamily() != null && user.getFamily().getId() == id);
    }
    public boolean familyOwner(long id) {
        return admin() || (family(id) && current().getFamilyRole() == User.FamilyRole.HEAD);
    }
    public boolean newFamily(long userId) {
        return (userId == 0 || user(userId)) && current().getFamily() == null;
    }
}
