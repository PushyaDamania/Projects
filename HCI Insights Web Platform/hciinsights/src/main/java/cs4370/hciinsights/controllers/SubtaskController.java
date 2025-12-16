package cs4370.hciinsights.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import cs4370.hciinsights.services.SubtaskService;
import cs4370.hciinsights.services.UserService;

@Controller
@RequestMapping("/studies/{studyId}/tasks/{taskId}/subtasks")
public class SubtaskController {

    private final SubtaskService subtaskService;
    private final UserService userService;

    @Autowired
    public SubtaskController(SubtaskService subtaskService, UserService userService) {
        this.subtaskService = subtaskService;
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView viewSubtasks(@PathVariable int studyId, @PathVariable int taskId) {
        ModelAndView mv = new ModelAndView("subtasks");
        try {
            mv.addObject("subtasks", subtaskService.getSubtasksByTaskId(taskId));
        } catch (Exception e) {
            mv.addObject("errorMessage", "Could not load subtasks.");
        }
        mv.addObject("studyId", studyId);
        mv.addObject("taskId", taskId);
        mv.addObject("loggedInUser", userService.getLoggedInUser());
        return mv;
    }

    @GetMapping("/create")
    public ModelAndView createSubtaskPage(@PathVariable int studyId, @PathVariable int taskId) {
        ModelAndView mv = new ModelAndView("create_subtasks");
        mv.addObject("studyId", studyId);
        mv.addObject("taskId", taskId);
        mv.addObject("loggedInUser", userService.getLoggedInUser());
        return mv;
    }

    @PostMapping("/create")
    public String createSubtask(@PathVariable int studyId,
            @PathVariable int taskId,
            @RequestParam String description,
            @RequestParam(required = false) Integer expectedCompTime) {
        try {
            subtaskService.createSubtask(taskId, description, expectedCompTime);
        } catch (Exception e) {
            return "redirect:/studies/" + studyId + "/tasks/" + taskId + "/subtasks/create?error=fail";
        }
        return "redirect:/studies/" + studyId + "/tasks/" + taskId + "/subtasks";
    }

    @GetMapping("/{subtaskId}")
    public ModelAndView viewSubtask(@PathVariable int studyId,
            @PathVariable int taskId,
            @PathVariable int subtaskId) {
        ModelAndView mv = new ModelAndView("subtask_detail");
        try {
            mv.addObject("subtask", subtaskService.getSubtaskById(taskId, subtaskId));
        } catch (Exception e) {
            mv.addObject("errorMessage", "Could not load subtask.");
        }
        mv.addObject("studyId", studyId);
        mv.addObject("taskId", taskId);
        mv.addObject("loggedInUser", userService.getLoggedInUser());
        return mv;
    }

    @GetMapping("/{subtaskId}/delete")
    public String deleteSubtask(@PathVariable int studyId,
            @PathVariable int taskId,
            @PathVariable int subtaskId) {
        try {
            subtaskService.deleteSubtask(taskId, subtaskId);
        } catch (Exception e) {
            return "redirect:/studies/" + studyId + "/tasks/" + taskId + "/subtasks?error=DeleteFailed";
        }
        return "redirect:/studies/" + studyId + "/tasks/" + taskId + "/subtasks?deleted=true";
    }
}
