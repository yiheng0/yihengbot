package moe.yiheng.handler

interface CommandHandler {
    fun handle(): String?
    fun getCommand(): String
    fun getParameters(): List<String>
    fun getParameter():String
}
