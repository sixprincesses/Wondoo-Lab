package com.wondoo.memberservice.auth.repository;

import com.wondoo.memberservice.auth.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
}
