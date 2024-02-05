package kr.doka.lab

import com.jayway.jsonpath.JsonPath
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

const val youtubeEndpoint = "https://www.youtube.com"
class UYApi {
    fun search(keyword: String): List<YoutubeMusic> {
        val userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36"
        val url = "${youtubeEndpoint}/results?search_query=${keyword}"
        val nvDocument: Connection.Response = Jsoup.connect(url).userAgent(userAgent)
            .method(Connection.Method.GET)
            .execute()
        val doc: Document = nvDocument.parse()

        val datas: Elements = doc.select("script")
        val musics: MutableList<YoutubeMusic> = mutableListOf()
        for (data in datas) {
            for (node in data.dataNodes()) {
                if (node.wholeData.contains("var ytInitialData = ")) {
                    var nodeData = node.wholeData
                    nodeData = nodeData.replace("var ytInitialData = ", "")
                    nodeData = nodeData.replace(nodeData.substring(nodeData.length - 1), "")
                    val titles: List<String> = JsonPath.read<List<String>?>(nodeData,
                        "$.contents.twoColumnSearchResultsRenderer.primaryContents.sectionListRenderer.contents[0].itemSectionRenderer.contents[*].videoRenderer.title.runs[0].text"
                    ).slice(0..4)
                    val ids: List<String> = JsonPath.read<List<String>?>(nodeData,
                        "$.contents.twoColumnSearchResultsRenderer.primaryContents.sectionListRenderer.contents[0].itemSectionRenderer.contents[*].videoRenderer.videoId"
                    ).slice(0..4)
                    val lengths: List<String> = JsonPath.read<List<String>?>(nodeData,
                        "$.contents.twoColumnSearchResultsRenderer.primaryContents.sectionListRenderer.contents[0].itemSectionRenderer.contents[*].videoRenderer.lengthText.simpleText"
                    ).slice(0..4)
                    val authors: List<String> = JsonPath.read<List<String>?>(nodeData,
                        "$.contents.twoColumnSearchResultsRenderer.primaryContents.sectionListRenderer.contents[0].itemSectionRenderer.contents[*].videoRenderer.longBylineText.runs[0].text"
                    ).slice(0..4)
                    for (i in 0..4) {
                        musics.add(YoutubeMusic(titles[i], ids[i], lengths[i], authors[i]))
                    }
                }
            }
        }
        return musics
    }
    fun fromURL(url: String): YoutubeMusic {
        return this.search(url.split("&")[0])[0]
    }
}