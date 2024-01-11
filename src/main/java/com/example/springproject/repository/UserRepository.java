package com.example.springproject.repository;


import com.example.springproject.dto.response.UserResponse;
import com.example.springproject.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Repository interface for managing User entities. Extends the BaseRepository interface.
 */
public interface UserRepository extends BaseRepository<User> {

  /**
   * Retrieves a UserResponse by user ID.
   *
   * @param id The ID of the user.
   * @return A UserResponse object containing selected user details.
   */
  @Query(
        """
              select new com.example.springproject.dto.response.UserResponse
              (u.id, u.username,u.password,u.email,u.phone,u.role)
              from User u
              where u.id=:id
              """
  )
  UserResponse getByUserId(String id);

  /**
   * Retrieves a paginated list of UserResponse objects.
   *
   * @param pageable Pagination information includes page number, size.
   * @return A paginated list of UserResponse objects.
   */
  @Query("""
             select new com.example.springproject.dto.response.UserResponse
             (u.id, u.username,u.password,u.email,u.phone,u.role)
             from User u
         """)
  Page<UserResponse> findAllUser(Pageable pageable);

  /**
   * Searches for users based on a keyword, providing paginated results.
   *
   * @param pageable Pagination information.
   * @param keyword  The keyword to search for in user attributes.
   * @return A paginated list of UserResponse objects matching the search criteria.
   */
//  @Query("""
//            select new com.example.springproject.dto.response.UserResponse
//            (u.id, u.username, u.password, u.email, u.phone, u.role)
//            from User u
//            where (:keyword is null or
//            lower(u.username) LIKE lower(concat('%', :keyword, '%')) or
//            lower(u.phone) LIKE lower(concat('%', :keyword, '%')) or
//            lower(u.email) LIKE lower(concat('%', :keyword, '%')) or
//            lower(u.phone) LIKE lower(concat('%', :keyword, '%')) or
//            lower(u.role) LIKE lower(concat('%', :keyword, '%')))
//        """)
//  Page<UserResponse> searchUser(Pageable pageable, String keyword);
  @Query("""
        select new com.example.springproject.dto.response.UserResponse
        (u.id, u.username, u.password, u.email, u.phone, u.role)
        from User u
        where (:keyword is null or
        lower(u.username) LIKE lower(concat(:prefix, :keyword, :suffix)) or
        lower(u.phone) LIKE lower(concat(:prefix, :keyword, :suffix)) or
        lower(u.email) LIKE lower(concat(:prefix, :keyword, :suffix)) or
        lower(u.phone) LIKE lower(concat(:prefix, :keyword, :suffix)) or
        lower(u.role) LIKE lower(concat(:prefix, :keyword, :suffix)))
    """)
  Page<UserResponse> searchUser(Pageable pageable, @Param("keyword") String keyword,
                                @Param("prefix") String prefix, @Param("suffix") String suffix);

  Optional<User> findUserByUsername(String username);

}
