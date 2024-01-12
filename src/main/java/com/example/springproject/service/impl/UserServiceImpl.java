package com.example.springproject.service.impl;

import com.example.springproject.dto.base.PageResponse;
import com.example.springproject.dto.request.UserRequest;
import com.example.springproject.dto.response.UserResponse;
import com.example.springproject.entity.User;
import com.example.springproject.exception.UserNotFoundException;
import com.example.springproject.repository.UserRepository;
import com.example.springproject.service.UserService;
import com.example.springproject.service.base.BaseServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.springproject.constant.CommonConstants.PERCENT;

/**
 * Implementation of the {@link UserService} interface.
 * Extends {@link BaseServiceImpl} for common CRUD operations.
 */
@Slf4j

public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    @PersistenceContext
    private EntityManager entityManager;

    private final UserRepository repository;

    /**
     * Constructor for UserServiceImpl.
     *
     * @param repository The UserRepository used for database operations.
     */
    public UserServiceImpl(UserRepository repository) {
        super(repository);
        this.repository = repository;
    }

    /**
     * Retrieve a user by their unique identifier.
     *
     * @param id The unique identifier of the user.
     * @return The UserResponseDTO containing user details.
     * @throws UserNotFoundException if the user with the given id is not found.
     */
    @Override
    public UserResponse getById(String id) {
        log.info("(request) getById: {}", id);
        UserResponse user = repository.getByUserId(id);
        if (user != null)
            return user;
        else
            throw new UserNotFoundException();
    }

    /**
     * Create a new user based on the provided UserRequestDTO.
     *
     * @param request The UserRequestDTO containing user information for creation.
     * @return The created UserResponse.
     */
    @Transactional
    @Override
    public UserResponse create(UserRequest request) {
        log.info("(request) create: {}", request);
        User user = new User(
              request.getUsername(),
              request.getPassword(),
              request.getEmail(),
              request.getPhone(),
              request.getRole()
        );
        this.create(user);
        return new UserResponse(
              user.getId(),
              user.getUsername(),
              user.getPassword(),
              user.getEmail(),
              user.getPhone(),
              user.getRole()
        );
    }

    /**
     * Delete a user by their unique identifier.
     *
     * @param id The unique identifier of the user to be deleted.
     */
//    @Transactional
//    @Override
//    public void delete(String id) {
//        log.info("(request) delete id: {}", id);
//        User user = checkUserExist(id);
//        repository.delete(user);
//
//    }

    /**
     * Deletes a user by their unique identifier using a stored procedure.
     *
     * This method logs information about the delete request, checks if the user with the given unique identifier exists,
     * and then proceeds to invoke a stored procedure to delete the user using the UserRepository's delete procedure.
     * If the user is not found, a UserNotFoundException is thrown.
     *
     * @param id The unique identifier of the user to be deleted.
     * @throws UserNotFoundException if the user with the given id is not found.
     */
    @Transactional
    @Override
    public void delete(String id) {
        log.info("(request) delete id: {}", id);
        this.checkUserExist(id);
        repository.deleteUser(id);
    }

    /**
     * Retrieve a paginated list of all users.
     *
     * @param size The number of users to be retrieved in each page.
     * @param page The page number.
     * @return The PageResponse containing a list of UserResponse objects.
     */
    @Override
    public PageResponse<UserResponse> getAllUser(int size, int page) {
        log.info("(request) listAllUser size : {}, page: {}", size, page);
        Pageable pageable = PageRequest.of(page, size);
        Page<UserResponse> listAllUsers = repository.findAllUser(pageable);
        return PageResponse.of(listAllUsers.getContent(), (int) listAllUsers.getTotalElements());
    }

    /**
     * Retrieves a paginated list of users based on a search keyword.
     *
     * @param keyword The search keyword to filter users.
     * @param size    The number of users to be retrieved in each page.
     * @param page    The page number.
     * @return A PageResponse containing a list of UserResponse objects matching the search criteria.
     * The PageResponse includes the user data for the requested page and the total number of matching users.
     */
    @Override
    public PageResponse<UserResponse> getUserBySearch(String keyword, int size, int page) {
        log.info("(request) listSearchUser keyword:{}, size : {}, page: {}", keyword, size, page);

        Pageable pageable = PageRequest.of(page, size);
        Page<UserResponse> users = repository.searchUser(pageable, keyword, PERCENT, PERCENT);

        return PageResponse.of(users.getContent(), (int) users.getTotalElements());
    }

    /**
     * Retrieves a paginated list of UserResponse objects.
     * <p>
     * This method is responsible for fetching a paginated list of users from the repository,
     * transforming them into UserResponse objects, and returning the result.
     * The transactional annotation ensures that the method is executed within a transaction context.
     *
     * @param size The number of users to be retrieved in each page.
     * @param page The page number.
     * @return A List of UserResponse objects representing the paginated list of users.
     */
    @Transactional
    @Override
    public List<UserResponse> getAllUsers(int size, int page) {
        // Call the repository to fetch a paginated list of users using a stored procedure.
        List<User> users = repository.getAllUser(size, page);

        // Map the list of User entities to a list of UserResponse objects using Java Stream and map.
        return users.stream().map(user -> new UserResponse(
                user.getId(),
                user.getUsername(),
                null,
                user.getEmail(),
                user.getPhone(),
                user.getRole()
        )).collect(Collectors.toList());
    }

    @Override
    public UserResponse updateUser(UserRequest request, String id) {
        User user = this.checkUserExist(id);
      return repository.updateUser(
              id,
              request.getUsername(),
              request.getPassword(),
              request.getEmail(),
              request.getPhone(),
              request.getRole()
        );
    }

    /**
     * Checks if a user with the given id exists.
     *
     * @param id The unique identifier of the user.
     * @return The User entity if found.
     * @throws UserNotFoundException if the user with the given id is not found.
     */
    private User checkUserExist(String id) {
        return repository.findById(id).orElseThrow(UserNotFoundException::new);
    }
}