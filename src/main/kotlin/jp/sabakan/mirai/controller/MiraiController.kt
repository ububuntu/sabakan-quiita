package jp.sabakan.mirai.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class MiraiController {

    // ホーム画面
    @GetMapping("/index")
    fun getIndex(): String {
        return "/index"
    }

    // ホーム画面
    @PostMapping("/index")
    fun postIndex(): String {
        return "/index"
    }
}