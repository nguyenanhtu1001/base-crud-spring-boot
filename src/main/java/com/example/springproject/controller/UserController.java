package com.example.springproject.controller;

import com.example.springproject.dto.base.PageResponse;
import com.example.springproject.dto.base.ResponseGeneral;
import com.example.springproject.dto.request.UserRequest;
import com.example.springproject.dto.response.UserResponse;
import com.example.springproject.service.UserService;
import com.example.springproject.service.base.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.example.springproject.constant.CommonConstants.*;
import static com.example.springproject.constant.MessageCodeConstant.*;

/**
 * Controller class responsible for handling HTTP requests related to user operations.
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final MessageService messageService;

    /**
     * Retrieve user details by user ID.
     *
     * @param id       The unique identifier of the user.
     * @param language The language for response messages.
     * @return ResponseGeneral containing UserResponse.
     */
    @GetMapping("/{id}")
    public ResponseGeneral<UserResponse> getById(
            @PathVariable String id,
            @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
    ) {
        log.info("(getById) id : {}", id);
        return ResponseGeneral.ofSuccess(messageService.getMessage(GET_USER_BY_ID, language),
                userService.getById(id));
    }

    /**
     * Create a new user.
     *
     * @param request  The UserRequest containing user information.
     * @param language The language for response messages.
     * @return ResponseGeneral containing created UserResponse.
     */
    @PostMapping
    public ResponseGeneral<UserResponse> create(
            @RequestBody UserRequest request,
            @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
    ) {
        log.info("(create) Request : {}", request);
        return ResponseGeneral.ofCreated(messageService.getMessage(CREATE_USER, language),
                userService.create(request));
    }

    /**
     * Search users based on a keyword.
     *
     * @param keyword  The keyword for user search.
     * @param size     The number of users to be retrieved in each page.
     * @param page     The page number.
     * @param language The language for response messages.
     * @return ResponseGeneral containing PageResponse of UserResponse.
     */
    @GetMapping("/search")
    public ResponseGeneral<PageResponse<UserResponse>> getUserBySearch(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE) int size,
            @RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER) int page,
            @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
    ) {
        log.info("(listSearchUser) keyword: {}, size : {}, page: {}", keyword, size, page);
        return ResponseGeneral.ofSuccess(messageService.getMessage(LIST_USER, language),
                userService.getUserBySearch(keyword, size, page)
        );
    }

    /**
     * Retrieve a paginated list of all users.
     *
     * @param size     The number of users to be retrieved in each page.
     * @param page     The page number.
     * @param language The language for response messages.
     * @return ResponseGeneral containing PageResponse of UserResponse.
     */
    @GetMapping("/all")
    public ResponseGeneral<PageResponse<UserResponse>> getAllUser(
            @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE) int size,
            @RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER) int page,
            @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
    ) {
        log.info("(listAllUser) size : {}, page: {}", size, page);
        return ResponseGeneral.ofSuccess(messageService.getMessage(LIST_USER, language),
                userService.getAllUser(size, page)
        );
    }

    /**
     * Delete a user by user ID.
     *
     * @param id       The unique identifier of the user to be deleted.
     * @param language The language for response messages.
     * @return ResponseGeneral indicating success and containing no data.
     */
    @DeleteMapping("{id}")
    public ResponseGeneral<Void> delete(
            @PathVariable String id,
            @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
    ) {
        log.info("(delete) id : {}", id);
        userService.delete(id);
        return ResponseGeneral.ofSuccess(messageService.getMessage(DELETE_USER, language));
    }
}

