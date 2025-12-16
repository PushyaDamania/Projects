-- ==========================================================
-- Drop tables (in FK-safe order)
-- ==========================================================
DROP TABLE IF EXISTS Result;
DROP TABLE IF EXISTS Subtask;
DROP TABLE IF EXISTS Session;
DROP TABLE IF EXISTS Task;
DROP TABLE IF EXISTS Participant;
DROP TABLE IF EXISTS Study;
DROP TABLE IF EXISTS Users;

-- ==========================================================
-- USER TABLE
-- ==========================================================
CREATE TABLE Users (
    user_id     INT AUTO_INCREMENT PRIMARY KEY,
    fname       VARCHAR(50) NOT NULL,
    lname       VARCHAR(50) NOT NULL,
    username    VARCHAR(50) NOT NULL UNIQUE,
    email       VARCHAR(100) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    registered  DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- ==========================================================
-- STUDY TABLE
-- ==========================================================
CREATE TABLE Study (
    study_id     INT AUTO_INCREMENT PRIMARY KEY,
    user_id      INT NOT NULL,
    title        VARCHAR(150) NOT NULL UNIQUE,
    description  TEXT,
    platform     VARCHAR(100),
    status       VARCHAR(50),
    created_at   DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

-- ==========================================================
-- PARTICIPANT TABLE
-- ==========================================================
CREATE TABLE Participant (
    participant_id   INT AUTO_INCREMENT PRIMARY KEY,
    fname            VARCHAR(50) NOT NULL,
    lname            VARCHAR(50) NOT NULL,
    age              INT,
    occupation       VARCHAR(100),
    occupation_exp   VARCHAR(100),
    email            VARCHAR(100)
);

-- ==========================================================
-- TASK TABLE
-- ==========================================================
CREATE TABLE Task (
    task_id             INT AUTO_INCREMENT PRIMARY KEY,
    study_id            INT NOT NULL,
    task_order          INT NOT NULL,
    description         TEXT NOT NULL,
    success_criteria    TEXT,
    expected_comp_time  INT,
    FOREIGN KEY (study_id) REFERENCES Study(study_id) ON DELETE CASCADE,
    UNIQUE (study_id, task_order)
);

-- ==========================================================
-- SUBTASK TABLE
-- ==========================================================
CREATE TABLE Subtask (
    task_id             INT NOT NULL,
    subtask_id          INT NOT NULL,
    description         TEXT,
    expected_comp_time  INT,
    PRIMARY KEY (task_id, subtask_id),
    FOREIGN KEY (task_id) REFERENCES Task(task_id) ON DELETE CASCADE
);

-- ==========================================================
-- SESSION TABLE
-- ==========================================================
CREATE TABLE Session (
    session_id      INT AUTO_INCREMENT PRIMARY KEY,
    participant_id  INT NOT NULL,
    study_id        INT NOT NULL,
    scheduled       DATETIME,
    notes           TEXT,
    score           INT,
    FOREIGN KEY (participant_id) REFERENCES Participant(participant_id) ON DELETE CASCADE,
    FOREIGN KEY (study_id) REFERENCES Study(study_id) ON DELETE CASCADE
);

-- ==========================================================
-- SESSION RESULT TABLE
-- ==========================================================
CREATE TABLE Result (
    result_id        INT AUTO_INCREMENT PRIMARY KEY,
    session_id       INT NOT NULL,
    task_id          INT NOT NULL,
    is_complete     BOOLEAN,
    completion_time  INT,
    errors           INT,
    notes            TEXT,
    FOREIGN KEY (session_id) REFERENCES Session(session_id) ON DELETE CASCADE,
    FOREIGN KEY (task_id) REFERENCES Task(task_id) ON DELETE CASCADE,
    UNIQUE (task_id, session_id)
);
