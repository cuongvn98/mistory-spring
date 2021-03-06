package com.ltm2019.mistory.repository;

import com.ltm2019.mistory.model.UserModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<UserModel, String> {
    @Query("{ username:'?0'}")
    UserModel findOneByUsername(String username);

    @Query(value = "{ $or: [{ username: { $regex: ?0 } },{ fullName: { $regex: ?0 } }] }")
    List<UserModel> searchBy(String text, Pageable pageable);
}
