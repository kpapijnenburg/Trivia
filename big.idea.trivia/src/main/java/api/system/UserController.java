package api.system;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import user.model.User;

import javax.xml.ws.Response;

@RestController
public class UserController {

    @RequestMapping(value = "/user" , method = RequestMethod.GET)
    public Response<User> login( @RequestParam(value = "name") String name, @RequestParam(value = "password") String password){
        //todo invullen
        return null;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Response register (@RequestParam(value = "name") String name, @RequestParam(value = "password")String password){
        // todo invullen
        return null;
    }

}
