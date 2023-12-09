package com.team.travel.dto.order;

import java.util.List;
import java.util.Objects;

public class PageOrderResponse {
    private List<OrderResponse> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;

    public PageOrderResponse(List<OrderResponse> content, int pageNo, int pageSize, long totalElements, int totalPages, boolean last) {
        this.content = content;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }

    public PageOrderResponse() {}

    public List<OrderResponse> getContent() {
        return content;
    }

    public void setContent(List<OrderResponse> content) {
        this.content = content;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageOrderResponse that = (PageOrderResponse) o;
        return pageNo == that.pageNo && pageSize == that.pageSize && totalElements == that.totalElements && totalPages == that.totalPages && last == that.last && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, pageNo, pageSize, totalElements, totalPages, last);
    }

    @Override
    public String toString() {
        return "PageOrderResponse{" +
                "content=" + content +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", totalElements=" + totalElements +
                ", totalPages=" + totalPages +
                ", last=" + last +
                '}';
    }
}
