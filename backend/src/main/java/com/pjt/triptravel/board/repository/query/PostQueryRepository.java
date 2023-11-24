package com.pjt.triptravel.board.repository.query;

import static com.pjt.triptravel.board.entity.QBoard.*;
import static com.pjt.triptravel.board.entity.QComment.*;
import static com.pjt.triptravel.board.entity.QPost.*;
import static com.pjt.triptravel.member.entity.QMember.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.EntityManager;

import com.pjt.triptravel.board.entity.QBoard;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.NumberPath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.pjt.triptravel.board.dto.post.PostSearchCondition;
import com.pjt.triptravel.board.dto.post.PostSearchOrder;
import com.pjt.triptravel.board.dto.post.PostSearchResult;
import com.pjt.triptravel.board.dto.post.QPostSearchResult;
import com.pjt.triptravel.board.entity.Post;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class PostQueryRepository {

	private final JPAQueryFactory query;

	public PostQueryRepository(EntityManager em) {
		this.query = new JPAQueryFactory(em);
	}

	public Page<PostSearchResult> query(PostSearchCondition condition, Pageable pageable) {
		List<PostSearchResult> result = query.select(new QPostSearchResult(
				board.id,
				post.id,
				post.title,
				comment1.count().intValue(),
				post.views,
				post.likes,
				post.writer.id,
				post.writer.nickname,
				post.writer.profileImageUrl,
				post.registrationDate,
				post.lastModifiedDate))
			.from(post)
			.join(post.board, board)
			.join(post.writer, member)
			.leftJoin(post.comments, comment1)
			.where(boardMatch(board.id, condition.getBoardId()),
				stringContains(post.title, condition.getTitle()),
				stringContains(post.writer.nickname, condition.getWriterNickname()),
				dateAfter(condition.getStartDate()),
				dateBefore(condition.getEndDate()))
			.groupBy(post)
			.orderBy(getOrderSpecifier(condition.getOrder()),
				post.id.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Post> countQuery = query
			.select(post)
			.from(post)
			.join(post.writer, member)
			.join(post.board, board)
			.leftJoin(post.comments, comment1)
			.where(boardMatch(board.id, condition.getBoardId()),
					stringContains(post.title, condition.getTitle()),
					stringContains(post.writer.nickname, condition.getWriterNickname()),
					dateAfter(condition.getStartDate()),
					dateBefore(condition.getEndDate()))
			.groupBy(post);

		return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchCount);
	}

	private BooleanExpression boardMatch(NumberPath<Long> boardId, Long boardIdCondition) {
		return boardIdCondition != null ? boardId.eq(boardIdCondition) : null;
	}

	private BooleanExpression dateAfter(LocalDate date) {
		return date != null ? post.registrationDate.after(LocalDateTime.of(date, LocalTime.MIN)) : null;
	}

	private BooleanExpression dateBefore(LocalDate date) {
		return date != null ? post.registrationDate.before(LocalDateTime.of(date, LocalTime.MAX)) : null;
	}

	private BooleanExpression stringContains(StringExpression expression, String pattern) {
		if (expression == null || !StringUtils.hasText(pattern)) {
			return null;
		}
		return expression.lower().contains(pattern.toLowerCase());
	}

	private OrderSpecifier<?> getOrderSpecifier(PostSearchOrder order) {
		if (order == PostSearchOrder.COMMENT_DESC) {
			return comment1.count().desc();
		} else if (order == PostSearchOrder.VIEWS_DESC) {
			return post.views.desc();
		} else if (order == PostSearchOrder.LIKES_DESC) {
			return post.likes.desc();
		} else if (order == PostSearchOrder.OLDEST) {
			return post.registrationDate.asc();
		} else { //  order == PostSearchOrder.NEWEST
			return post.registrationDate.desc();
		}
	}
}
