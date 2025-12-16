package cs4370.hciinsights.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import cs4370.hciinsights.services.SessionService;
import cs4370.hciinsights.services.ParticipantService;
import cs4370.hciinsights.services.UserService;

import java.sql.Timestamp;

@Controller
@RequestMapping("/studies/{studyId}/sessions")
public class SessionController {

    private final SessionService sessionService;
    private final UserService userService;
    private final ParticipantService participantService;

    @Autowired
    public SessionController(SessionService sessionService, ParticipantService participantService,
            UserService userService) {
        this.sessionService = sessionService;
        this.participantService = participantService;
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView listSessions(@PathVariable int studyId) {
        ModelAndView mv = new ModelAndView("sessions");
        try {
            mv.addObject("sessions", sessionService.getSessionsByStudy(studyId));
            mv.addObject("participants", participantService.getAllParticipants());
        } catch (Exception e) {
            mv.addObject("errorMessage", e.getMessage());
        }
        mv.addObject("studyId", studyId);
        mv.addObject("loggedInUser", userService.getLoggedInUser());
        return mv;
    }

    @GetMapping("/create")
    public ModelAndView createSessionPage(@PathVariable int studyId) {
        ModelAndView mv = new ModelAndView("create_session");
        try {
            mv.addObject("participants", participantService.getAllParticipants());
        } catch (Exception e) {
            mv.addObject("errorMessage", e.getMessage());
        }
        mv.addObject("studyId", studyId);
        mv.addObject("loggedInUser", userService.getLoggedInUser());
        return mv;
    }

    @PostMapping("/create")
    public String createSession(
            @PathVariable int studyId,
            @RequestParam("participantId") int participantId,
            @RequestParam("scheduled") String scheduled,
            @RequestParam(required = false) String notes,
            @RequestParam(required = false) Integer score) {
        try {
            sessionService.createSession(
                    participantId,
                    studyId,
                    Timestamp.valueOf(scheduled.replace("T", " ") + ":00"),
                    notes,
                    score);
            return "redirect:/studies/" + studyId + "/sessions";
        } catch (Exception e) {
            return "redirect:/studies/" + studyId + "/sessions/create?error=" + e.getMessage();
        }
    }

    @GetMapping("/{sessionId}")
    public ModelAndView viewSession(@PathVariable("studyId") int studyId,
            @PathVariable("sessionId") int sessionId) {
        ModelAndView mv = new ModelAndView("session_detail");
        try {
            var session = sessionService.getSessionById(sessionId);
            if (session == null) {
                return new ModelAndView("redirect:/studies/" + studyId + "/sessions?error=NotFound");
            }
            mv.addObject("session", session);
        } catch (Exception e) {
            mv.addObject("errorMessage", "Could not load session details.");
        }
        mv.addObject("studyId", studyId);
        mv.addObject("loggedInUser", userService.getLoggedInUser());
        return mv;
    }

    @GetMapping("/delete/{sessionId}")
    public String deleteSession(@PathVariable int studyId,
            @PathVariable int sessionId) {
        try {
            sessionService.deleteSession(sessionId);
        } catch (Exception e) {
            return "redirect:/studies/" + studyId + "/sessions?error=DeleteFailed";
        }
        return "redirect:/studies/" + studyId + "/sessions?deleted=true";
    }
}
