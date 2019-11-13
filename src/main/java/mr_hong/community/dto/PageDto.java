package mr_hong.community.dto;


import java.util.ArrayList;
import java.util.List;

public class PageDto {
    private List<QuestionDto> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer currentPage;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }


    public List<QuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions;
    }

    public boolean isShowPrevious() {
        return showPrevious;
    }

    public void setShowPrevious(boolean showPrevious) {
        this.showPrevious = showPrevious;
    }

    public boolean isShowFirst() {
        return showFirstPage;
    }

    public void setShowFirst(boolean showFirst) {
        this.showFirstPage = showFirst;
    }

    public boolean isShowFirstPage() {
        return showFirstPage;
    }

    public void setShowFirstPage(boolean showFirstPage) {
        this.showFirstPage = showFirstPage;
    }

    public boolean isShowNext() {
        return showNext;
    }

    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
    }

    public boolean isShowEndPage() {
        return showEndPage;
    }

    public void setShowEndPage(boolean showEndPage) {
        this.showEndPage = showEndPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }

    public void setPagination(Integer totalPage, Integer page) {
        this.totalPage = totalPage;
        this.currentPage = page;
        pages.add(page);
        if(page < 1){
            page = 1;
        }
        if(page > totalPage){
            page = totalPage;
        }
        //做上一页和下一页的时候要以当前页为基准，所以需要currentPage

        for(int i=1;i<=3;i++){
            if(page - i >0){
                pages.add(0,page-i); //将page分成0、1两部分，一个从前加，一个从后面加。
            }
            if(page+i <= totalPage){
                pages.add(page+i);
            }
        }

        if(page == 1){
            showPrevious=false;
        }else{
            showPrevious=true;
        }
        if(page == totalPage){
            showNext=false;
        }else{
            showNext=true;
        }
        //是否展示第一页和最后一页，显示符;如果包含第一页或者最后一页
        if(pages.contains(1)){
            showFirstPage = false;
        }else {
            showFirstPage = true;
        }
        if(pages.contains(totalPage)){
            showEndPage = false;
        }else {
            showEndPage = true;
        }
    }
}
