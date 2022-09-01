package org.energize.servlets.register;

import com.google.gson.Gson;
import org.energize.domain.User;
import org.energize.dto.UserDTO;
import org.energize.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/register/create")
public class CreateServlet  extends HttpServlet {

    @Override
    protected  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        boolean success  = false;

        UserDTO userDTO = new Gson().fromJson(request.getReader(),UserDTO.class);
        User user = this.userDTOToUser(userDTO);

        UserService userService = new UserService();
        success = userService.create(user);

    }

    private User userDTOToUser(UserDTO userDTO){
        User user = new User();
        user.setFirst_name(userDTO.getFirst_name());
        user.setLast_name(userDTO.getLast_name());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return user;
    }
}
