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

    // 問題をカテゴリ別にランダムで取得するSQLクエリ
    val getRandomCabGabByCategory = """
        SELECT DISTINCT ON (cabgab_category) *
        FROM cabgab_t
        WHERE cabgab_category = :cabgabCategory
        ORDER BY cabgab_category, RANDOM()
    """.trimIndent()


    /**
     * 指定カテゴリのランダムなCabGab問題を取得する
     *
     * @param cabgabCategory CabGabカテゴリ
     * @return CabGab問題リスト
     */
    fun getRandomCabGab(data: CabGabData): List<Map<String, Any?>> {
        // パラメータマップの作成
        val paramMap = mapOf<String, Any?>(
            "cabgabCategory" to data.cabGabCategory
        )

        // クエリの実行
        return jdbc.queryForList(getRandomCabGabByCategory, paramMap)
    }
}