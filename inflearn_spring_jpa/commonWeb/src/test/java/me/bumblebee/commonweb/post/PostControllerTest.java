package me.bumblebee.commonweb.post;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostRepository postRepository;

    @Test
    void getPost() throws Exception {
        createPosts();

        mockMvc.perform(get("/posts/1"))
                .andDo(print())
                .andExpect(status().isOk())
            .andExpect(content().string("jpa"));
    }

    @Test
    void getPosts() throws Exception {
        createPosts();

        mockMvc.perform(get("/posts/")
                        .param("page", "3")
                        .param("size", "10")
                        .param("sort", "created,desc")
                        .param("sort", "title"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title", Matchers.is("jpa")));
    }

    private void createPosts() {
        int postsCount = 100;
        while(postsCount > 0 ){
            Post post = new Post();
            post.setTitle("jpa");
            postRepository.save(post);
            postsCount--;
        }
    }

}