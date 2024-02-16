package com.wondoo.articleservice.match.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MatchMemberResponse {
    @JsonProperty("similarity")
    private Double similarity;

    @JsonProperty("labels")
    private List<String> labelList;
    @JsonProperty("data1")
    private List<Long> dataList1;
    @JsonProperty("data2")
    private List<Long> dataList2;

    @JsonProperty("study_time1")
    private TimeFormat studyTime1;
    @JsonProperty("study_time2")
    private TimeFormat studyTime2;
    @JsonProperty("study_time_gap")
    private TimeFormat studyTimeGap;
    @JsonProperty("more_than")
    private boolean moreThan;

    @Builder
    public MatchMemberResponse(Double similarity, List<String> labelList, List<Long> dataList1, List<Long> dataList2, TimeFormat studyTime1, TimeFormat studyTime2, TimeFormat studyTimeGap, boolean moreThan) {
        this.similarity = similarity;
        this.labelList = labelList;
        this.dataList1 = dataList1;
        this.dataList2 = dataList2;
        this.studyTime1 = studyTime1;
        this.studyTime2 = studyTime2;
        this.studyTimeGap = studyTimeGap;
        this.moreThan = moreThan;
    }
}