package fpt.lab;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/v1")
public class SampleController {
    @GetMapping
    @ResponseBody
    public String base() {
        return "Database connected successfully...";
    }
}