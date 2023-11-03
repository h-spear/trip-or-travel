package com.pjt.triptravel.board.service;

import com.pjt.triptravel.board.dto.post.PostCreateParam;
import com.pjt.triptravel.board.dto.post.PostUpdateParam;
import com.pjt.triptravel.board.entity.Board;
import com.pjt.triptravel.board.entity.Post;
import com.pjt.triptravel.board.repository.BoardRepository;
import com.pjt.triptravel.board.repository.PostRepository;
import com.pjt.triptravel.member.entity.Member;
import com.pjt.triptravel.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Transactional
    public Long posting(Long writerId, PostCreateParam param) {
        log.info("게시글 작성 writer={}, boardId={}", writerId, param.getBoardId());
        Member member = memberRepository.findById(writerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원번호입니다."));
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
