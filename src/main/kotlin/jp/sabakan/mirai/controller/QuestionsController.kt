package jp.sabakan.mirai.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
    class QuestionsController {

        // 適性試験メイン画面
        @GetMapping("/questions")
        fun getQuestions(): String{
            return "questions/questions-main"
        }

        //　SPIメイン画面
        @GetMapping("/spi")
        fun getQuestionSpiMain(): String{
            return "questions/spi-main"
        }

        // SPI問題画面
        @GetMapping("/spi/study")
        fun getQuestionSpiStudy(): String{
            return "questions/spi-study"
        }

        // SPI結果画面
        @GetMapping("/spi/result")
        fun getQuestionSpiResult(): String{
            return "questions/spi-result"
        }

        // CAB/GABメイン画面
        @GetMapping("/cabgab")
        fun getQuestionCabgabMain(): String{
            return "questions/cabgab-main"
        }

        // CAB/GAB問題画面
        @GetMapping("/cabgab/study")
        fun getQuestionCabgabStudy(): String{
            return "questions/cabgab-study"
        }

        // CAB/GAB結果画面
        @GetMapping("/cabgab/result")
        fun getQuestionCabgabResult(): String{
            return "questions/cabgab-result"
        }

        // 適性試験メイン画面
        @PostMapping("/questions")
        fun postQuestions(): String{
            return "questions/questions-main"
        }

        // SPIメイン画面
        @PostMapping("/spi")
        fun postQuestionSpiMain(): String{
            return "questions/spi-main"
        }

        // SPI問題画面
        @PostMapping("/spi/study")
        fun postQuestionSpiStudy(): String{
            return "questions/spi-study"
        }

        // SPI結果画面
        @PostMapping("/spi/result")
        fun postQuestionSpiResult(): String{
            return "questions/spi-result"
        }

        // CAB/GABメイン画面
        @PostMapping("/cabgab")
        fun postQuestionCabgabMain(): String{
            return "questions/cabgab-main"
        }

        // CAB/GAB問題画面
        @PostMapping("/cabgab/study")
        fun postQuestionCabgabStudy(): String{
            return "questions/cabgab-study"
        }

        //　CAB/GAB結果画面
        @PostMapping("/cabgab/result")
        fun postQuestionCabgabResult(): String{
            return "questions/cabgab-result"
        }

    }