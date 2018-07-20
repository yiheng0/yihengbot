package moe.yiheng.handler

import org.telegram.telegrambots.api.objects.Message
import java.util.regex.Pattern

class CommandHandlerFactory {
    companion object {
        fun createCommandHandle(msg:Message): CommandHandler {
            if (contains(msg.text,"/[a-zA-Z0-9]+@[a-zA-Z0-9_]+")) {
                return CommandHandlerWithAt(msg)
            } else if (contains(msg.text,"/[a-zA-Z0-9_]+")){
                return CommandHandlerWithoutAt(msg)
            }
            throw IllegalArgumentException("not a command")
        }

        /**
         * 判断s中是否包含regex
         */
        private fun contains(s:String,regex:String): Boolean {
            val pattern = Pattern.compile(regex)
            val matcher = pattern.matcher(s)
            return matcher.find()
        }
    }
}