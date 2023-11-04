package com.pjt.triptravel.board.service;

import java.util.List;

import com.pjt.triptravel.board.dto.post.PostCreateParam;
import com.pjt.triptravel.board.dto.post.PostSearchCondition;
import com.pjt.triptravel.board.dto.post.PostSearchResult;
import com.pjt.triptravel.board.dto.post.PostUpdateParam;
import com.pjt.triptravel.board.entity.Board;
import com.pjt.triptravel.board.entity.Post;
import com.pjt.triptravel.board.repository.BoardRepository;
import com.pjt.triptravel.board.repository.PostRepository;
import com.pjt.triptravel.board.repository.query.PostQueryRepository;
import com.pjt.triptravel.common.exception.UserNotFoundException;
import com.pjt.triptravel.member.entity.Member;
import com.pjt.triptravel.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final PostQueryRepository postQueryRepository;

    public Page<PostSearchResult> search(PostSearchCondition condition, Pageable pageable) {
        return postQueryRepository.query(condition, pageable);
    }

    @Transactional
    public Long posting(Long writerId, PostCreateParam param) {
        log.info("게시글 작성 writer={}, boardId={}", writerId, param.getBoardId());
        Member member = memberRepository.findById(writerId)
                .orElseThrow(UserNotFoundException::new);
        Board board = boardRepository.findById(param.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시판 번호입니다."));

        Post post = Post.builder()
                .title(param.getTitle())
                .content(param.getContent())
                .board(board)
                .writer(member)
                .build();
        postRepository.save(post);
        return post.getId();
    }

    @Transactional
    public void update(Long postId, Long writerId, PostUpdateParam param) {
        Post post = postRepository.findByIdAndWriterId(postId, writerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 번호입니다."));
        post.changeTitle(param.getTitle());
        post.changeContent(param.getContent());
    }

    @Transactional
    public void delete(Long postId, Long writerId) {
        Post post = postRepository.findByIdAndWriterId(postId, writerId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 번호입니다."));
        postRepository.delete(post);
    }
}
