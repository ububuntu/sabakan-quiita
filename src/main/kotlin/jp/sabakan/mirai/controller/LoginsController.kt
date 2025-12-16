package jp.sabakan.mirai.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LoginsController {

    // ログイン画面
    @GetMapping("/signin")
    fun signIn(model: Model): String {
        return "logins/sign-in"
    }

    // 新規登録画面
    @GetMapping("/signup")
    fun signUp(model: Model): String {
        return "logins/sign-up"
    }

}