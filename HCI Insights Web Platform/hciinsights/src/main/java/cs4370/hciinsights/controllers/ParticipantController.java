package cs4370.hciinsights.controllers;

import cs4370.hciinsights.services.ParticipantService;
import cs4370.hciinsights.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/participants")
public class ParticipantController {

    private final ParticipantService participantService;
    private final UserService userService;

    @Autowired
    public ParticipantController(ParticipantService participantService, UserService userService) {
        this.participantService = participantService;
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView listParticipants() {
        ModelAndView mv = new ModelAndView("participants");

        try {
            mv.addObject("participants", participantService.getAllParticipants());
        } catch (Exception e) {
            mv.addObject("errorMessage", "Failed to load participants.");
        }

        mv.addObject("loggedInUser", userService.getLoggedInUser());
        return mv;
    }

    @GetMapping("/create")
    public ModelAndView createParticipantPage() {
        ModelAndView mv = new ModelAndView("create_participant");
        mv.addObject("loggedInUser", userService.getLoggedInUser());
        return mv;
    }

    @PostMapping("/create")
    public String createParticipant(@RequestParam String fname,
            @RequestParam String lname,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String occupation,
            @RequestParam(required = false) String occupationExp,
            @RequestParam(required = false) String email) {

        try {
            boolean success = participantService.createParticipant(
                    fname, lname, age, occupation, occupationExp, email);

            if (success) {
                return "redirect:/participants";
            }
        } catch (Exception e) {
            return "redirect:/participants/create?error=Failed";
        }

        return "redirect:/participants/create?error=Unknown";
    }

    @GetMapping("/{participantId}")
    public ModelAndView participantDetail(@PathVariable int participantId) {
        ModelAndView mv = new ModelAndView("participant_detail");

        try {
            mv.addObject("participant", participantService.getParticipantById(participantId));
        } catch (Exception e) {
            mv.addObject("errorMessage", "Failed to load participant details.");
        }
        mv.addObject("loggedInUser", userService.getLoggedInUser());
        return mv;
    }

    @GetMapping("/delete/{participantId}")
    public String deleteParticipant(@PathVariable int participantId) {
        try {
            participantService.deleteParticipant(participantId);
        } catch (Exception e) {
            return "redirect:/participants?error=DeleteFailed";
        }
        return "redirect:/participants?deleted=true";
    }

}