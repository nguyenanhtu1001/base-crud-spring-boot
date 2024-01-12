package com.example.springproject.repository;


import com.example.springproject.dto.response.UserResponse;
import com.example.springproject.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
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

  /**
   * Custom method to call a stored procedure named "get_all_users" with pagination parameters.
   *
   * @param pageSize    The number of records to retrieve in each page.
   * @param pageNumber  The page number.
   * @return             A list of User entities returned by the stored procedure.
   */
  @Procedure(procedureName = "get_all_users")
  List<User> getAllUser(@Param("pageSize") int pageSize, @Param("pageNumber") int pageNumber);


  /**
   * Executes a stored procedure to add a new user to the database.
   *
   * This method invokes a stored procedure named "add_new_user" to insert a new user into the database.
   * The procedure takes parameters for the username, password, email, phone, and role.
   *
   * @param username The username of the new user.
   * @param password The password of the new user.
   * @param email    The email of the new user.
   * @param phone    The phone number of the new user.
   * @param role     The role of the new user.
   */
  @Procedure(procedureName = "add_new_user")
  void addNewUser(
          @Param("p_username") String username,
          @Param("p_password") String password,
          @Param("p_email") String email,
          @Param("p_phone") String phone,
          @Param("p_role") String role
  );

  /**
   * Executes a stored procedure to delete a user by their unique identifier.
   *
   * This method invokes a stored procedure named "delete_user_by_id" to delete a user from the database
   * based on the provided unique identifier.
   *
   * @param id The unique identifier of the user to be deleted.
   */
  @Procedure(procedureName = "delete_user_by_id")
  void deleteUser(@Param("in_in") String id);


  /**
   * Calls a stored procedure to update user information in the database.
   *
   * This method invokes a stored procedure named "update_user" to update the information of a user in the database.
   * It passes the provided parameters for user ID, username, password, email, phone, and role to the stored procedure.
   * The stored procedure is expected to return a User object representing the updated user.
   *
   * @param userId   The unique identifier of the user to be updated.
   * @param username The new username for the user.
   * @param password The new password for the user.
   * @param email    The new email for the user.
   * @param phone    The new phone number for the user.
   * @param role     The new role for the user.
   * @return A User object representing the updated user.
   */
  @Procedure(procedureName = "update_user")
  UserResponse updateUser(
          @Param("p_user_id") String userId,
          @Param("p_username") String username,
          @Param("p_password") String password,
          @Param("p_email") String email,
          @Param("p_phone") String phone,
          @Param("p_role") String role
  );
}
