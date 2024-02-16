package com.wondoo.memberservice.point.repository;

import com.wondoo.memberservice.point.domain.PointReceipt;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PointReceiptRepository extends MongoRepository<PointReceipt, String> {
}
