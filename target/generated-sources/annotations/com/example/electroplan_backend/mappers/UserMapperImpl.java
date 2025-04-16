package com.example.electroplan_backend.mappers;

import com.example.electroplan_backend.dto.entities.UserEntity;
import com.example.electroplan_backend.dto.requests.UserRegistrationRequest;
import com.example.electroplan_backend.dto.responses.UserProfileResponse;
import com.example.electroplan_backend.dto.responses.UserShortResponse;
import com.example.electroplan_backend.dto.responses.UserUpdateResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-16T20:25:28+0300",
    comments = "version: 1.6.0, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserShortResponse toUserResponse(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserShortResponse userShortResponse = new UserShortResponse();

        userShortResponse.setId( userEntity.getId() );
        userShortResponse.setName( userEntity.getName() );
        userShortResponse.setSurname( userEntity.getSurname() );

        return userShortResponse;
    }

    @Override
    public UserUpdateResponse toUserUpdateResponse(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserUpdateResponse userUpdateResponse = new UserUpdateResponse();

        userUpdateResponse.setId( userEntity.getId() );
        userUpdateResponse.setName( userEntity.getName() );
        userUpdateResponse.setSurname( userEntity.getSurname() );
        userUpdateResponse.setPhoneNumber( userEntity.getPhoneNumber() );
        userUpdateResponse.setEmail( userEntity.getEmail() );

        return userUpdateResponse;
    }

    @Override
    public UserEntity toUserEntity(UserRegistrationRequest userRegistrationRequest) {
        if ( userRegistrationRequest == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setName( userRegistrationRequest.getName() );
        userEntity.setSurname( userRegistrationRequest.getSurname() );
        userEntity.setPhoneNumber( userRegistrationRequest.getPhoneNumber() );
        userEntity.setEmail( userRegistrationRequest.getEmail() );
        userEntity.setPassword( userRegistrationRequest.getPassword() );

        return userEntity;
    }

    @Override
    public UserProfileResponse toUserProfileResponse(UserEntity byEmail) {
        if ( byEmail == null ) {
            return null;
        }

        UserProfileResponse userProfileResponse = new UserProfileResponse();

        userProfileResponse.setId( byEmail.getId() );
        userProfileResponse.setName( byEmail.getName() );
        userProfileResponse.setSurname( byEmail.getSurname() );
        userProfileResponse.setEmail( byEmail.getEmail() );
        userProfileResponse.setPhoneNumber( byEmail.getPhoneNumber() );

        return userProfileResponse;
    }
}
