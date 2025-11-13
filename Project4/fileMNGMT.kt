import java.io.File

fun createTxtFile() {
    val records100 = File("/Users/danielabbott/Downloads/fake_data_records.txt")
    val file = File("example.txt")
    file.createNewFile()


    val content = records100.readLines()
    if (content.size != 100) {
        throw IllegalStateException("Source file must contain exactly 100 records.")
    }

    file.writeText(content.joinToString(separator = "\n"))
}

fun appendTextFile() {
        val records50 = File("/Users/danielabbott/Downloads/fake_data_records_50.txt")
        val file = File("example.txt")
        file.appendText(records50.readLines().joinToString(separator = "\n"))

}

fun readTextFile() {
        val file = File("example.txt")
        val list = ArrayList<String>()
        val records = file.readLines()
        records.forEach {
            val trimmed = it.trim()
            list.add(trimmed)
            println(trimmed)
        }

}

fun createJsonFile() {


}

fun appendJsonFile() {


}

fun readJsonFile() {


}

fun main() {
        createTxtFile()
        appendTextFile()
        readTextFile()

}
