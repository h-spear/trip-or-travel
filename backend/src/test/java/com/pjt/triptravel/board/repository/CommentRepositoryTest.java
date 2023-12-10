package com.pjt.triptravel.board.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.pjt.triptravel.board.entity.Board;
import com.pjt.triptravel.board.entity.Comment;
import com.pjt.triptravel.board.entity.Post;
import com.pjt.triptravel.member.entity.Member;
import com.pjt.triptravel.member.repository.MemberRepository;

@DataJpaTest
class CommentRepositoryTest {

	@Autowired BoardRepository boardRepository;
	@Autowired MemberRepository memberRepository;
	@Autowired PostRepository postRepository;
	@Autowired CommentRepository commentRepository;

	private Board testBoard;
	private Member testWriter;
	private Post testPost;

	@BeforeEach
	void beforeEach() {
		testBoard = boardRepository.save(Board.builder().name("test board").build());
		testWriter = memberRepository.save(Member.builder().email("email@test.com").password("pass").build());
		testPost = postRepository.save(new Post("post...", "test...", testBoard, testWriter));
	}

	@AfterEach
	void afterEach() {
		testBoard = null;
		testWriter = null;
		testPost = null;
	}

	@Test
	void findByPostIdWithCommenterTest() throws Exception {
	    // given
		Member commenter1 = memberRepository.save(Member.builder()
			.email("commenter1@test.com")
			.password("pass")
			.nickname("rabbit")
			.build());
		Member commenter2 = memberRepository.save(Member.builder()
			.email("commenter2@test.com")
			.password("pass")
			.nickname("giraffe")
			.build());
		commentRepository.save(Comment.builder().parent(null).comment("comment!!!").commenter(commenter1).post(testPost).build());
		commentRepository.save(Comment.builder().parent(null).comment("comment!!!").commenter(commenter2).post(testPost).build());

	    // when
		Long postId = testPost.getId();
		List<Comment> result = commentRepository.findByPostIdWithCommenter(postId);
		Comment comment1 = result.get(0);
		Comment comment2 = result.get(1);

		// then
		assertThat(result.size()).isEqualTo(2);
		assertThat(comment1.getComment()).isEqualTo("comment!!!");
		assertThat(comment1.getCommenter().getId()).isEqualTo(commenter1.getId());
		assertThat(comment1.getCommenter().getEmail()).isEqualTo("commenter1@test.com");
		assertThat(comment1.getCommenter().getNickname()).isEqualTo("rabbit");
		assertThat(comment2.getComment()).isEqualTo("comment!!!");
		assertThat(comment2.getCommenter().getId()).isEqualTo(commenter2.getId());
		assertThat(comment2.getCommenter().getEmail()).isEqualTo("commenter2@test.com");
		assertThat(comment2.getCommenter().getNickname()).isEqualTo("giraffe");
	}

	@Test
	void findParentTest() throws Exception {
	    // given
		Member commenter = memberRepository.save(Member.builder()
			.email("commenter@test.com")
			.build());
		Comment comment = commentRepository.save(
			Comment.builder().parent(null).comment("parent").commenter(commenter).post(testPost).build());
		Comment reply = commentRepository.save(
			Comment.builder().parent(comment).comment("my").commenter(commenter).post(testPost).build());

		// when
		Long commentId = comment.getId();
		Long replyId = reply.getId();
		Optional<Comment> find1 = commentRepository.findParent(commentId);
		Optional<Comment> find2 = commentRepository.findParent(replyId);

		// then
		assertThat(find1.isPresent()).isFalse();
		assertThat(find2.isPresent()).isTrue();
		assertThat(find2.get().getId()).isEqualTo(comment.getId());
	}

}