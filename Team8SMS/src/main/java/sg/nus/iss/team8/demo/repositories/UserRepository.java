package sg.nus.iss.team8.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository<User> extends JpaRepository<User,String> {

}
