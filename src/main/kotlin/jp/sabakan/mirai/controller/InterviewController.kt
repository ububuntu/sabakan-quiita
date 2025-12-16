package jp.sabakan.mirai.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class InterviewController {

    // 面接メイン画面
    @GetMapping("/interview")
    fun getInterview(): String {
        return "/interview/interview-main"
    }

    // Web面接画面
    @GetMapping("/interview/do")
    fun getInterviewDo(): String {
        return "/interview/interview-do"
    }

    //　面接ログ画面
    @GetMapping("/interview/log")
    fun getInterviewLog(): String {
        return "/interview/interview-log"
    }

    // 面接結果画面
    @GetMapping("/interview/result")
    fun getInterviewResult(): String {
        return "/interview/interview-result"
    }

    // 面接メイン画面
    @PostMapping("/interview")
    fun postInterview(): String {
        return "/interview/interview-main"
    }

    // Web面接画面
    @PostMapping("/interview/do")
    fun postInterviewDo(): String {
        return "/interview/interview-do"
    }

    // 面接ログ画面
    @PostMapping("/interview/log")
    fun postInterviewLog(): String {
        return "/interview/interview-log"
    }

    // 面接結果画面
    @PostMapping("/interview/result")
    fun postInterviewResult(): String {
        return "/interview/interview-result"
    }
}