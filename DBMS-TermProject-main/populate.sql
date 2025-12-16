
-- USERS
INSERT INTO Users (fname, lname, username, email, password) VALUES
('Alice', 'Nguyen', 'Alice' , 'alice@example.com', '$2a$10$wMENSNuvz4vNSzXM.0364e6SHxbElaU81gPVu/7/R.ZXSPDAQG.rm'),
('Alex', 'John', 'Alex' , 'alex@example.com', '$2a$10$wMENSNuvz4vNSzXM.0364e6SHxbElaU81gPVu/7/R.ZXSPDAQG.rm'),
('Bob', 'Smith', 'Bob' , 'bob@example.com', '$2a$10$wMENSNuvz4vNSzXM.0364e6SHxbElaU81gPVu/7/R.ZXSPDAQG.rm');

-- STUDIES
INSERT INTO Study (user_id, title, description, platform, status) VALUES
(1, 'Mobile UX Study', 'Testing mobile app usability', 'Android', 'ACTIVE'),
(2, 'Web Navigation Study', 'Evaluating website navigation', 'Web', 'DRAFT');

-- PARTICIPANTS
INSERT INTO Participant (fname, lname, age, occupation, occupation_exp, email) VALUES
('Charlie', 'Lee', 28, 'Designer', '3 years', 'charlie@example.com'),
('Dana', 'Kim', 35, 'Developer', '5 years', 'dana@example.com');

-- TASKS
INSERT INTO Task (study_id, task_order, description, success_criteria, expected_comp_time) VALUES
(1, 1, 'Login to app', 'Successful login', 60),
(1, 2, 'Navigate to profile', 'Profile page loads', 90),
(2, 1, 'Find contact page', 'Contact page visible', 45);

-- SUBTASKS
INSERT INTO Subtask (task_id, subtask_id, description, expected_comp_time) VALUES
(1, 1, 'Enter username', 20),
(1, 2, 'Enter password', 20),
(1, 3, 'Click login', 20),
(2, 1, 'Open menu', 30),
(2, 2, 'Tap profile icon', 60);

-- SESSIONS
INSERT INTO Session (participant_id, study_id, scheduled, notes, score) VALUES
(1, 1, '2025-12-05 10:00:00', 'Initial run', 85),
(2, 2, '2025-12-06 14:00:00', 'Follow-up', 90);

-- RESULTS
INSERT INTO Result (session_id, task_id, is_complete, completion_time, errors, notes) VALUES
(1, 1, TRUE, 55, 0, 'Smooth login'),
(1, 2, TRUE, 80, 1, 'Minor delay'),
(2, 3, FALSE, 60, 2, 'Could not find page');
