package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    //8080으로 들어면 뜨는 도메인컨트롤러 ("/")
    @GetMapping("/")
    public String home(){
        return "home"; //home.html이 호출
    }
}
