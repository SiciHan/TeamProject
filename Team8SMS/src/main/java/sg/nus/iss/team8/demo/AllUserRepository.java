package sg.nus.iss.team8.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AllUserRepository<AllUser> extends JpaRepository<AllUser,String> {

}
