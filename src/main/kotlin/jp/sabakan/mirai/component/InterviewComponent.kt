package jp.sabakan.mirai.component

import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import org.springframework.web.client.postForEntity
import java.lang.Exception

@Component
class InterviewComponent(
    private val restTemplate: RestTemplate
) {
    private val url = "http://192.168.1.100:5000"

    private inline fun <T> safeCall(default: T, block: () -> T): T {
        return try {
            block()
        } catch (e: Exception) {
            default
        }
    }

    /**
     * 面接分析サービスへの接続確認
     */
    fun testConnection(): Boolean = safeCall(false) {
        restTemplate.getForEntity<String>(url)
        true
    }

    /**
     * 面接分析開始指示
     */
    fun startAnalysis(): Boolean = safeCall(false) {
        restTemplate.getForEntity<String>("$url/interview/start")
        true
    }

    /**
     * フレーム画像の送信と分析指示
     */
    fun analyzeFrame(base64: String): Boolean = safeCall(false) {
        val request = mapOf("base64" to base64)
        restTemplate.postForEntity<String>("$url/interview/frame", request)
        true
    }

    /**
     * 音声分析結果の取得
     */
    fun getAudioAnalysis(): String? = safeCall(null) {
        restTemplate.getForEntity<String>("$url/interview/audio").body
    }

    /**
     * 面接分析停止指示
     */
    fun stopAnalysis(): Boolean = safeCall(false) {
        restTemplate.getForEntity<String>("$url/interview/stop")
        true
    }

    /**
     * 面接分析データリセット指示
     */
    fun resetAnalysis(): Boolean = safeCall(false) {
        restTemplate.getForEntity<String>("$url/interview/reset")
        true
    }

    /**
     * 汎用POSTリクエスト送信
     */
    fun post(path: String, body: Any? = null): Boolean = safeCall(false) {
        restTemplate.postForEntity<String>("$url$path", body)
        true
    }
}
