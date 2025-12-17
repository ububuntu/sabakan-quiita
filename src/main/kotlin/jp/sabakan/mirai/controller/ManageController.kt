package jp.sabakan.mirai.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class ManageController {

    // 管理メイン画面
    @GetMapping("/manage")
    fun getManage(): String {
        return "/manage/manage-main"
    }

    // ユーザー管理画面
    @GetMapping("/manage/users")
    fun getManageUsers(): String {
        return "/manage/users/manage-users-main"
    }

    // ユーザー情報変更画面
    @GetMapping("/manage/users/edit")
    fun getManageUsersEdit(): String {
        return "/manage/users/manage-users-edit"
    }

    // ユーザー追加画面
    @GetMapping("/manage/users/add")
    fun getManageUsersAdd(): String {
        return "/manage/users/manage-users-add"
    }

    // SPIメイン画面
    @GetMapping("/manage/spi")
    fun getManageSpi(): String {
        return "/manage/spi/manage-spi-main"
    }

    // SPI変更画面
    @GetMapping("/manage/spi/edit")
    fun getManageSpiEdit(): String {
        return "/manage/spi/manage-spi-edit"
    }

    // SPI追加画面
    @GetMapping("/manage/spi/add")
    fun getManageSpiAdd(): String {
        return "/manage/spi/manage-spi-add"
    }

    // CAB/GABメイン画面
    @GetMapping("/manage/cabgab")
    fun getManageCabgab(): String {
        return "/manage/cabgab/manage-cabgab-main"
    }

    // CAB/GAB変更画面
    @GetMapping("/manage/cabgab/edit")
    fun getManageCabgabEdit(): String {
        return "/manage/cabgab/manage-cabgab-edit"
    }

    // CAB/GAB追加画面
    @GetMapping("/manage/cabgab/add")
    fun getManageCabgabAdd(): String {
        return "/manage/cabgab/manage-cabgab-add"
    }

    // ログ管理画面
    @GetMapping("/manage/logs")
    fun getManageLogs(): String {
        return "/manage/manage-logs"
    }



    // 管理メイン画面
    @PostMapping("/manage")
    fun postManage(): String {
        return "/manage/manage-main"
    }

    // ユーザー管理画面
    @PostMapping("/manage/users")
    fun postManageUsers(): String {
        return "/manage/users/manage-users-main"
    }

    // ユーザー情報変更画面
    @PostMapping("/manage/users/edit")
    fun postManageUsersEdit(): String {
        return "/manage/users/manage-users-edit"
    }

    // ユーザー追加画面
    @PostMapping("/manage/users/add")
    fun postManageUsersAdd(): String {
        return "/manage/users/manage-users-add"
    }

    // SPIメイン画面
    @PostMapping("/manage/spi")
    fun postManageSpi(): String {
        return "/manage/spi/manage-spi-main"
    }

    // SPI変更画面
    @PostMapping("/manage/spi/edit")
    fun postManageSpiEdit(): String {
        return "/manage/spi/manage-spi-edit"
    }

    // SPI追加画面
    @PostMapping("/manage/spi/add")
    fun postManageSpiAdd(): String {
        return "/manage/spi/manage-spi-add"
    }

    // CAB/GABメイン画面
    @PostMapping("/manage/cabgab")
    fun postManageCabgab(): String {
        return "/manage/cabgab/manage-cabgab-main"
    }

    // CAB/GAB変更画面
    @PostMapping("/manage/cabgab/edit")
    fun postManageCabgabEdit(): String {
        return "/manage/cabgab/manage-cabgab-edit"
    }

    // CAB/GAB追加画面
    @PostMapping("/manage/cabgab/add")
    fun postManageCabgabAdd(): String {
        return "/manage/cabgab/manage-cabgab-add"
    }

    // ログ管理画面
    @PostMapping("/manage/logs")
    fun postManageLogs(): String {
        return "/manage/manage-logs"
    }
}