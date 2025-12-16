package jp.sabakan.mirai.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
    class EsController {

        // ESメイン画面
        @GetMapping("/es")
        fun getEs(): String{
            return "entrysheet/es-main"
        }

        // ES一覧画面
        @GetMapping("/es/list")
        fun getEsList(): String{
            return "entrysheet/es-list"
        }

        // ES作成画面
        @GetMapping("/es/creation")
        fun getEsCreation(): String{
            return "entrysheet/es-creation"
        }

        // ESメイン画面
        @PostMapping("/es")
        fun postEs(): String{
            return "entrysheet/es-main"
        }

        // ES一覧画面
        @PostMapping("/es/list")
        fun postEsList(): String{
            return "entrysheet/es-list"
        }

        // ES作成画面
        @PostMapping("/es/creation")
        fun postEsCreation(): String{
            return "entrysheet/es-creation"
        }
    }
