package jp.sabakan.mirai.component

import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.RestClientException
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.client.getForEntity
import java.util.concurrent.CompletableFuture

/**
 * DockerコンテナでホストされているAI分析サーバーとの通信を行うクライアント。
 *
 * このクライアントは、面接分析用のREST APIエンドポイントとの非同期通信を提供します。
 * 全てのメソッドはCompletableFutureを返し、非ブロッキングな操作を可能にします。
 *
 * @property baseUrl AI分析サーバーのベースURL（デフォルト: http://192.168.1.100:5000）
 * @constructor baseUrlを指定してDockerAIClientのインスタンスを作成します
 */
@Component
class DockerAIClient(
    private val baseUrl: String = "http://192.168.1.100:5000"
) {
    private val restTemplate = RestTemplate()

    /**
     * AI分析サーバーへの接続をテストします。
     *
     * サーバーが起動しており、到達可能かどうかを確認します。
     *
     * @return 接続成功時はtrue、失敗時はfalseを含むCompletableFuture
     */
    fun testConnection(): CompletableFuture<Boolean> {
        return CompletableFuture.supplyAsync {
            try {
                restTemplate.getForEntity<String>(baseUrl)
                true
            } catch (_: Exception) {
                false
            }
        }
    }

    /**
     * 面接分析セッションを開始します。
     *
     * サーバー側で新しい分析セッションを初期化し、フレーム分析の準備を行います。
     *
     * @return 開始成功時はtrue、失敗時はfalseを含むCompletableFuture
     */
    fun startAnalysis(): CompletableFuture<Boolean> {
        return post("/interview/start")
    }

    /**
     * Base64エンコードされた画像フレームを分析のためにサーバーへ送信します。
     *
     * @param base64 Base64エンコードされた画像データ
     * @return 送信成功時はtrue、失敗時はfalseを含むCompletableFuture
     */
    fun analyzeFrame(base64: String): CompletableFuture<Boolean> {
        val body = mapOf("image" to base64)
        return post("/interview/analyze", body)
    }

    /**
     * Base64エンコードされた音声データを分析のためにサーバーへ送信します。
     *
     * 音声ファイルをBase64形式でエンコードし、AI分析サーバーに送信して
     * 音声分析を実行します。音声データは面接中の発話内容や声のトーン、
     * 感情分析などに使用される可能性があります。
     *
     * @param base64Audio Base64エンコードされた音声データ（WAV、MP3などの形式）
     * @return 送信成功時はtrue、失敗時はfalseを含むCompletableFuture
     * @see analyzeFrame 画像フレーム分析メソッド
     */
    fun analyzeAudio(base64Audio: String): CompletableFuture<Boolean> {
        val body = mapOf("audio" to base64Audio)
        return post("/interview/analyze-audio", body)
    }

    /**
     * サーバーから処理済みの音声データまたは音声分析結果を取得します。
     *
     * AI分析サーバーが生成した音声ファイル（フィードバック音声、
     * 合成音声など）や、音声分析の結果をバイナリ形式で取得します。
     * 取得したデータは、アプリケーション側で再生や保存が可能です。
     *
     * @return 音声データのバイト配列、またはエラー時・データが存在しない場合はnullを含むCompletableFuture
     * @throws RestClientException ネットワークエラーやサーバーエラーが発生した場合
     * @see stopAnalysis 分析結果取得メソッド
     */
    fun getAudioResult(): CompletableFuture<ByteArray?> {
        return CompletableFuture.supplyAsync {
            try {
                val response = restTemplate.getForEntity<ByteArray>(
                    "$baseUrl/interview/audio"
                )
                response.body
            } catch (_: Exception) {
                null
            }
        }
    }

    /**
     * 面接分析セッションを停止し、分析結果を取得します。
     *
     * サーバー側で分析を終了し、これまでに収集されたデータに基づく
     * 分析結果を返します。
     *
     * @return 分析結果のJSON文字列、またはエラー時はnullを含むCompletableFuture
     */
    fun stopAnalysis(): CompletableFuture<String?> {
        return CompletableFuture.supplyAsync {
            try {
                val headers = HttpHeaders().apply {
                    contentType = MediaType.APPLICATION_JSON
                }
                val request = HttpEntity(mapOf<String, String>(), headers)
                val response = restTemplate.postForEntity(
                    "$baseUrl/interview/stop",
                    request,
                    String::class.java
                )
                response.body
            } catch (_: Exception) {
                null
            }
        }
    }

    /**
     * サーバー側の分析状態をリセットします。
     *
     * これまでに蓄積された分析データをクリアし、
     * 新しい分析セッションを開始できる状態に戻します。
     *
     * @return リセット成功時はtrue、失敗時はfalseを含むCompletableFuture
     */
    fun reset(): CompletableFuture<Boolean> {
        return post("/interview/reset")
    }

    /**
     * 指定されたパスに対してPOSTリクエストを送信します。
     *
     * 内部的に使用されるヘルパーメソッドで、共通のPOSTリクエスト処理を実装します。
     *
     * @param path APIエンドポイントのパス（baseUrlからの相対パス）
     * @param body リクエストボディとして送信するキー・バリューマップ（デフォルト: 空のマップ）
     * @return HTTPステータスコードが2xxの場合true、それ以外はfalseを含むCompletableFuture
     */
    private fun post(
        path: String,
        body: Map<String, String> = emptyMap()
    ): CompletableFuture<Boolean> {
        return CompletableFuture.supplyAsync {
            try {
                val headers = HttpHeaders().apply {
                    contentType = MediaType.APPLICATION_JSON
                }
                val request = HttpEntity(body, headers)
                val response = restTemplate.postForEntity(
                    "$baseUrl$path",
                    request,
                    String::class.java
                )
                response.statusCode.is2xxSuccessful
            } catch (_: RestClientException) {
                false
            }
        }
    }
}

/**
 * AI分析機能のREST APIエンドポイントを提供するコントローラー。
 *
 * このコントローラーは、DockerAIClientをラップし、HTTP経由で
 * AI分析機能にアクセスするためのAPIを提供します。
 * 分析状態とフレーム数を内部的に管理します。
 *
 * @property client DockerAIClientのインスタンス
 * @constructor clientを依存性注入によって受け取り、AIAnalysisControllerのインスタンスを作成します
 */
@org.springframework.web.bind.annotation.RestController
@org.springframework.web.bind.annotation.RequestMapping("/api")
class AIAnalysisController(private val client: DockerAIClient) {

    /**
     * 現在の分析状態を示す文字列。
     * 可能な値: "待機中", "✅接続成功", "❌接続失敗", "分析中", "完了", "エラー"
     */
    private var status = "待機中"

    /**
     * これまでに処理されたフレーム数のカウンター。
     */
    private var frames = 0

    /**
     * 現在の分析状態とフレーム数を取得します。
     *
     * @return status（状態）とframes（フレーム数）を含むマップ
     */
    @org.springframework.web.bind.annotation.GetMapping("/status")
    fun getStatus() = mapOf(
        "status" to status,
        "frames" to frames
    )

    /**
     * AI分析サーバーへの接続をテストします。
     *
     * 接続結果に応じて内部状態を更新します。
     *
     * @return success（成功フラグ）とstatus（更新後の状態）を含むマップのCompletableFuture
     */
    @org.springframework.web.bind.annotation.PostMapping("/test")
    fun testConnection(): CompletableFuture<Map<String, Any>> {
        return client.testConnection().thenApply {
            status = if (it) "✅接続成功" else "❌接続失敗"
            mapOf("success" to it, "status" to status)
        }
    }

    /**
     * 面接分析を開始します。
     *
     * 成功した場合、内部状態を"分析中"に更新します。
     *
     * @return success（成功フラグ）とstatus（更新後の状態）を含むマップのCompletableFuture
     */
    @org.springframework.web.bind.annotation.PostMapping("/start")
    fun startAnalysis(): CompletableFuture<Map<String, Any>> {
        return client.startAnalysis().thenApply {
            if (it) status = "分析中"
            mapOf("success" to it, "status" to status)
        }
    }

    /**
     * 面接分析を停止し、結果を取得します。
     *
     * 結果の取得に成功した場合は状態を"完了"に、
     * 失敗した場合は"エラー"に更新します。
     *
     * @return result（分析結果）とstatus（更新後の状態）を含むマップのCompletableFuture
     */
    @org.springframework.web.bind.annotation.PostMapping("/stop")
    fun stopAnalysis(): CompletableFuture<Map<String, Any?>> {
        return client.stopAnalysis().thenApply {
            status = if (it != null) "完了" else "エラー"
            mapOf("result" to it, "status" to status)
        }
    }

    /**
     * 分析状態をリセットします。
     *
     * フレームカウンターを0にリセットし、状態を"待機中"に戻します。
     *
     * @return success（成功フラグ）、status（更新後の状態）、frames（リセット後のフレーム数）を含むマップのCompletableFuture
     */
    @org.springframework.web.bind.annotation.PostMapping("/reset")
    fun reset(): CompletableFuture<Map<String, Any>> {
        return client.reset().thenApply {
            frames = 0
            status = "待機中"
            mapOf("success" to it, "status" to status, "frames" to frames)
        }
    }

    /**
     * Base64エンコードされた音声データを分析のために送信します。
     *
     * リクエストボディから音声データを受け取り、AI分析サーバーへ送信します。
     * 音声データは発話内容、声のトーン、感情分析などに使用されます。
     *
     * @param audioData Base64エンコードされた音声データを含むリクエストボディ
     * @return success（成功フラグ）とstatus（更新後の状態）を含むマップのCompletableFuture
     */
    @org.springframework.web.bind.annotation.PostMapping("/analyze-audio")
    fun analyzeAudio(
        @org.springframework.web.bind.annotation.RequestBody audioData: Map<String, String>
    ): CompletableFuture<Map<String, Any>> {
        val base64Audio = audioData["audio"] ?: ""
        return client.analyzeAudio(base64Audio).thenApply {
            if (it) status = "音声分析中"
            mapOf("success" to it, "status" to status)
        }
    }

    /**
     * サーバーから処理済みの音声データを取得します。
     *
     * AI分析サーバーが生成した音声ファイル（フィードバック音声、合成音声など）を
     * バイナリ形式で取得します。取得したデータはクライアント側で再生や保存が可能です。
     *
     * @return 音声データのバイト配列を含むResponseEntity、またはエラー時は404ステータス
     */
    @org.springframework.web.bind.annotation.GetMapping("/audio-result")
    fun getAudioResult(): CompletableFuture<org.springframework.http.ResponseEntity<ByteArray>> {
        return client.getAudioResult().thenApply { audioData ->
            if (audioData != null) {
                val headers = HttpHeaders().apply {
                    contentType = MediaType.parseMediaType("audio/mpeg")
                    contentLength = audioData.size.toLong()
                }
                org.springframework.http.ResponseEntity.ok()
                    .headers(headers)
                    .body(audioData)
            } else {
                org.springframework.http.ResponseEntity.notFound().build()
            }
        }
    }
}