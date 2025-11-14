import java.io.File
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class Record (
    val firstName: String,
    val lastName: String,
    val dob: String,
    val phone: String,
    val street: String,
    val city: String,
    val state: String,
    val zip: String
)

fun createTxtFile() {
    val directory = "project_files"
    val filePath = "$directory/example.txt"
    val dir = File(directory)
    if (!dir.exists()) {
        dir.mkdirs()
    }
    val file = File(filePath)

    val sourcePath = "/Users/danielabbott/Downloads/fake_data_records.txt"
    val sourceFile = File(sourcePath)
    if (!sourceFile.exists()) {
        println("Source file does not exist: $sourcePath")
        return
    }
    file.createNewFile()


    val content = sourceFile.readLines()
    if (content.size != 100) {
        throw IllegalStateException("Source file must contain exactly 100 records. Counting: ${content.size} records")
    }

    file.writeText(content.joinToString(separator = "\n"))
}

fun appendTextFile() {
    val filePath = "project_files/example.txt"
    val file = File(filePath)

    if (!file.exists()) {
        println("File does not exist. Run the create program first")
        return
    }

    val sourcePath = "/Users/danielabbott/Downloads/fake_data_records_50.txt"
    val sourceFile = File(sourcePath)
    if (!sourceFile.exists()) {
        println("Source file does not exist: $sourcePath")
        return
    }
    val content = sourceFile.readLines()
    if (content.size != 50){
        throw IllegalStateException("Source file must contain exactly 50 records. Counting: ${content.size} records")
    }
    file.appendText(sourceFile.readLines().joinToString(separator = "\n"))

}

fun readTextFile() {
    val filePath = "project_files/example.txt"
        val file = File(filePath)

    if (!file.exists()) {
        println("File does not exist. Run the create program first")
        return
    }

        val list = ArrayList<String>()
        val records = file.readLines()
        records.forEach {
            val trimmed = it.trim()
            list.add(trimmed)
            println(trimmed)
        }

}

fun createJsonFile() {
    val directory = "project_files"
    val filePath = "$directory/json-example.txt"
    val dir = File(directory)
    if (!dir.exists()) {
        dir.mkdirs()
    }
    val file = File(filePath)

    val sourcePath = "/Users/danielabbott/Downloads/fake_data_records.txt"
    val sourceFile = File(sourcePath)
    if (!sourceFile.exists()) {
        println("Source file does not exist: $sourcePath")
        return
    }
    file.createNewFile()

    val content = sourceFile.readLines()
    if (content.size != 50){
        println("File does not contain exactly 50 records.")
    }

    val records = mutableListOf<Record>()
    content.forEach { line ->
        val fields = line.trim().split(",")
        if (fields.size == 8){
            records.add(Record(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], fields[6], fields[7]))
        }
        else{
            println("Fields need to be 8 . Counting: ${fields.size} fields")
        }
    }
    val gson = Gson()
    file.bufferedWriter().use{ writer ->
        gson.toJson(records, writer)
    }

}

fun appendJsonFile() {
    val filePath = "project_files/json-example.txt"
    val file = File(filePath)

    if (!file.exists()) {
        println("File does not exist. Run the create program first")
        return
    }

    val sourcePath = "/Users/danielabbott/Downloads/fake_data_records_50.txt"
    val sourceFile = File(sourcePath)
    if (!sourceFile.exists()) {
        println("Source file does not exist: $sourcePath")
        return
    }
    val content = sourceFile.readLines()
    if (content.size != 50){
        println("Source file must contain exactly 50 records. Counting: ${content.size} records")
    }

    val gson = Gson()
    val listType = object : TypeToken<MutableList<Record>>() {}.type
    val existingRecords : MutableList<Record> = file.bufferedReader().use { reader ->
        gson.fromJson(reader, listType)
    }

    val newRecords = mutableListOf<Record>()
    content.forEach { line ->
        val fields = line.trim().split(",")
        if (fields.size == 8){
            newRecords.add(Record(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], fields[6], fields[7]))
        }
        else{
            println("Fields need to be 8 . Counting: ${fields.size} fields")
        }
    }

    existingRecords.addAll(newRecords)
    file.bufferedWriter().use { writer ->
        gson.toJson(existingRecords, writer)
    }
}

fun readJsonFile() {
    val filePath = "project_files/example.txt"
    val file = File(filePath)

    if (!file.exists()) {
        println("File does not exist. Run the create program first")
        return
    }

}

fun printTable(records: List<Record>) {
    println("| First Name | Last Name | DOB        | Phone           | Street Address     | City           | State | Zip     |")
    println("|------------|-----------|------------|-----------------|--------------------|----------------|-------|---------|")

    records.forEach { record ->
        println("| ${record.firstName.padEnd(10)} | ${record.lastName.padEnd(9)} | ${record.dob.padEnd(10)} | ${record.phone.padEnd(15)} | ${record.street.padEnd(22)} | ${record.city.padEnd(14)} | ${record.state.padEnd(5)} | ${record.zip.padEnd(7)} |")
    }
}

fun main() {
    val filePath = "project_files/example.txt"
    val file = File(filePath)
    createTxtFile()
    createJsonFile()
    appendTextFile()
    appendJsonFile()

    if (!file.exists()) {
        println("File does not exist. Run the create and append programs first")
        return
    }

    val records = ArrayList<Record>()
    file.bufferedReader().use { reader -> // bufferedreader to efficiently read the file . stores in reader
        reader.forEachLine { line -> // for each line in the reader
            val fields = line.trim().split(",") // split at the comma so we can get our fields
            if (fields.size == 8) {
                records.add(
                    Record(
                        fields[0], fields[1], fields[2], fields[3],
                        fields[4], fields[5], fields[6], fields[7]
                        )
                    )
                }
            }
        }

        if (records.size != 150) {
            println("Expected 150 records. Counting: ${records.size} records")

        }
    printTable(records)
    }
