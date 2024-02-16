package com.wondoo.memberservice.member.repository.query;

import com.wondoo.memberservice.member.data.query.MemberSearchQuery;

import java.util.List;

public interface MemberRepositoryCustom {

    List<MemberSearchQuery> memberSearch(String keyword);
}
