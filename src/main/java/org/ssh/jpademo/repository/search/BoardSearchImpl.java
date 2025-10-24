package org.ssh.jpademo.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.ssh.jpademo.domain.Board;
import org.ssh.jpademo.domain.QBoard;

import java.util.List;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {
    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {
        QBoard qboard = QBoard.board;
        JPQLQuery<Board> query = from(qboard);
        BooleanBuilder builder = new BooleanBuilder();
        builder.or(qboard.title.containsIgnoreCase("a"));   // or title like '%1%'
        builder.or(qboard.content.containsIgnoreCase("a")); // or content like '%1%'
        builder.or(qboard.author.containsIgnoreCase("a"));
        query.where(builder);
        query.where(qboard.bno.gt(0));
        this.getQuerydsl().applyPagination(pageable, query);
        List<Board> list = query.fetch();
        long count = query.fetchCount();    // 젠체 레코드 수

        return new PageImpl<Board>(list, pageable, count);
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
        QBoard qboard = QBoard.board;
        JPQLQuery<Board> query = from(qboard);

        if (types != null && types.length > 0 && keyword != null) {
            BooleanBuilder builder = new BooleanBuilder();
            for(String type : types){
                switch (type){
                    case "t":
                        builder.or(qboard.title.containsIgnoreCase(keyword));
                        break;
                    case "c":
                        builder.or(qboard.content.containsIgnoreCase(keyword));
                        break;
                    case "w":
                        builder.or(qboard.author.containsIgnoreCase(keyword));

                }
            }
            query.where(builder);
        }
        query.where(qboard.bno.gt(0));
        this.getQuerydsl().applyPagination(pageable, query);
        List<Board> list = query.fetch();
        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }
}
