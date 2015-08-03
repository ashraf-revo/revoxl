package revox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import revox.domain.user;
import revox.messages.ConversationMessage;
import revox.repository.userRepository;
import revox.service.UserSecurity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

/**
 * Created by ashraf on 8/2/15.
 */
@Controller
class controller {

    @Autowired
    userRepository userRepository;
    @Autowired
    UserSecurity userSecurity;

    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/")
    public int login() {
        return 0;
    }
    @RequestMapping(value = "/signup")
    public String signup(WebRequest request, HttpServletRequest req, HttpServletResponse res) {
        userSecurity.handSignUp(request, req, res);
        return "redirect:/";
    }

    @MessageMapping("/hello")
    public void greeting(ConversationMessage message,Principal principal){
        sendingOperations.convertAndSendToUser(
                principal.getName(), "/topic/greetings",
                message.setContent("welcome "+message.getContent()));

    }
    @Autowired
    SimpMessageSendingOperations sendingOperations;
    @RequestMapping("/csrf")
    @ResponseBody
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }
}
