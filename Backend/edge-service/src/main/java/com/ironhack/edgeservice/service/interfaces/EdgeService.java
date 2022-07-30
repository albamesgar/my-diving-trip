package com.ironhack.edgeservice.service.interfaces;

import com.ironhack.edgeservice.controller.dto.DiveDTO;
import com.ironhack.edgeservice.controller.dto.UserDTO;
import com.ironhack.edgeservice.model.Dive;
import com.ironhack.edgeservice.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

public interface EdgeService {
    UserDTO getUser(@PathVariable Long id);
    User loginUser(@AuthenticationPrincipal User user);
    UserDTO registerUser(@RequestBody UserDTO userDTO);
    User modifyUser(@PathVariable Long userId, @RequestBody UserDTO userDTO);
    void deleteUser(@PathVariable Long id);
    Dive addDiveToDiveBook(@PathVariable Long userId, @RequestBody DiveDTO diveDTO) throws IOException;
    String validateDive(@PathVariable Long id);
    String cancelDiveValidation(@PathVariable Long id);
}
