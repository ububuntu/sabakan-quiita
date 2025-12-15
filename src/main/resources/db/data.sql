-- ユーザマスタ
INSERT INTO user_m (
    user_id, user_name, user_address, password, user_role, user_valid, created_at, lasted_at
) VALUES (
    'U001', 'テスト太郎', '北海道札幌市', 'password123', 'ADMIN', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

-- 面接テーブル
INSERT INTO interview_t (
    interview_id, user_id, interview_eyes, interview_posture, interview_voice, interview_date, interview_score
) VALUES (
    'I001', 'U001', 4, 5, 3, CURRENT_DATE, 12
);

-- ESテーブル
INSERT INTO es_t (
    es_id, user_id, es_content_reason, es_content_selfpr, es_content_activities, es_content_stwe, es_occupation, es_date
) VALUES (
    'E001', 'U001', '志望動機の例', '自己PRの例', '学生時代の活動例', 'ストレス耐性の例', 'エンジニア', CURRENT_DATE
);

-- SPIテーブル
INSERT INTO spi_t (
    spi_id, spi_content, spi_answer1, spi_answer2, spi_answer3, spi_answer4, spi_correct_answer, spi_category
) VALUES (
    'S001', '1+1は？', '1', '2', '3', '4', 2, '計算'
);

-- SPI結果テーブル
INSERT INTO spi_result_t (
    spi_result_id, user_id, spi_id, spi_user_answer, spi_is_correct, spi_answered_at
) VALUES (
    'SR001', 'U001', 'S001', 2, TRUE, CURRENT_TIMESTAMP
);

-- CAB/GABテーブル
INSERT INTO cabgab_t (
    cabgab_id, cabgab_content, cabgab_answer1, cabgab_answer2, cabgab_answer3, cabgab_answer4, cabgab_correct_answer, cabgab_category
) VALUES (
    'C001', '図形の回転問題', 'A', 'B', 'C', 'D', 3, '空間把握'
);

-- CAB/GAB結果テーブル
INSERT INTO cabgab_result_t (
    cabgab_result_id, user_id, cabgab_id, cabgab_user_answer, cabgab_is_correct, cabgab_answered_at
) VALUES (
    'CR001', 'U001', 'C001', 3, TRUE, CURRENT_TIMESTAMP
);
