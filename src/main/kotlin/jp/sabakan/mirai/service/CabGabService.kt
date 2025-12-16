package jp.sabakan.mirai.service

import jp.sabakan.mirai.data.AnsweredCabGabData
import jp.sabakan.mirai.data.CabGabData
import jp.sabakan.mirai.entity.CabGabEntity
import jp.sabakan.mirai.repository.CabGabRepository
import jp.sabakan.mirai.request.CabGabRequest
import jp.sabakan.mirai.response.CabGabResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CabGabService {
    @Autowired
    lateinit var cabGabRepository: CabGabRepository

    /**
     * 指定カテゴリーのCabGab質問を取得する
     *
     * @param request CabGabリクエスト
     * @return CabGabレスポンス
     */
    fun getCabGabList(request: CabGabRequest): CabGabResponse {
        //リクエストからデータ変換
        val data = CabGabData()
        data.cabGabCategory = request.cabGabCategory

        //リポジトリへ問い合わせ
        val table: List<Map<String, Any?>> = cabGabRepository.getCabGab(data)
        val list: List<CabGabEntity> = tableToListEntity(table)

        //結果を返す
        return CabGabResponse().apply {
            cabGabs = list
            message = null
        }
    }

    /**
     * CabGab回答を登録する
     *
     * @param request CabGabリクエスト
     * @return CabGabレスポンス
     */
    fun insertCabGabAnswer(request: CabGabRequest): CabGabResponse {
        //リクエストからデータ変換
        val data = AnsweredCabGabData()
        data.cabgabResultId = request.cabgabResultId
        data.cabGabId = request.cabGabId
        data.userId = request.userId
        data.userAnswer = request.userAnswer
        data.correctAnswer = request.correctAnswer

        //リポジトリへ登録処理
        val update = cabGabRepository.insertCabGabAnswer(data)

        // 登録結果を確認
        if (update == 0) {
            return CabGabResponse().apply {
                message = "Failed to record Cab/Gab answer."
            }
        }

        // 回答率更新処理
        val answersString = cabGabRepository.createAnswer(data)
        val correctCount = answersString.count { it == 'T' }

        // パラメータマップの作成
        val rateUpdate = cabGabRepository.updateCabGabAnswerRate(data)

        // 登録結果を確認
        if (rateUpdate == 0) {
            val rateInsertCount  = cabGabRepository.insertCabGabAnswerRate(data)

            if (rateInsertCount == 0) {
                 throw Exception("Failed to insert Cab/Gab answer.")
            }
        } else if (rateUpdate == 1) {
            // 多分ありえないエラー
            throw RuntimeException("Failed to update Cab/Gab answer.")
        }

        //結果を返す
        return CabGabResponse().apply {
            message = "Cab/Gab answer recorded successfully."
        }
    }


    /**
     * テーブルデータをCabGabEntityリストに変換する
     *
     * @param table テーブルデータ
     * @return CabGabEntityリスト
     */
    fun tableToListEntity(table: List<Map<String, Any?>>): List<CabGabEntity> {
        val list: MutableList<CabGabEntity> = mutableListOf()
        for (row in table) {
            val entity = CabGabEntity().apply {
                cabGabId = row["cab_gab_id"] as String?
                cabGabContent = row["cab_gab_content"] as String?
                cabGabAnswer1 = row["cab_gab_answer_1"] as String?
                cabGabAnswer2 = row["cab_gab_answer_2"] as String?
                cabGabAnswer3 = row["cab_gab_answer_3"] as String?
                cabGabAnswer4 = row["cab_gab_answer_4"] as String?
                cabGabCorrectAnswer = (row["cab_gab_correct_answer"] as Number?)?.toInt()
                cabGabCategory = row["cab_gab_category"] as String?
            }
            list.add(entity)
        }
        return list
    }
}