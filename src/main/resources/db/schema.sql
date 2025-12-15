/* ユーザマスタ */
CREATE TABLE user_m(
    user_id         CHAR(20) PRIMARY KEY NOT NULL UNIQUE ,
    user_name       VARCHAR(50) NOT NULL ,
    user_address    VARCHAR(50) NOT NULL ,
    password        VARCHAR(255) NOT NULL ,
    user_role       VARCHAR(5) NOT NULL ,
    user_valid      BOOLEAN NOT NULL ,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
    lasted_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

/* 面接テーブル */
CREATE TABLE interview_t(
    interview_id            CHAR(20) PRIMARY KEY NOT NULL UNIQUE ,
    user_id                 CHAR(20) NOT NULL ,
    interview_expression    INT,
    interview_eyes          INT,
    interview_posture       INT,
    interview_voice         INT,
    interview_date          DATE,
    interview_score         INT,
    FOREIGN KEY (user_id) REFERENCES user_m(user_id)
);

/* ESテーブル */
CREATE TABLE es_t(
    es_id                   CHAR(20) PRIMARY KEY NOT NULL UNIQUE ,
    user_id                 CHAR(20) NOT NULL ,
    es_content_reason       VARCHAR(500),
    es_content_selfpr       VARCHAR(500),
    es_content_activities   VARCHAR(500),
    es_content_stwe         VARCHAR(500),
    es_occupation           VARCHAR(100),
    es_date                 DATE,
    FOREIGN KEY (user_id) REFERENCES user_m(user_id)
);

/* SPIテーブル */
CREATE TABLE spi_t(
    spi_id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY ,
    spi_content         VARCHAR(255),
    spi_answer1         VARCHAR(100),
    spi_answer2         VARCHAR(100),
    spi_answer3         VARCHAR(100),
    spi_answer4         VARCHAR(100),
    spi_correct_answer  INT,
    spi_category        VARCHAR(50)
);



/* CAB/GABテーブル */
CREATE TABLE cabgab_t(
    cabgab_id               BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY ,
    cabgab_content          VARCHAR(255),
    cabgab_answer1          VARCHAR(100),
    cabgab_answer2          VARCHAR(100),
    cabgab_answer3          VARCHAR(100),
    cabgab_answer4          VARCHAR(100),
    cabgab_correct_answer   CHAR(1) DEFAULT NULL ,
    cabgab_category         VARCHAR(50)
);

/* 回答テーブル */
CREATE TABLE cabgab_answer_t(
    cabgab_answer_id    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY ,
    user_id             CHAR(20) NOT NULL ,
    cabgab_id          BIGINT NOT NULL ,
    cabgab_user_answer         CHAR(1) DEFAULT NULL ,
    is_correct         BOOLEAN DEFAULT NULL ,
    answer_date        TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
    FOREIGN KEY (user_id) REFERENCES user_m(user_id),
    FOREIGN KEY (cabgab_id) REFERENCES cabgab_t(cabgab_id)
);

/* 正答率テーブル */
CREATE TABLE cabgab_rate_t(
    cabgab_rate_id     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY ,
    user_id            CHAR(20) NOT NULL ,
    cabgab_id         BIGINT NOT NULL ,
    cabgab_answers      CHAR(3) DEFAULT NULL,
    total_count        INT DEFAULT 0 ,
    lasted_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
    FOREIGN KEY (user_id) REFERENCES user_m(user_id)
);