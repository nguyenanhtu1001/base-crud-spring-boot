package com.example.springproject.service.impl;

import com.example.springproject.dto.base.PageResponse;
import com.example.springproject.dto.request.UserRequest;
import com.example.springproject.dto.response.UserResponse;
import com.example.springproject.entity.User;
import com.example.springproject.exception.UserNotFoundException;
import com.example.springproject.repository.UserRepository;
import com.example.springproject.service.UserService;
import com.example.springproject.service.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link UserService} interface.
 * Extends {@link BaseServiceImpl} for common CRUD operations.
 */
@Slf4j
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
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
    @Transactional
    @Override
    public void delete(String id) {
        log.info("(request) delete id: {}", id);
        User user = checkUserExist(id);
        repository.delete(user);
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
     * Retrieve a paginated list of users based on a keyword search.
     *
     * @param keyword The keyword to search for in user details.
     * @param size    The number of users to be retrieved in each page.
     * @param page    The page number.
     * @return The PageResponse containing a list of UserResponse objects matching the search criteria.
     */
    @Override
    public PageResponse<UserResponse> getUserBySearch(String keyword, int size, int page) {
        log.info("(request) listSearchUser keyword:{}, size : {}, page: {}", keyword, size, page);

        Pageable pageable = PageRequest.of(page, size);
        Page<UserResponse> list = repository.searchUser(pageable, keyword);
        return PageResponse.of(list.getContent(), (int) list.getTotalElements());
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