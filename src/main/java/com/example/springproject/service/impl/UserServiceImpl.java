package com.example.springproject.service.impl;

import com.example.springproject.dto.base.PageResponse;
import com.example.springproject.dto.request.UserRequest;
import com.example.springproject.dto.response.UserResponse;
import com.example.springproject.entity.User;
import com.example.springproject.exception.UserNotFoundException;
import com.example.springproject.repository.UserRepository;
import com.example.springproject.service.UserService;
import com.example.springproject.service.base.BaseServiceImpl;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("CreateUserProcedure");

        // Đặt giá trị cho các tham số
        storedProcedure.registerStoredProcedureParameter("p_username", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_password", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_email", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_phone", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_role", String.class, ParameterMode.IN);

        storedProcedure.setParameter("p_username", request.getUsername());
        storedProcedure.setParameter("p_password", request.getPassword());
        storedProcedure.setParameter("p_email", request.getEmail());
        storedProcedure.setParameter("p_phone", request.getPhone());
        storedProcedure.setParameter("p_role", request.getRole());

        // Thực thi stored procedure
        storedProcedure.execute();

        // Lấy thông tin người dùng từ kết quả truy vấn SELECT
        Query selectQuery = entityManager.createNativeQuery("SELECT * FROM user WHERE username = :username", User.class);
        selectQuery.setParameter(request.getUsername(), request.getUsername());

        // Đảm bảo rằng chỉ có một kết quả được trả về
        User newUser = (User) selectQuery.getSingleResult();

        log.info("Stored procedure CreateUserProcedure executed successfully. New user ID: {}", newUser.getId());

        // Trả về đối tượng UserResponse
        return new UserResponse(
              newUser.getId(),
              newUser.getUsername(),
              newUser.getPassword(),
              newUser.getEmail(),
              newUser.getPhone(),
              newUser.getRole()
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
     * Retrieves a paginated list of users based on a search keyword.
     *
     * @param keyword The search keyword to filter users.
     * @param size   The number of users to be retrieved in each page.
     * @param page    The page number.
     * @return A PageResponse containing a list of UserResponse objects matching the search criteria.
     *         The PageResponse includes the user data for the requested page and the total number of matching users.
     */
    @Override
    public PageResponse<UserResponse> getUserBySearch(String keyword, int size, int page) {
        log.info("(request) listSearchUser keyword:{}, size : {}, page: {}", keyword, size, page);

        Pageable pageable = PageRequest.of(page, size);
        Page<UserResponse> users = repository.searchUser(pageable, keyword, PERCENT, PERCENT);

        return PageResponse.of(users.getContent(), (int) users.getTotalElements());
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