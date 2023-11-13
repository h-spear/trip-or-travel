package com.pjt.triptravel.board.repository.query;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import com.pjt.triptravel.board.dto.post.PostSearchCondition;
import com.pjt.triptravel.board.dto.post.PostSearchOrder;
import com.pjt.triptravel.board.dto.post.PostSearchResult;
import com.pjt.triptravel.board.entity.Board;
import com.pjt.triptravel.board.entity.Post;
import com.pjt.triptravel.board.repository.BoardRepository;
import com.pjt.triptravel.board.repository.PostRepository;
import com.pjt.triptravel.member.entity.Member;
import com.pjt.triptravel.member.repository.MemberRepository;

@DataJpaTest
class PostQueryRepositoryTest {

	@Autowired private BoardRepository boardRepository;
	@Autowired private MemberRepository memberRepository;
	@Autowired private PostRepository postRepository;
	@Autowired private TestEntityManager testEntityManager;

	private EntityManager em;
	private PostQueryRepository postQueryRepository;

	private List<Member> members;
	private Long boardId;
	Map<Integer, Long> postIdMap;

	@BeforeEach
	void beforeEach() {
		em = testEntityManager.getEntityManager();
		postQueryRepository = new PostQueryRepository(em);
		postIdMap = initData();
	}

	@Test
	void queryTest1_페이징() throws Exception {
	    // given
		PostSearchCondition condition = new PostSearchCondition();
		condition.setBoardId(boardId);
		Pageable pageable;

		// when
		pageable = PageRequest.of(0, 4);
		Page<PostSearchResult> result1 = postQueryRepository.query(condition, pageable);

		pageable = PageRequest.of(1, 4);
		Page<PostSearchResult> result2 = postQueryRepository.query(condition, pageable);

		pageable = PageRequest.of(2, 4);
		Page<PostSearchResult> result3 = postQueryRepository.query(condition, pageable);

		// then
		assertThat(result1.getNumber()).isEqualTo(0);
		assertThat(result1.getSize()).isEqualTo(4);
		assertThat(result1.getTotalPages()).isEqualTo(3);
		assertThat(result1.getNumberOfElements()).isEqualTo(4);
		assertThat(result1.isFirst()).isTrue();
		assertThat(result1.hasNext()).isTrue();
		assertThat(result1.isLast()).isFalse();

		assertThat(result2.getNumber()).isEqualTo(1);
		assertThat(result2.getSize()).isEqualTo(4);
		assertThat(result2.getTotalPages()).isEqualTo(3);
		assertThat(result2.getNumberOfElements()).isEqualTo(4);
		assertThat(result2.isFirst()).isFalse();
		assertThat(result2.hasNext()).isTrue();
		assertThat(result2.isLast()).isFalse();

		assertThat(result3.getNumber()).isEqualTo(2);
		assertThat(result3.getSize()).isEqualTo(4);
		assertThat(result3.getTotalPages()).isEqualTo(3);
		assertThat(result3.getNumberOfElements()).isEqualTo(1);
		assertThat(result3.isFirst()).isFalse();
		assertThat(result3.hasNext()).isFalse();
		assertThat(result3.isLast()).isTrue();
	}

	/**
	 * 	 0, 제목: 장미의 이름, 	작성자: writer1,	추천: 10,	조회: 30
	 * 	 1, 제목: 샬롯의 거미줄,	작성자: writer1,	추천: 13,	조회: 33
	 */
	@Test
	void queryTest2_검색조건() throws Exception {
		// given
		PostSearchCondition condition = new PostSearchCondition();
		condition.setBoardId(boardId);
		condition.setWriterNickname("1");
		condition.setTitle("의");
		Pageable pageable = PageRequest.of(0, 4);

		// when
		Page<PostSearchResult> result = postQueryRepository.query(condition, pageable);

		// then
		assertThat(result.getNumber()).isEqualTo(0);
		assertThat(result.getSize()).isEqualTo(4);
		assertThat(result.getTotalPages()).isEqualTo(1);
		assertThat(result.getNumberOfElements()).isEqualTo(2);
		assertThat(result.isFirst()).isTrue();
		assertThat(result.hasNext()).isFalse();
		assertThat(result.isLast()).isTrue();
	}

	/**
	 * 	 5, 제목: 범인 한자와씨,	작성자: writer2,	추천: 6,	조회: 60
	 * 	 3, 제목: 소피의 세계, 	작성자: writer2,	추천: 3,	조회: 50
	 * 	 6, 제목: 허클베리 핀, 	작성자: writer2,	추천: 13,	조회: 40
	 * 	 4, 제목: 바람,			작성자: writer2,	추천: 5,	조회: 30
	 */
	@Test
	void queryTest4_검색조건_정렬조건() throws Exception {
		// given
		PostSearchCondition condition = new PostSearchCondition();
		condition.setBoardId(boardId);
		condition.setWriterNickname("ter2"); // writer2
		condition.setOrder(PostSearchOrder.VIEWS_DESC);
		Pageable pageable = PageRequest.of(0, 10);

		// when
		Page<PostSearchResult> result = postQueryRepository.query(condition, pageable);

		// then
		assertThat(result.getNumber()).isEqualTo(0);
		assertThat(result.getSize()).isEqualTo(10);
		assertThat(result.getTotalPages()).isEqualTo(1);
		assertThat(result.getNumberOfElements()).isEqualTo(4);
		assertThat(result.isFirst()).isTrue();
		assertThat(result.hasNext()).isFalse();
		assertThat(result.isLast()).isTrue();

		List<Long> postIds = result.stream().map(PostSearchResult::getPostId)
			.collect(Collectors.toList());
		assertThat(postIds).containsExactly(
			postIdMap.get(5), postIdMap.get(3), postIdMap.get(6), postIdMap.get(4)
		);
	}

	/**
	 * 	 7, 제목: 데이터베이스, 	작성자: writer3,	추천: 30,	조회: 230
	 * 	 6, 제목: 허클베리 핀, 	작성자: writer2,	추천: 13,	조회: 40
	 * 	 1, 제목: 샬롯의 거미줄,	작성자: writer1,	추천: 13,	조회: 33
	 * 	 0, 제목: 장미의 이름,	작성자: writer1,	추천: 10,	조회: 30
	 * 	 5, 제목: 범인 한자와씨,	작성자: writer2,	추천: 6,	조회: 60
	 * 	 4, 제목: 바람,			작성자: writer2,	추천: 5,	조회: 30
	 * 	 2, 제목: 곰돌이 푸,		작성자: writer1,	추천: 5,	조회: 50
	 * 	 3, 제목: 소피의 세계, 	작성자: writer2,	추천: 3,	조회: 50
	 * 	 8, 제목: 컴퓨터구조론,	작성자: writer3,	추천: 0,	조회: 0
	 */
	@Test
	void queryTest3_정렬조건() throws Exception {
		// given
		PostSearchCondition condition = new PostSearchCondition();
		condition.setBoardId(boardId);
		condition.setOrder(PostSearchOrder.LIKES_DESC);
		Pageable pageable = PageRequest.of(0, 10);

		// when
		Page<PostSearchResult> result = postQueryRepository.query(condition, pageable);

		// then
		assertThat(result.getNumber()).isEqualTo(0);
		assertThat(result.getSize()).isEqualTo(10);
		assertThat(result.getTotalPages()).isEqualTo(1);
		assertThat(result.getNumberOfElements()).isEqualTo(9);
		assertThat(result.isFirst()).isTrue();
		assertThat(result.hasNext()).isFalse();
		assertThat(result.isLast()).isTrue();

		List<Long> postIds = result.stream().map(PostSearchResult::getPostId)
			.collect(Collectors.toList());
		assertThat(postIds).containsExactly(
			postIdMap.get(7), postIdMap.get(6), postIdMap.get(1),
			postIdMap.get(0), postIdMap.get(5), postIdMap.get(4),
			postIdMap.get(2), postIdMap.get(3), postIdMap.get(8)
		);
	}

	/**
	 * 	 0, 제목: 장미의 이름,	작성자: writer1,	추천: 10,	조회: 30
	 * 	 1, 제목: 샬롯의 거미줄,	작성자: writer1,	추천: 13,	조회: 33
	 * 	 2, 제목: 곰돌이 푸,		작성자: writer1,	추천: 5,	조회: 50
	 * 	 3, 제목: 소피의 세계, 	작성자: writer2,	추천: 3,	조회: 50
	 * 	 4, 제목: 바람,			작성자: writer2,	추천: 5,	조회: 30
	 * 	 5, 제목: 범인 한자와씨,	작성자: writer2,	추천: 6,	조회: 60
	 * 	 6, 제목: 허클베리 핀, 	작성자: writer2,	추천: 13,	조회: 40
	 * 	 7, 제목: 데이터베이스, 	작성자: writer3,	추천: 30,	조회: 230
	 * 	 8, 제목: 컴퓨터구조론,	작성자: writer3,	추천: 0,	조회: 0
	 */
	private Map<Integer, Long> initData() {
		members = new ArrayList<>();
		for (int i = 1; i <= 3; ++i) {
			members.add(memberRepository.save(
				Member.builder()
					.email("test" + i + "@test.com")
					.password("password")
					.nickname("writer" + i)
					.build()
			));
		}

		Board board = boardRepository.save(new Board("", ""));
		boardId = board.getId();

		List<Post> posts = new ArrayList<>();
		posts.add(postRepository.save(getDummyPost("장미의 이름", "dummy content...", board, members.get(0), 10, 30)));
		posts.add(postRepository.save(getDummyPost("샬롯의 거미줄", "dummy content...", board, members.get(0), 13, 33)));
		posts.add(postRepository.save(getDummyPost("곰돌이 푸", "dummy content...", board, members.get(0), 5, 50)));
		posts.add(postRepository.save(getDummyPost("소피의 세계", "dummy content...", board, members.get(1), 3, 50)));
		posts.add(postRepository.save(getDummyPost("바람과 함께 사라지다", "dummy content...", board, members.get(1), 5, 30)));
		posts.add(postRepository.save(getDummyPost("범인 한자와씨", "dummy content...", board, members.get(1), 6, 60)));
		posts.add(postRepository.save(getDummyPost("허클베리 핀의 모험", "dummy content...", board, members.get(1), 13, 40)));
		posts.add(postRepository.save(getDummyPost("데이터베이스 배움터", "dummy content...", board, members.get(2), 30, 230)));
		posts.add(postRepository.save(getDummyPost("컴퓨터구조론", "dummy content...", board, members.get(2), 0, 0)));

		Map<Integer, Long> postIdMap = new HashMap<>();
		for (int i = 0; i < 9; ++i) {
			postIdMap.put(i, posts.get(i).getId());
		}
		em.flush();
		em.clear();
		return postIdMap;
	}

	private Post getDummyPost(String title, String content, Board board, Member writer, int likes, int views) {
		Post post = new Post(title, content, board, writer);
		ReflectionTestUtils.setField(post, "likes", likes);
		ReflectionTestUtils.setField(post, "views", views);
		return post;
	}
}