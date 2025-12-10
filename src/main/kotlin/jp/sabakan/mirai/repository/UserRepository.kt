package jp.sabakan.mirai.repository

import jp.sabakan.mirai.data.UserData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

/**
 * ユーザ情報リポジトリクラス
 */
@Repository
class UserRepository {
    @Autowired
    lateinit var njdbc: NamedParameterJdbcTemplate
    @Autowired
    lateinit var jdbc: JdbcTemplate

    // ユーザー情報をメールアドレスで取得するSQLクエリ
    val getUserOne = "SELECT * FROM user_m WHERE user_address = :userAddress"

    fun getUserLogin(data: UserData): List<Map<String, Any?>> {
        // パラメータマップの作成
        val paramMap = mapOf<String, Any?>(
            "userAddress" to data.userAddress
        )

        // クエリの実行
        return njdbc.queryForList(getUserOne, paramMap)
    }

}