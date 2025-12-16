package jp.sabakan.mirai.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class UserController {

    // ユーザーメイン画面
    @GetMapping("/user")
    fun getUser(): String {
        return "users/user-main"
    }

    // パスワード変更画面
    @GetMapping("/user/repassword")
    fun getRepassword(): String {
        return "users/user-repassword"
    }

    // ユーザー目標設定画面
    @GetMapping("/user/target")
    fun getTarget(): String {
        return "users/user-target"
    }

    // ユーザーメイン画面
    @PostMapping("/user")
    fun postUser(): String{
        return "users/user-main"
    }

    // パスワード変更画面
    @PostMapping("/user/repassword")
    fun postRepassword(): String{
        return "users/user-repassword"
    }

    // ユーザー目標設定画面
    @PostMapping("/user/target")
    fun postTarget(): String{
        return "users/user-target"
    }
}