--DROP DATABASE LearnLanguages;
CREATE DATABASE LearnLanguages
GO

USE LearnLanguages;
GO


CREATE TABLE users
(
    id BIGINT IDENTITY,
    username VARCHAR(255) NOT NULL,
    first_name VARCHAR(20),
    last_name VARCHAR(20),
    count_words INT,
    password VARCHAR(300),
    email VARCHAR(200),
    phone CHAR(11),
    CONSTRAINT PK_User
        PRIMARY KEY (id)
);

CREATE TABLE roles
(
    id TINYINT IDENTITY,
    name VARCHAR(100) NOT NULL
        UNIQUE,
    CONSTRAINT PK_RoleId
        PRIMARY KEY (id)
);

CREATE TABLE user_role
(
    user_id BIGINT,
    role_id TINYINT,
    CONSTRAINT FK_Userid
        FOREIGN KEY (user_id)
        REFERENCES dbo.users (id),
    CONSTRAINT FK_RoleId
        FOREIGN KEY (role_id)
        REFERENCES dbo.roles (id)
);

INSERT INTO dbo.roles
(
    name
)
VALUES
('ROLE_ADMIN' -- name - varchar(100)
    );
INSERT INTO dbo.roles
(
    name
)
VALUES
('ROLE_USER' -- name - varchar(100)
    );
INSERT INTO dbo.roles
(
    name
)
VALUES
('ROLE_MANAGEMENT' -- name - varchar(100)
    );

/*
INSERT dbo.users
(
	
    first_name,
    last_name,
    count_words,
    password,
    email,
    phone,
	username,
)
VALUES
(   'bao1', -- first_name - varchar(20)
    'nguyen1', -- last_name - varchar(20)
    0, -- count_words - int
    123, -- password - varchar(300)
    'bao@gmail.com', -- email - varchar(200)
    '078804'  -- phone - char(11)
    )
*/

/*
CREATE TABLE TypeTest(
	id SMALLINT IDENTITY,
	tag_code VARCHAR(20),
	description VARCHAR(200)
	CONSTRAINT PK_TypeTest PRIMARY KEY(id)
)
*/

CREATE TABLE vocabulary
(
    id BIGINT IDENTITY,
    word VARCHAR(255),
    meaning_word NVARCHAR(255),
    type VARCHAR(100),
    sentence TEXT,
    IPA NVARCHAR(255),
    meaning_sentence NTEXT,
    audio_word VARCHAR(255),
    audio_sentence VARCHAR(255),
    added_date DATETIME,
    img VARCHAR(255),
    CONSTRAINT PK_NewWord
        PRIMARY KEY (id)
);
ALTER TABLE dbo.vocabulary
ADD topic_id INT,
    CONSTRAINT FK_Topic
    FOREIGN KEY (topic_id)
    REFERENCES dbo.topic;


CREATE TABLE user_vocabulary
(
    id_user BIGINT,
    id_vocabulary BIGINT,
    submit_date DATETIME,
    current_FE DECIMAL(4,3),
    count_false SMALLINT
        DEFAULT 0,
    time_repetition INT
        DEFAULT 1,
		rate_q TINYINT DEFAULT 5,
    CONSTRAINT PK_Clustered
        PRIMARY KEY (
                        id_user,
                        id_vocabulary
                    ),
    CONSTRAINT FK_User
        FOREIGN KEY (id_user)
        REFERENCES dbo.users (id),
    CONSTRAINT FK_NewWord
        FOREIGN KEY (id_vocabulary)
        REFERENCES dbo.vocabulary (id),
);


CREATE TABLE course
(
    id INT IDENTITY,
    title NVARCHAR(255),
    target NVARCHAR(255),
    description NTEXT,
    img VARCHAR(255),
    CONSTRAINT PK_CourseId
        PRIMARY KEY (id)
);

CREATE TABLE topic
(
    id INT IDENTITY,
    title_en VARCHAR(255),
    title_vn NVARCHAR(255),
    id_course INT,
    img VARCHAR(255),
    CONSTRAINT FK_Course
        FOREIGN KEY (id_course)
        REFERENCES dbo.course (id),
    CONSTRAINT PK_Topic
        PRIMARY KEY (id)
);


INSERT dbo.user_vocabulary
(
    id_user,
    id_vocabulary,
    submit_date,
    current_FE,
    end_date,
    count_false,
    time_repetition
)
VALUES
(   2,       -- id_user - bigint
    19,      -- id_vocabulary - bigint
    NULL,    -- submit_date - datetime
    NULL,    -- current_FE - decimal(2, 2)
    NULL,    -- end_date - datetime
    DEFAULT, -- count_false - smallint
    DEFAULT  -- time_repetition - int
    );

/**
	 2 loại question đưa ra cấu tiếng anh
chọn đáp án dịch nghĩa từ được gạch dưới

đưa ra câu tiếng anh
chọn từ tiếng anh còn khuyết

	*/
CREATE TABLE question
(
    id BIGINT IDENTITY,
    question NVARCHAR(255),
    right_answer_id BIGINT,
    id_vocabulary BIGINT,
    CONSTRAINT PK_Question
        PRIMARY KEY (id),
    CONSTRAINT FK_Vocabulary
        FOREIGN KEY (id_vocabulary)
        REFERENCES dbo.vocabulary (id),
);


CREATE TABLE answer
(
    id BIGINT IDENTITY,
    answer NVARCHAR(255),
    id_question BIGINT,
    CONSTRAINT PK_Answer
        PRIMARY KEY (id),
    CONSTRAINT FK_Question
        FOREIGN KEY (id_question)
        REFERENCES dbo.question (id)
);

ALTER TABLE question
ADD CONSTRAINT FK_Anwer
    FOREIGN KEY (right_answer_id)
    REFERENCES dbo.answer (id);


SELECT v.*
FROM dbo.vocabulary v
WHERE v.id NOT IN
      (
          SELECT uv1.id_vocabulary
          FROM dbo.user_vocabulary uv1
          WHERE uv1.id_user = 1
      );

SELECT uv1.id_vocabulary
FROM dbo.user_vocabulary uv1
WHERE uv1.id_user = 2;

SELECT t1_0.course_id,
       t1_1.id,
       c1_0.id,
       c1_0.description,
       c1_0.img,
       c1_0.target,
       c1_0.title,
       t1_1.title_en,
       t1_1.title_vn
FROM course_topic t1_0
    JOIN topic t1_1
        ON t1_1.id = t1_0.topic_id
    LEFT JOIN course c1_0
        ON c1_0.id = t1_1.course_id;
