package jp.sabakan.mirai.repository

import jp.sabakan.mirai.data.AnsweredCabGabData
import jp.sabakan.mirai.data.CabGabData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class CabGabRepository {
    @Autowired
    lateinit var jdbc: JdbcTemplate

    // カテゴリーごとにCabGab質問を取得するSQLクエリ
    val getCabGabByCategory = """
        SELECT * FROM cabgab_t
        WHERE cabgab_category = :cabgabCategory
        ORDER BY RANDOM() LIMIT 1
    """.trimIndent()

    // 回答結果を保存するSQLクエリ
    val insertCabGabAnswer = """
        INSERT INTO cabgab_result_t (cabgab_result_id, cabgab_id, user_id, cabgab_user_answer, cabgab_correct_answer, cabgab_answer_at)
        VALUES (:cabgabResultId, :cabgabId, :userId, :cabgabUserAnswer, :cabgabCorrectAnswer, CURRENT_TIMESTAMP)
    """.trimIndent()

    // 回答内容に基づき回答率を更新するSQLクエリ
    val updateCabGabAnswerRate = """
        UPDATE cabgab_rate_t
        SET cabgab_answers = :cabgabAnswers,
            cabgab_count = :cabgabCount
        WHERE cabgab_id = :cabgabId AND user_id = :userId
    """.trimIndent()

    // 直近3回分の回答を得るSQLクエリ
    val getCabGabAnswers = """
        SELECT cabgab_answer, cabgab_correct_answer FROM cabgab_result_t
        WHERE cabgab_id = :cabgabId AND user_id = :userId
        ORDER BY cabgab_answer_at DESC LIMIT 3
    """.trimIndent()

    // 回答率テーブルに新規登録するSQLクエリ
    val insertCabGabAnswerRate = """
        INSERT INTO cabgab_rate_t (cabgab_id, user_id, cabgab_answers, cabgab_count)
        VALUES (:cabgabId, :userId, :cabgabAnswers, :cabgabCount)
    """.trimIndent()

    /**
     * 指定カテゴリーのCabGab質問を取得する
     *
     * @param cabGabCategory 問題種別
     * @return CabGab質問リスト
     */
    fun getCabGab(data: CabGabData): List<Map<String, Any?>>{
        // パラメータマップの作成
        val paramMap = mapOf<String, Any?>(
            "cabgabCategory" to data.cabGabCategory
        )

        // クエリの実行
        return jdbc.queryForList(getCabGabByCategory, paramMap)
    }

    /**
     * CabGabの回答結果を保存する
     *
     * @param data 回答データ
     * @return 更新件数
     */
    fun insertCabGabAnswer(data: AnsweredCabGabData): Int {
        // パラメータマップの作成
        val paramMap = mapOf<String, Any?>(
            "cabgabResultId" to data.cabgabResultId,
            "cabgabId" to data.cabGabId,
            "userId" to data.userId,
            "cabgabUserAnswer" to data.userAnswer,
            "cabgabCorrectAnswer" to data.correctAnswer
        )

        // クエリの実行
        return jdbc.update(insertCabGabAnswer, paramMap)
    }

    /**
     * CabGabの回答率を更新する
     *
     * @param data 回答データ
     * @return 更新件数
     */
    fun updateCabGabAnswerRate(data: AnsweredCabGabData): Int {
        // パラメータマップの作成
        val temp = createAnswer(data)
        val paramMap = mapOf<String, Any?>(
            "cabgabAnswers" to temp ,
            "cabgabCount" to temp.count{ it == 'T' } ,
            "cabgabId" to data.cabGabId ,
            "userId" to data.userId
        )

        // クエリの実行
        return jdbc.update(updateCabGabAnswerRate, paramMap)
    }

    /**
     * 回答履歴文字列を作成する
     *
     * @param data 回答データ
     * @return 回答履歴文字列
     */
     fun createAnswer(data: AnsweredCabGabData): String {
        // パラメータマップの作成
        val paramMap = mapOf<String, Any?>(
            "cabgabId" to data.cabGabId,
            "userId" to data.userId
        )

        // クエリの実行
        val answers = jdbc.queryForList(getCabGabAnswers, paramMap)

        // 回答の正誤を判定
        val correctResult = if (data.userAnswer == data.correctAnswer) "T" else "F"

        // 回答の正当性を評価
        val history = answers.map {
            val userAnswer = it["cabgab_answer"] as? String
            val correctAnswer = it["cabgab_correct_answer"] as? String
            when {
                userAnswer == null || correctAnswer == null -> "N"
                userAnswer == correctAnswer -> "T"
                else -> "F"
            }
        }

        // 新しい順に連結して返す
        val newHistory = listOf(correctResult) + history
        return newHistory.take(3).joinToString("")
    }

    /**
     * CabGabの回答率を新規登録する
     *
     * @param data 回答データ
     * @return 更新件数
     */
    fun insertCabGabAnswerRate(data: AnsweredCabGabData): Int {
        // パラメータマップの作成
        val temp = createAnswer(data)
        val paramMap = mapOf<String, Any?>(
            "cabgabId" to data.cabGabId ,
            "userId" to data.userId ,
            "cabgabAnswers" to temp ,
            "cabgabCount" to temp.count{ it == 'T' }
        )

        // クエリの実行
        return jdbc.update(insertCabGabAnswerRate, paramMap)
    }
}