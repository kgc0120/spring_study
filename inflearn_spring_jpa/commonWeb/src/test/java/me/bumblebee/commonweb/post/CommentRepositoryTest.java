package me.bumblebee.commonweb.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    public void getComment() {

        Post post = new Post();
        post.setTitle("jpa");
        Post savedPost = postRepository.save(post);

        Comment comment = new Comment();
        comment.setCommnet("spring data jpa projection");
        comment.setPost(savedPost);
        comment.setUp(10);
        comment.setDown(1);
        commentRepository.save(comment);

        commentRepository.findByPostId(savedPost.getId(), CommentOnly.class).forEach(c -> {
            System.out.println("===============");
            System.out.println(c.getId());
        });
    }

    @Test
    void spexs() {
        Page<Comment> page = commentRepository
                .findAll(CommentSpecs.isBest().or(CommentSpecs.isGood()), PageRequest.of(0, 10));
    }
}