package com.example.post.util;

import lombok.Data;

@Data
public class PageNavigator {
    //페이지 관련 정보
    private int countPerPage;		//페이지당 글목록 수
    private int pagePerGroup;		//그룹당 페이지 수
    private int currentPage;		//현재 페이지 (최근 글이 1부터 시작)
    private long totalRecordsCount;	//전체 글 수
    private int totalPageCount;		//전체 페이지 수
    private int currentGroup;		//현재 그룹 (최근 그룹이 0부터 시작)
    private int startPageGroup;		//현재 그룹의 첫 페이지
    private int endPageGroup;		//현재 그룹의 마지막 페이지
    private int startRecord;		//전체 레코드 중 현재 페이지 첫 글의 위치 (0부터 시작)

    // 생성자
    public PageNavigator(int countPerPage, int pagePerGroup, int totalPageCount, int currentPage, long totalRecordsCount) {
        // 페이지 관련 정보 설정
        this.countPerPage = countPerPage;
        this.pagePerGroup = pagePerGroup;
        this.totalPageCount = totalPageCount;
        this.currentPage = currentPage;
        this.totalRecordsCount = totalRecordsCount;

        // 현재 페이지가 속한 페이지 그룹 계산
        currentGroup = currentPage / pagePerGroup;
        startPageGroup = currentGroup * pagePerGroup;
        endPageGroup = Math.max(Math.min(startPageGroup + pagePerGroup - 1, totalPageCount - 1), 0);
    }
}
