package com.sahabuddin.contactmanager.respositories;

import com.sahabuddin.contactmanager.entities.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(@NotBlank(message = "Email is required") String email);
}
