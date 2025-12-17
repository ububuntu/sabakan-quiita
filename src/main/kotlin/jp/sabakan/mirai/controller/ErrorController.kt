
package jp.sabakan.mirai.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping


@Controller
class ErrorController {

    // 共通のエラー画面
    @GetMapping("/errors")
    fun getError(): String {
        return "error/error"
    }

    // 404エラー画面
    @GetMapping("/error/404")
    fun get404Error(): String {
        return "error/404-error"
    }

    // 500エラー画面
    @GetMapping("/error/500")
    fun get500Error(): String {
        return "error/500-error"
    }

    // 共通のエラー画面
    @PostMapping("/errors")
    fun postError(): String {
        return "error/error"
    }

    // 404エラー画面
    @PostMapping("/error/404")
    fun post404Error(): String {
        return "error/404-error"
    }

    // 500エラー画面
    @PostMapping("/error/500")
    fun post500Error(): String {
        return "error/500-error"
    }
}