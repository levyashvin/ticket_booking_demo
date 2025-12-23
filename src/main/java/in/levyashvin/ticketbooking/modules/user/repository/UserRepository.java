package in.levyashvin.ticketbooking.modules.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import in.levyashvin.ticketbooking.modules.user.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    // logging in via email
    Optional<User> findByEmail(String email);
}
