package cs4370.hciinsights.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cs4370.hciinsights.models.Study;
import cs4370.hciinsights.services.AnalyticsService;
import cs4370.hciinsights.services.StudyService;
import cs4370.hciinsights.services.UserService;

@Controller
public class AnalyticsController {

    private final AnalyticsService analyticsService;
    private final UserService userService;
    private final StudyService studyService;

    @Autowired
    public AnalyticsController(AnalyticsService analyticsService, UserService userService, StudyService studyService) {
        this.analyticsService = analyticsService;
        this.userService = userService;
        this.studyService = studyService;
    }

    @GetMapping("/analytics")
        public ModelAndView showAnalytics(
            @RequestParam(name = "studyId", required = false) Integer studyId) {

        ModelAndView mv = new ModelAndView("analytics");

        mv.addObject("loggedInUser", userService.getLoggedInUser());
        int userId = userService.getLoggedInUser().getUserId();
        mv.addObject("userId", userId);
        mv.addObject("selectedStudyId", studyId);

        try {

            mv.addObject("studies", mapStudies(studyService.getStudiesByUser(userId), studyId));

            mv.addObject("avgTaskTimes", analyticsService.getAverageTaskTimes(userId, studyId));
            mv.addObject("difficultyRatios", analyticsService.getTaskDifficultyRatios(userId, studyId));
            mv.addObject("participantScores", analyticsService.getAverageParticipantScores(userId, studyId));
        } catch (Exception e) {
            mv.addObject("errorMessage", e.getMessage());
        }
        return mv;
    }

    private List<Map<String, Object>> mapStudies(List<Study> studies, Integer selectedId) {
        List<Map<String, Object>> mapped = new ArrayList<>();

        for (Study study : studies) {
            Map<String, Object> row = new HashMap<>();
            row.put("studyId", study.getStudyId());
            row.put("title", study.getTitle());
            row.put("selected", selectedId != null && study.getStudyId() == selectedId);
            mapped.add(row);
        }
        return mapped;
    }

}