package server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class SomeController {

    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "Hello world!";
    }

    @GetMapping("/name/{name}")
    @ResponseBody
    public String name(@PathVariable("name") String name , @RequestParam(name = "title", required = false) String title) {
        var sb = new StringBuilder("Hello ");
        if(title != null) {
            sb.append(title ). append(' ');
        }
        sb.append(name );
        sb.append('!');
        return sb.toString ();
    }
}