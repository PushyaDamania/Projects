package cs4370.hciinsights.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cs4370.hciinsights.services.UserService;
import cs4370.hciinsights.services.StudyService;

@Controller
@RequestMapping
public class HomeController {

    private final UserService userService;
    private final StudyService studyService;

    @Autowired
    public HomeController(UserService userService, StudyService studyService) {
        this.userService = userService;
        this.studyService = studyService;
    }

    @GetMapping
    public ModelAndView webpage(@RequestParam(name = "error", required = false) String error) {
        ModelAndView mv = new ModelAndView("home");

        mv.addObject("loggedInUser", userService.getLoggedInUser());
        mv.addObject("errorMessage", error);

        try {
            int userId = userService.getLoggedInUser().getUserId();
            mv.addObject("studies", studyService.getStudiesByUser(userId));
        } catch (Exception e) {
            mv.addObject("studies", null);
        }

        return mv;
    }

}