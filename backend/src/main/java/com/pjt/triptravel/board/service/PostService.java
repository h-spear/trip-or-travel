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
import org.springframework.dao.EmptyResultDataAccessException;
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

    public Long posting(Long writerId, Long boardId, PostCreateParam param) {
        Member member = memberRepository.findById(writerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원번호입니다."));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시판 번호입니다."));

        Post post = Post.builder()
                .title(param.getTitle())
                .content(param.getContent())
                .board(board)
                .writer(member)
                .build();
        return post.getId();
    }

    public void update(Long postId, PostUpdateParam param) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 번호입니다."));
        post.changeTitle(param.getTitle());
        post.changeContent(param.getContent());
    }

    public void delete(Long postId) {
        try {
            postRepository.deleteById(postId);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("존재하지 않는 게시글 번호입니다.");
        }
    }
}
