package jp.sabakan.mirai.entity

import lombok.Data
import java.util.Date

@Data
class UserEntity {
    // ユーザID
    var userId: String? = null

    // ユーザネーム
    var userName: String? = null

    // ユーザアドレス
    var userAddress: String? = null

    // パスワード
    var password: String? = null

    // 権限
    var userRole: String? = null

    // 有効性
    var isValid: Boolean? = null

    // 登録日時
    var createdAt: Date? = null

    // 最終ログイン日時
    var lastedAt: Date? = null
}