package com.pjt.triptravel.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pjt.triptravel.board.dto.comment.CommentCreateParam;
import com.pjt.triptravel.board.dto.comment.CommentUpdateParam;
import com.pjt.triptravel.board.entity.Comment;
import com.pjt.triptravel.board.entity.Post;
import com.pjt.triptravel.board.repository.CommentRepository;
import com.pjt.triptravel.board.repository.PostRepository;
import com.pjt.triptravel.common.exception.UserNotFoundException;
import com.pjt.triptravel.member.entity.Member;
import com.pjt.triptravel.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

	private final MemberRepository memberRepository;
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;

	@Transactional
	public Long write(Long commenterId, Long postId, CommentCreateParam param) {
		log.info("댓글 작성 commenterId={}, comment={}", commenterId, param.getComment());
		Member member = memberRepository.findById(commenterId)
			.orElseThrow(UserNotFoundException::new);
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 번호입니다."));
		Comment parent = param.getParentCommentId() != null ?
			commentRepository.findByIdAndPostId(param.getParentCommentId(), postId).orElse(null) : null;

		Comment comment = commentRepository.save(Comment.builder()
			.comment(param.getComment())
			.commenter(member)
			.post(post)
			.parent(parent)
			.build());

		return comment.getId();
	}

	@Transactional
	public void update(Long commenterId, Long commentId, CommentUpdateParam param) {
		Comment comment = commentRepository.findByIdAndCommenterId(commentId, commenterId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글 번호입니다."));
		comment.updateComment(param.getComment());
	}

	@Transactional
	public void delete(Long commenterId, Long commentId) {
		Comment comment = commentRepository.findByIdAndCommenterId(commentId, commenterId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글 번호입니다."));
		commentRepository.delete(comment);
	}
}
