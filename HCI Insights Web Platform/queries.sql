/*
Java class: AnalyticsService.java 
Java method: getAverageTaskTimes
Purpose: for a user_id, get avg completion time/task but for only top 15 tasks for that user
URL: GET /analytics/task/average-time
*/
SELECT t.task_id,
       t.description,
       AVG(r.completion_time) AS avg_time
FROM Result r
JOIN Task t ON r.task_id = t.task_id
JOIN Session s ON r.session_id = s.session_id
JOIN Study st ON s.study_id = st.study_id
JOIN(
    SELECT DISTINCT t2.task_id
    FROM Task t2
    JOIN Study st2 ON t2.study_id = st2.study_id
    WHERE st2.user_id = 1
      AND st2.study_id = 10
    ORDER BY t2.task_id
    LIMIT 15
)AS topTasks ON topTasks.task_id = t.task_id
WHERE st.user_id = 1
  AND st.study_id = 10
GROUP BY t.task_id, t.description
ORDER BY t.task_id;

/*
Java class: AnalyticsService.java 
Java method: getTaskDifficultyRatio
Purpose: for a user_id and study, get difficulty ratio/task with formula (r.completion_time * 1.0 / t.expected_comp_time), limit to only top 15
URL: GET /analytics/task/difficulty
*/
SELECT t.task_id,
       t.description,
       AVG(r.completion_time * 1.0 / t.expected_comp_time) AS difficulty_ratio
FROM Result r
JOIN Task t ON r.task_id = t.task_id
JOIN Session s ON r.session_id = s.session_id
JOIN Study st ON s.study_id = st.study_id
WHERE st.user_id = 1
  AND st.study_id = 10
  AND r.completion_time IS NOT NULL
  AND t.expected_comp_time IS NOT NULL
GROUP BY t.task_id, t.description
ORDER BY difficulty_ratio DESC
LIMIT 15;

/*
Java class: AnalyticsService.java 
Java method: getAverageParticipantScores
Purpose: for given user_id and study, get avg session score/participant and limit to top 15
URL: GET analytics/participants/average-score
*/
SELECT p.participant_id,
       CONCAT(p.fname, ' ', p.lname) AS name,
       AVG(s.score) AS avg_score
FROM Participant p
JOIN Session s ON p.participant_id = s.participant_id
JOIN Study st ON s.study_id = st.study_id
WHERE st.user_id  = 1
  AND st.study_id = 10
GROUP BY p.participant_id, name
ORDER BY avg_score DESC
LIMIT 15;

/*
Java class: ParticipantService.java 
Java method: createParticipant
Purpose: insert participant with age and occupation info 
URL: POST /participants
*/
INSERT INTO Participant (fname, lname, age, occupation, occupation_exp, email)
VALUES ('Alice', 'Johnson', 29, 'Designer', '5 years', 'alice.johnson@example.com');

/*
Java class: ParticipantService.java 
Java method: getAllParticipants
Purpose: get all participants ordered lname then fname
URL: GET /participants
*/
SELECT participant_id,
       fname,
       lname,
       age,
       occupation,
       occupation_exp,
       email
FROM Participant
ORDER BY lname, fname;

/*
Java class: ParticipantService.java 
Java method: getParticipantsById
Purpose: get single participant w/ ID
URL: GET /participant/101
*/
SELECT participant_id,
       fname,
       lname,
       age,
       occupation,
       occupation_exp,
       email
FROM Participant
WHERE participant_id = 101;

/*
Java class: ParticipantService.java 
Java method: deleteParticipant
Purpose: delete participant by ID
URL: DELETE /participant/101
*/
DELETE FROM Participant
WHERE participant_id = 101;

/*
Java class: ResultService.java 
Java method: createResult
Purpose: insert new task result for a session_id
URL: POST /sessions/201/results
*/
INSERT INTO Result (session_id, task_id, is_complete, completion_time, errors, notes)
VALUES (201, 5, TRUE, 90, 2, 'Completed with some issues');

/*
Java class: ResultService.java 
Java method: getResultsBySessions
Purpose: get results for a given session
URL: GET /sessions/201/results
*/
SELECT result_id,
       session_id,
       task_id,
       is_complete,
       completion_time,
       errors,
       notes
FROM Result
WHERE session_id = 201;


/*
Java class: ResultService.java 
Java method: getResultsById
Purpose: get result by its id
URL: GET /results/301
*/
SELECT result_id,
       session_id,
       task_id,
       is_complete,
       completion_time,
       errors,
       notes
FROM Result
WHERE result_id = 301;

/*
Java class: ResultService.java 
Java method: deleteResult
Purpose: delete result by its id
URL: DELETA /results/301
*/
DELETE FROM Result
WHERE result_id = 301;

/*
Java class: SessionService.java 
Java method: createSession
Purpose: make new session for participant 
URL: POST /studies/10/sessions
*/
INSERT INTO Session (participant_id, study_id, scheduled, notes, score)
VALUES (101, 10, '2025-12-10 09:00:00', 'Initial usability test', 85);

/*
Java class: SessionService.java 
Java method: getSessionByStudy
Purpose: get all sessions for a study, order by time
URL: GET /studies/10/sessions
*/
SELECT session_id,
       participant_id,
       study_id,
       scheduled,
       notes,
       score
FROM Session
WHERE study_id = 10
ORDER BY scheduled ASC;

/*
Java class: SessionService.java 
Java method: getSessionById
Purpose: get all sessions by id
URL: GET /sessions/201
*/
SELECT session_id,
       participant_id,
       study_id,
       scheduled,
       notes,
       score
FROM Session
WHERE session_id = 201;

/*
Java class: SessionService.java 
Java method: deleteSessions
Purpose: delete all sessions by id
URL: DELETE /sessions/201
*/
DELETE FROM Session
WHERE session_id = 201;

/*
Java class: StudyService.java 
Java method: createStudy
Purpose: create new study
URL: POST /studies
*/
INSERT INTO Study (user_id, title, description, platform, status)
VALUES (1,
        'Mobile Checkout Flow',
        'Evaluate ease of completing purchases on mobile app',
        'Mobile',
        'ACTIVE');

/*
Java class: StudyService.java 
Java method: getStudyById
Purpose: get details of a study by id
URL: GET /studies/10
*/
SELECT study_id,
       user_id,
       title,
       description,
       platform,
       status,
       created_at
FROM Study
WHERE study_id = 10;

/*
Java class: StudyService.java 
Java method: getStudyByUser
Purpose: get details of a study by user
URL: GET /studies
*/
SELECT study_id,
       user_id,
       title,
       description,
       platform,
       status,
       created_at
FROM Study
WHERE user_id = 1
ORDER BY created_at DESC;

/*
Java class: StudyService.java 
Java method: updateStudy
Purpose: update main fields of a study by id
URL: POST /studies/10/edit
*/
UPDATE Study
SET title = 'Updated Study Title',
    description = 'Revised description for this study',
    platform = 'Web',
    status = 'COMPLETED'
WHERE study_id = 10;

/*
Java class: StudyService.java 
Java method: deleteStudy
Purpose: delete study by id
URL: DELETE /studies/10/
*/
DELETE FROM Study
WHERE study_id = 10;

/*
Java class: SubtaskService.java 
Java method: getNextSubtaskId
Purpose: get next subtask id by taxing MAX+1
*/
SELECT COALESCE(MAX(subtask_id), 0) + 1 AS next_id
FROM Subtask
WHERE task_id = 5;

/*
Java class: SubtaskService.java 
Java method: createSubtask
Purpose: insert new subtask
URL: POST /tasks/5/subtasks
*/
INSERT INTO Subtask (task_id, subtask_id, description, expected_comp_time)
VALUES (5, 3, 'Confirm shipping address', 30);

/*
Java class: SubtaskService.java 
Java method: getSubtaskByTaskId
Purpose: get all subtasks for a task ordered by subtask 
URL: GET /tasks/5/subtasks
*/
SELECT subtask_id,
       task_id,
       description,
       expected_comp_time
FROM Subtask
WHERE task_id = 5
ORDER BY subtask_id ASC; 

/*
Java class: SubtaskService.java 
Java method: getSubtaskByTaskId
Purpose: get subtask by id
URL: GET /tasks/5/subtasks/3
*/
SELECT subtask_id,
       task_id,
       description,
       expected_comp_time
FROM Subtask
WHERE task_id = 5
  AND subtask_id = 3;

/*
Java class: SubtaskService.java 
Java method: deleteSubtask
Purpose: delete subtask part of a task 
URL: DELETE /tasks/5/subtasks/3
*/
DELETE FROM Subtask
WHERE task_id = 5
  AND subtask_id = 3;


/*
Java class: TaskService.java 
Java method: createTask
Purpose: create new task for a study 
URL: POST /studies/10/tasks
*/
INSERT INTO Task (study_id, task_order, description, success_criteria, expected_comp_time)
VALUES (10,
        1,
        'Log in to the application',
        'User successfully logs in without errors',
        60);

/*
Java class: TaskService.java 
Java method: getTasksByStudy
Purpose: list tasks for a study
URL: GET /studies/10/tasks
*/
SELECT task_id,
       study_id,
       task_order,
       description,
       success_criteria,
       expected_comp_time
FROM Task
WHERE study_id = 10
ORDER BY task_order ASC;


/*
Java class: TaskService.java 
Java method: getTasksById
Purpose: list tasks for a id
URL: GET/tasks/5
*/
SELECT task_id,
       study_id,
       task_order,
       description,
       success_criteria,
       expected_comp_time
FROM Task
WHERE task_id = 5;

/*
Java class: TaskService.java 
Java method: deleteTask
Purpose: delete task by id
URL: DELETE /tasks/5
*/
DELETE FROM Task
WHERE task_id = 5;

/*
Java class: UserService.java 
Java method: authenticate
Purpose: get user and check their login credentials 
URL: POST /login
*/
SELECT user_id,
       fname,
       lname,
       email,
       password,
       registered
FROM Users
WHERE username = 'alice0';

/*
Java class: UserService.java 
Java method: regusterUser
Purpose: make new account w/ hashed password
URL: POST /signup
*/
INSERT INTO Users (fname, lname, username, password, email)
VALUES ('Alice', 'Nguyen', 'alice0',
        '$2a$10$exampleHashedPasswordValueHere', 'alice0@example.com');
