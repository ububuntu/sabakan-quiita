package jp.sabakan.mirai.data

import lombok.Data

@Data
class AnsweredCabGabData {
    // 回答ID
    var answerId: String? = null

    // セッションID
    var sessionId: String? = null

    // CAB/GAB受検ID
    var cabGabId: String? = null

    // ユーザID
    var userId: String? = null

    // ユーザの回答
    var userAnswer: Int? = null

    // 正誤フラグ
    var isCorrect: Boolean? = null

    // 回答日時
    var answeredAt: String? = null
}