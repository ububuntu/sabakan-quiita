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
