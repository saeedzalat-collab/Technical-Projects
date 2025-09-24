package com.appsdeveloperblog.app.ws.io.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.appsdeveloperblog.app.ws.io.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findByEmail(String Email);
	UserEntity findByUserId(String userId);
	
	@Query(value = "select * from Users u where u.EMAIL_VERIFICATION_STATUS = true", nativeQuery = true)
	Page<UserEntity> findAllUsersWithConfirmedEmailAddress(Pageable pageableRequest);
	
	//Positional Parameters:
	@Query(value = "select * from Users u where u.FIRST_NAME  = ?1", nativeQuery = true)
	List<UserEntity> findUsersByFirstName(String firstName);
	
	//With named Parameters:
	@Query(value = "select * from Users u where u.LAST_NAME  = :lastName"  , nativeQuery = true)
	List<UserEntity> findUsersByLastName(@Param("lastName") String lastName);
	
	@Transactional // this annotation allows us to modify or delete records from database and run it in transaction
	@Modifying // we add this because we update a record in database
	@Query(value = "UPDATE users SET email_verification_status = :emailVerificationStatus WHERE user_id = :userId", nativeQuery = true)
	void updateUserEmailVerificationStatus(@Param("emailVerificationStatus") boolean emailVerificationStatus, @Param("userId") String userId);
	
	//Using JPQL
	@Query("SELECT user FROM UserEntity user WHERE user.userId = :userId")
	UserEntity findUserEntityByUserId(@Param("userId") String userId);

	//return specific fields using JPQL
	@Query("select user.firstName, user.lastName from UserEntity user where user.userId = :userId")
	List<Object[]> getUserEntityFullNameById(@Param("userId") String userId);
	
	@Transactional // this annotation allows us to modify or delete records from database
	@Modifying // we add this because we update a record in database
	@Query( "UPDATE UserEntity u SET u.emailVerificationStatus = :emailVerificationStatus WHERE userId = :userId")
	void updateUserEntityEmailVerificationStatus(@Param("emailVerificationStatus") boolean emailVerificationStatus, @Param("userId") String userId);

}


