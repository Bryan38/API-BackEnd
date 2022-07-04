package fr.ynov.api.repositories;

import fr.ynov.api.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void createUserSuccess() {
        final User user = new User("Pierre", "Pocheron", "pierre.pocheron@gmail.com", "0123456789", "SuperPassword");
        entityManager.persist(user);
        entityManager.flush();

        Optional<User> userFound = userRepository.findByEmail(user.getEmail());

        assertThat(userFound.get().getId()).isGreaterThan(0);
    }


    @Test
    public void getUserByEmailSuccess() {
        final User user = new User("Pierre", "Pocheron", "pierre.pocheron@gmail.com", "0123456789", "SuperPassword");
        entityManager.persist(user);
        entityManager.flush();

        Optional<User> userFound = userRepository.findByEmail(user.getEmail());

        assertThat(userFound).isNotEmpty(); //Should be null
        assertThat(userFound.get().getEmail()).isEqualTo(user.getEmail()); //Should be equals
    }

    @Test
    public void getUserByEmailFailed() {
        final User user = new User("Pierre", "Pocheron", "pierre.pocheron@gmail.com", "0123456789", "SuperPassword");
        entityManager.persist(user);
        entityManager.flush();

        Optional<User> userFound = userRepository.findByEmail("other.email@gmail.com");

        assertThat(userFound).isEmpty(); //Should be null
    }

    @Test
    public void deleteUserSuccess() {
        final User user = new User("Pierre", "Pocheron", "pierre.pocheron@gmail.com", "0123456789", "SuperPassword");
        entityManager.persist(user);
        entityManager.flush();

        userRepository.delete(user);
        Optional<User> userFound = userRepository.findByEmail("pierre.pocheron@gmail.com");

        assertThat(userFound).isEmpty();
    }

    /*
    @Test
    public void getUsersOfEventSuccess() {
        final User user = new User("Pierre", "Pocheron", "pierre.pocheron@gmail.com", "0123456789", "SuperPassword");
        final User user2 = new User("Remy", "Potus", "remy.potus@gmail.com", "0123456789", "SuperPassword");

        final Date dateStart = new Date("2022-07-01");
        final Date dateEnd = new Date("2022-08-31");
        final Event event = new Event(1l, "nameEvent", dateStart, dateEnd, "descriptionEvent",1l);

        List<User> listUsers = event.getListParticipants();
        listUsers.add(user);
        listUsers.add(user2);

        entityManager.persist(user);
        entityManager.persist(event);
        entityManager.flush();


        List<User> userListFound = userRepository.findUsersOfEvent(event.getId());

        assertThat(userListFound).isNotEmpty(); //Should be null
        assertEquals(2, listUsers.stream().count());
    }
     */
}
