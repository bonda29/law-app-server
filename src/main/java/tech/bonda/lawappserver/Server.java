package tech.bonda.lawappserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tech.bonda.lawappserver.models.Role.ERole;
import tech.bonda.lawappserver.models.Role.Role;
import tech.bonda.lawappserver.repository.RoleRepository;

@SpringBootApplication
public class Server {

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }

    @Bean
    public CommandLineRunner initializeRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.count() == 0)
            {
                for (ERole role : ERole.values())
                    roleRepository.save(new Role(role));
            }
        };
    }

}
