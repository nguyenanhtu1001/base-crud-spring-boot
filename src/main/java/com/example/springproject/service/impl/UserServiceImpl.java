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

import static com.example.springproject.constant.CommonConstants.PERCENT;

@Slf4j
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
  private final UserRepository repository;

  public UserServiceImpl(UserRepository repository) {
    super(repository);
    this.repository = repository;
  }

  @Override
  public UserResponse getById(String id) {
    log.info("(request) getById: {}", id);
    UserResponse user = repository.getByUserId(id);
    if (user != null)
      return user;
    else
      throw new UserNotFoundException();
  }

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

  @Transactional
  @Override
  public void delete(String id) {
    log.info("(request) delete id: {}", id);
    User user = checkUserExist(id);
    repository.delete(user);
  }

  @Override
  public PageResponse<UserResponse> getAllUser(int size, int page) {
    log.info("(request) listAllUser size : {}, page: {}", size, page);

    Pageable pageable = PageRequest.of(page, size);
    Page<UserResponse> listAllUsers = repository.findAllUser(pageable);
    return PageResponse.of(listAllUsers.getContent(), (int) listAllUsers.getTotalElements());
  }

  @Override
  public PageResponse<UserResponse> getUserBySearch(String keyword, int size, int page) {
    log.info("(request) listSearchUser keyword:{}, size : {}, page: {}", keyword, size, page);

    Pageable pageable = PageRequest.of(page, size);
    Page<UserResponse> users = repository.searchUser(pageable, keyword, PERCENT, PERCENT);

    return PageResponse.of(users.getContent(), (int) users.getTotalElements());
  }

  private User checkUserExist(String id) {
    return repository.findById(id).orElseThrow(UserNotFoundException::new);
  }
}