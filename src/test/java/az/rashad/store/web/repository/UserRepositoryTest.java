package az.rashad.store.web.repository;

import az.rashad.store.web.StoreAppWebApplication;
import az.rashad.store.web.TestUtils;
import az.rashad.store.web.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StoreAppWebApplication.class)
public class UserRepositoryTest {


    private MockMvc mockMvc;

    private ArrayList<User> users = new ArrayList<>();

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        userRepository.deleteAll();

        users.add(userRepository.save(new User(null, "user1", "pass1")));
        users.add(userRepository.save(new User(null, "user2", "pass2")));
    }

    @Test
    public void getUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtils.APPLICATION_HAL_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.users", hasSize(2)))
                .andExpect(jsonPath("$._embedded.users.[0].id", is(users.get(0).getId().intValue())))
                .andExpect(jsonPath("$._embedded.users.[0].userName", is(users.get(0).getUserName())))
                .andExpect(jsonPath("$._embedded.users.[0].passWord", is(users.get(0).getPassWord())))
                .andExpect(jsonPath("$._embedded.users.[1].id", is(users.get(1).getId().intValue())))
        ;

    }

    @Test
    public void addUser() throws Exception {
        User user = new User(null, "user3", "pass3");

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(user))
        )
                .andExpect(status().isCreated())
//                .andExpect(content().contentType(TestUtils.APPLICATION_HAL_JSON_UTF8_VALUE))
                .andDo(print())
//                .andExpect(jsonPath("$._embedded.users", hasSize(2)))
        ;
        userRepository.findAll().forEach(a -> System.out.println(a.getId() + a.getUserName()));

    }

    @Test
    public void changeUser() throws Exception {
        User user = new User(users.get(0).getId(), "changedUser", "changedPass");

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(user))
        )
                .andExpect(status().isCreated())
//                .andExpect(content().contentType(TestUtils.APPLICATION_HAL_JSON_UTF8_VALUE))
                .andDo(print())
//                .andExpect(jsonPath("$._embedded.users", hasSize(2)))
        ;
        userRepository.findAll().forEach(a -> System.out.println(a.getId() + a.getUserName()));

    }

    @Test
    public void changeUserWithPut() throws Exception {
        User user = new User(users.get(0).getId(), "changedUser2", "changedPassword2");

        mockMvc.perform(put("/users/" + users.get(0).getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(user))
        )
                .andDo(print())
                .andExpect(status().isNoContent())
        ;

        userRepository.findAll().forEach(a -> System.out.println(a.getId() + a.getUserName()));

    }

    @Test
    public void changeUserName() throws Exception {
        User user = new User(null, "changedUser3", null);

        mockMvc.perform(patch("/users/" + users.get(0).getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(user))
        )
                .andExpect(status().isNoContent())
//                .andExpect(content().contentType(TestUtils.APPLICATION_HAL_JSON_UTF8_VALUE))
                .andDo(print())
//                .andExpect(jsonPath("$._embedded.users", hasSize(2)))
        ;

        userRepository.findAll().forEach(a -> System.out.println(a.getId() + a.getUserName()));

    }

    @Test
    public void deleteUser() throws Exception {
        mockMvc.perform(delete("/users/" + users.get(0).getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isNoContent())
//                .andExpect(content().contentType(TestUtils.APPLICATION_HAL_JSON_UTF8_VALUE))
                .andDo(print())
//                .andExpect(jsonPath("$._embedded.users", hasSize(2)))
        ;

        userRepository.findAll().forEach(a -> System.out.println(a.getId() + a.getUserName()));

    }

}