package moe.yiheng.utils

import moe.yiheng.domain.Prpr
import moe.yiheng.service.ChatService
import moe.yiheng.service.ChatServiceImpl
import moe.yiheng.service.PrprService
import moe.yiheng.service.PrprServiceImpl
import org.telegram.telegrambots.api.objects.Message
import java.util.*

private val prprService: PrprService = PrprServiceImpl()

fun generateRandomEmoji(): String {
    val list = listOf("\uD83C\uDF1D", "\uD83C\uDF1A", "\uD83D\uDE00", "\uD83D\uDE03", "\uD83D\uDE04", "\uD83D\uDE01", "\uD83D\uDE05"
            , "\uD83D\uDE43", "\uD83D\uDE09", "\uD83D\uDE0D", "\uD83D\uDE17", "\uD83D\uDE0E", "\uD83D\uDE15", "\uD83D\uDE0F", "\uD83D\uDE27")
    return list[Random().nextInt(list.size)]
}

fun prpr(username: String, chatId: Long) {
    var prpr = prprService.findByUsernameAndChatId(username, chatId)
    if (prpr == null) {
        prpr = Prpr(username, chatId, 1)
        prprService.add(prpr)
    } else {
        prpr.prprstat += 1
        prprService.update(prpr)
    }
}

fun prprstat(parameters: List<String>, msg: Message): String? {
    return if (parameters.isEmpty()) {
        val prpr = prprService.findByUsernameAndChatId(msg.from.userName.toLowerCase(), msg.chatId)
        if (prpr == null) {
            generateUserLink(msg) + " 还没有被prpr过呢,立即 /prpr@" + msg.from.userName
        } else {
            "${generateUserLink(msg)} 在这个群组中被prpr了 ${prpr.prprstat} 次 立即 /prpr@${msg.from.userName}"
        }
    } else {
        val prpr = prprService.findByUsernameAndChatId(parameters[0], msg.chatId)
        if (prpr == null) {
            """@${parameters[0]} 还没有被prpr过呢,立即 /prpr@${parameters[0]}"""
        } else {
            """@${parameters[0]} 在这个群组中被prpr了${prpr.prprstat}次 立即 /prpr@${parameters[0]}"""
        }
    }
}

fun generateUserLink(username: String, firstName: String, lastName: String): String {
    return "<a href=\"https://t.me/$username\">$firstName $lastName</a>"
}

fun generateUserLink(msg: Message): String {
    return generateUserLink(msg.from.userName ?: "", msg.from.firstName ?: "", msg.from.lastName ?: "")
}