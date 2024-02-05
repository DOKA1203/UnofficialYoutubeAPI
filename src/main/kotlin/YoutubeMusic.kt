package kr.doka.lab

class YoutubeMusic(var title: String, val id: String, var length: String, var author: String) {
    val url = "https://www.youtube.com/watch?v=${id}"
    val thumbnail = "https://i.ytimg.com/vi/${id}/hqdefault.jpg"

    override fun toString(): String {
        return "Title: ${title}, ID: ${id}, Length: ${length}, Author: ${author}"
    }
}