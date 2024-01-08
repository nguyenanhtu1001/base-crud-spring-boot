package com.example.springproject.service;


import com.example.springproject.dto.base.PageResponse;
import com.example.springproject.dto.request.UserRequest;
import com.example.springproject.dto.response.UserResponse;

/**
 * Service interface for managing user-related operations.
 */
public interface UserService {

    /**
     * Retrieve a user by their unique identifier.
     *
     * @param id The unique identifier of the user.
     * @return The UserResponseDTO containing user details.
     */
    UserResponse getById(String id);

    /**
     * Create a new user based on the provided UserRequestDTO.
     *
     * @param request The UserRequestDTO containing user information for creation.
     * @return The created UserResponseDTO.
     */
    UserResponse create(UserRequest request);

    /**
     * Delete a user by their unique identifier.
     *
     * @param id The unique identifier of the user to be deleted.
     */
    void delete(String id);

    /**
     * Retrieve a paginated list of all users.
     *
     * @param size The number of users to be retrieved in each page.
     * @param page The page number.
     * @return The PageResponse containing a list of UserResponse objects.
     */
    PageResponse<UserResponse> getAllUser(int size, int page);

    /**
     * Retrieve a paginated list of users based on a keyword search.
     *
     * @param keyword The keyword to search for in user details.
     * @param size    The number of users to be retrieved in each page.
     * @param page    The page number.
     * @return The PageResponse containing a list of UserResponse objects matching the search criteria.
     */
    PageResponse<UserResponse> getUserBySearch(String keyword, int size, int page);

}