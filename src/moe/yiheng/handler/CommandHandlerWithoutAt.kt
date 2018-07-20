package moe.yiheng.handler

import moe.yiheng.domain.Prpr
import moe.yiheng.service.ChatService
import moe.yiheng.service.ChatServiceImpl
import moe.yiheng.service.PrprService
import moe.yiheng.service.PrprServiceImpl
import moe.yiheng.utils.generateRandomEmoji
import moe.yiheng.utils.generateUserLink
import moe.yiheng.utils.prpr
import moe.yiheng.utils.prprstat
import org.telegram.telegrambots.api.objects.Message
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class CommandHandlerWithoutAt(private val msg: Message) : CommandHandler {

    private val service: PrprService = PrprServiceImpl()
    private val chatService: ChatService = ChatServiceImpl()

    override fun getCommand(): String {
        val regex = "/([a-zA-z0-9]+)"
        val matcher = Pattern.compile(regex).matcher(msg.text)

        if (matcher.find()) {
            return matcher.group(1)
        } else {
            throw IllegalArgumentException("not a command")
        }
    }

    override fun handle(): String? {
        if (getCommand() == "prpr" && msg.replyToMessage == null) { // prpr自己
            if (msg.from.userName == null) return "坏耶,您没有设置username"
            prpr(msg.from.userName.toLowerCase(), msg.chatId)
            return generateUserLink(msg) + " prpr了自己!"
        } else if (getCommand() == "prpr" && msg.replyToMessage != null) { // prpr 回复的人
            if (msg.from.userName == null) return "坏耶,对方没有设置username"
            prpr(msg.replyToMessage.from.userName.toLowerCase(), msg.chatId)
            if (chatService.findByChatId(msg.chatId)?.shouldReplyPrpr == 0) return null
            return "${generateUserLink(msg)} prpr了 ${generateUserLink(msg.replyToMessage)} ${ generateRandomEmoji() }"
        } else if (getCommand() == "prcount") {
            val parameters = getParameters()
            return prprstat(parameters,msg)
        }
        return null
    }



    override fun getParameters(): List<String> {
        val regex = "/[a-zA-z0-9_]+\\s(.+)"
        val matcher = Pattern.compile(regex).matcher(msg.text)
        if (matcher.find()) {
            val s = matcher.group(1)
            return if (!s.contains(" ")) {
                listOf(s)
            } else {
                val list = s.split(Regex(" +"))
                list
            }
        }
        return ArrayList()
    }

    override fun getParameter(): String {
        val regex = "/[a-zA-z0-9_]+\\s(.+)"
        val matcher = Pattern.compile(regex).matcher(msg.text)
        if (matcher.find()) {
            return matcher.group(1)
        }
        return ""
    }

}