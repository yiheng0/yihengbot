package moe.yiheng.bot

import moe.yiheng.handler.CommandHandler
import moe.yiheng.handler.CommandHandlerFactory
import moe.yiheng.utils.generateUserLink
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.api.objects.Message
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import java.util.*

class MyBot : TelegramLongPollingBot() {


    private var canSay = HashMap<Long, Boolean>()

    private val hasPermission = listOf("yiheng233")

    inner class SleepThread(private val chatId: Long) : Runnable {
        override fun run() {
            Thread.sleep(5000 * 60)
            canSay[chatId] = true
        }
    }


    override fun onUpdateReceived(update: Update?) {
        if (update == null) return
        var handler: CommandHandler? = null
        val message: Message = update.message ?: return
        if (!message.hasText()) return
        println("@${message.from.userName}:${message.text}")
        if (message.text.contains("delete") && message.replyToMessage.from.userName == me.userName && message.from.userName in hasPermission) {
            execute(DeleteMessage(message.chatId, message.replyToMessage.messageId))
        }

        listenKeyWords(message)

        try {
            handler = CommandHandlerFactory.createCommandHandle(message)
        } catch (e: Exception) {
            println("not a command")
        }
        if (handler == null) return
        val reply = handler.handle()
        if (reply != null) {
            execute(SendMessage()
                    .setChatId(message.chatId)
                    .setText(reply)
                    .setReplyToMessageId(message.messageId)
                    .setParseMode("html")
                    .disableWebPagePreview()
            )
        }


        if (handler.getCommand() == "say") {
            val msg = if (handler.getParameter().trim().isEmpty()) "似乎没有说话" else handler.getParameter()
            execute(SendMessage()
                    .setChatId(message.chatId)
                    .setText("${generateUserLink(message)}: $msg")
                    .setParseMode("html")
                    .disableWebPagePreview()
            )
        } else if (handler.getCommand() == "choose") {
            val args = handler.getParameters()
            val msg = if (args.size == 1 && args[0].trim().isEmpty())
                "格式错误,/choose 后添加你想要随机选择的内容"
            else
                "随机选择的结果是: _${args[Random().nextInt(args.size)]}_"
            execute(SendMessage()
                    .setChatId(message.chatId)
                    .setText(msg)
                    .setParseMode("markdown")
                    .disableWebPagePreview()
            )
        }

    }

    private fun listenKeyWords(message: Message) {
        if (message.from.userName in hasPermission) return
        if (!canSay.contains(message.chatId)) {
            // 没有存在
            canSay[message.chatId] = true
        }

        if (canSay[message.chatId] == true) {

            when {
                message.text.toUpperCase().contains("RBQ") -> {
                    execute(SendMessage()
                            .setChatId(message.chatId)
                            .setText("${generateUserLink(message)}是本裙御用rbq呢")
                            .setReplyToMessageId(message.messageId)
                            .disableWebPagePreview()
                            .setParseMode("html")
                    )
                    canSay[message.chatId] = false
                    Thread(SleepThread(message.chatId)).start()
                }
                message.text.toUpperCase().contains("绒布球") -> {
                    execute(SendMessage()
                            .setChatId(message.chatId)
                            .setText("${generateUserLink(message)}绒布球!")
                            .setReplyToMessageId(message.messageId)
                            .disableWebPagePreview()
                            .setParseMode("html")
                    )
                    canSay[message.chatId] = false
                    Thread(SleepThread(message.chatId)).start()
                }
                message.text.contains("女装") -> {
                    execute(SendMessage(message.chatId, "让别人女装的同时,${generateUserLink(message)}也要女装哦")
                            .setReplyToMessageId(message.messageId)
                            .setParseMode("html")
                            .disableWebPagePreview()
                    )
                    canSay[message.chatId] = false
                    Thread(SleepThread(message.chatId)).start()
                }
                message.text.toUpperCase().contains("小裙子") -> {
                    execute(SendMessage()
                            .setChatId(message.chatId)
                            .setText("${generateUserLink(message)}要穿小裙子呢")
                            .setReplyToMessageId(message.messageId)
                            .disableWebPagePreview()
                            .setParseMode("html")
                    )
                    canSay[message.chatId] = false
                    Thread(SleepThread(message.chatId)).start()
                }
            }

        }
    }

    override fun getBotUsername(): String = "yihengbot"

    override fun getBotToken(): String = "*************************************"
}