package cs4370.hciinsights.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import cs4370.hciinsights.services.ResultService;
import cs4370.hciinsights.services.TaskService;
import cs4370.hciinsights.services.UserService;

@Controller
@RequestMapping("/studies/{studyId}/sessions/{sessionId}/results")
public class ResultController {

    private final ResultService resultService;
    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public ResultController(ResultService resultService, TaskService taskService, UserService userService) {
        this.resultService = resultService;
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView viewResults(@PathVariable int studyId,
            @PathVariable int sessionId) {
        ModelAndView mv = new ModelAndView("results");

        try {
            mv.addObject("results", resultService.getResultsBySession(sessionId));
        } catch (Exception ignored) {
        }

        mv.addObject("studyId", studyId);
        mv.addObject("sessionId", sessionId);
        mv.addObject("loggedInUser", userService.getLoggedInUser());
        return mv;
    }

    @GetMapping("/create")
    public ModelAndView createResultPage(@PathVariable int studyId,
            @PathVariable int sessionId) {
        ModelAndView mv = new ModelAndView("create_result");

        try {
            mv.addObject("tasks", taskService.getTasksByStudy(studyId));
        } catch (Exception ignored) {
        }

        mv.addObject("studyId", studyId);
        mv.addObject("sessionId", sessionId);
        mv.addObject("loggedInUser", userService.getLoggedInUser());
        return mv;
    }

    @PostMapping("/create")
    public String createResult(@PathVariable int studyId,
            @PathVariable int sessionId,
            @RequestParam int taskId,
            @RequestParam(defaultValue = "false") boolean isComplete,
            @RequestParam(required = false) Integer completionTime,
            @RequestParam(required = false) Integer errors,
            @RequestParam(required = false) String notes) {
        try {
            resultService.createResult(sessionId, taskId, isComplete, completionTime, errors, notes);
        } catch (Exception ignored) {
        }

        return "redirect:/studies/" + studyId + "/sessions/" + sessionId + "/results";
    }

    @GetMapping("/{resultId}")
    public ModelAndView viewResult(@PathVariable int studyId,
            @PathVariable int sessionId,
            @PathVariable int resultId) {
        ModelAndView mv = new ModelAndView("result_detail");

        try {
            mv.addObject("result", resultService.getResultById(resultId));
        } catch (Exception ignored) {
        }

        mv.addObject("studyId", studyId);
        mv.addObject("sessionId", sessionId);
        mv.addObject("loggedInUser", userService.getLoggedInUser());
        return mv;
    }

    @GetMapping("/{resultId}/delete")
    public String deleteResult(@PathVariable int studyId,
            @PathVariable int sessionId,
            @PathVariable int resultId) {
        try {
            resultService.deleteResult(resultId);
        } catch (Exception ignored) {
        }

        return "redirect:/studies/" + studyId + "/sessions/" + sessionId + "/results";
    }
}
