package com.example.electroplan_backend.mappers;

import com.example.electroplan_backend.dto.entities.UserEntity;
import com.example.electroplan_backend.dto.requests.UserRegistrationRequest;
import com.example.electroplan_backend.dto.responses.UserProfileResponse;
import com.example.electroplan_backend.dto.responses.UserShortResponse;
import com.example.electroplan_backend.dto.responses.UserUpdateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserShortResponse toUserResponse(UserEntity userEntity);
    UserUpdateResponse toUserUpdateResponse(UserEntity userEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isAdmin", ignore = true)
    UserEntity toUserEntity(UserRegistrationRequest userRegistrationRequest);


    UserProfileResponse toUserProfileResponse(UserEntity byEmail);

}
