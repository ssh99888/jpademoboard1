package org.ssh.jpademo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageResponseDTO<E> {
    private int page;           // 현재페이지
    private int size;           // 블록사이즈
    private int total;          // 전체레코드 수
    private int start;          // 블록의 시작페이지
    private int end;            // 블록의 끝페이지
    private boolean next;       // 다음페이지 여부
    private boolean prev;       // 이전페이지 여부
    private List<E> dtolist;    // 페이징할 dtoList

    // 생성자
    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total) {
        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();
        this.total = total;
        this.dtolist = dtoList;

        this.end = (int)(Math.ceil(this.page/(double)size))*size;
        this.start = this.end-(size-1);
        int last = (int)(Math.ceil(total/(double)size));
        this.end = end>last?last:end;
        this.prev = this.start > 1;
        this.next = this.end < last;
    }
}
