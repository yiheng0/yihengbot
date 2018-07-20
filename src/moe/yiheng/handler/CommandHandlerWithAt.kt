package moe.yiheng.handler

import moe.yiheng.domain.Chat
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

class CommandHandlerWithAt(private val msg: Message) : CommandHandler {

    private val prprService: PrprService = PrprServiceImpl()
    private val chatService: ChatService = ChatServiceImpl()

    override fun getCommand(): String {
        val matcher = Pattern.compile("/([a-zA-Z0-9]+)@[a-zA-Z0-9]+").matcher(msg.text)

        if (matcher.find()) {
            return matcher.group(1)
        } else {
            throw IllegalArgumentException("not a command")
        }
    }

    override fun handle(): String? {
        println(getCommand())
        if (getCommand() == "prpr") {
            prpr(getAtUser(), msg.chatId)
            if (chatService.findByChatId(msg.chatId)?.shouldReplyPrpr == 0) return null
            return """${generateUserLink(msg)} prpr了 @${getAtUser()} ${generateRandomEmoji()}"""
        }
        if (getAtUser() == "yihengbot") {
            val parameters = getParameters()
            if (getCommand() == "prcount") {
                return prprstat(parameters, msg)
            } else if (getCommand() == "setreply" ) {
                val flag: Int
                try {
                    if (getParameters().size!=1) throw IllegalArgumentException()
                    flag = parameters[0].toInt()
                    if (!(flag == 0 || flag == 1)) {
                        throw IllegalArgumentException()
                    }
                } catch (e: Exception) {
                    return "参数错误,只能为0(不回复)或1(回复)"
                }
                val chat: Chat? = chatService.findByChatId(msg.chatId)
                if (chat == null) {
                    chatService.add(Chat(msg.chatId, flag))
                } else if (chat.shouldReplyPrpr != flag) {
                    chatService.update(Chat(msg.chatId, flag))
                }
                return "已成功设置为${if (flag == 1) "" else "不"}回复prpr消息"
            }

        }

        return null
    }


    private fun getAtUser(): String {
        val regex = "/[a-zA-Z0-9]+@([a-zA-Z0-9_]+)"
        val matcher = Pattern.compile(regex).matcher(msg.text)

        if (matcher.find()) {
            return matcher.group(1).toLowerCase()
        } else {
            throw IllegalArgumentException("not a command")
        }
    }

    override fun getParameters(): List<String> {
        val regex = "/[a-zA-z0-9]+@[a-zA-Z0-9_]+\\s(.+)"
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
        val regex = "/[a-zA-z0-9]+@[a-zA-Z0-9_]+\\s(.+)"
        val matcher = Pattern.compile(regex).matcher(msg.text)
        if (matcher.find()) {
            return matcher.group(1)
        }
        return ""
    }
}