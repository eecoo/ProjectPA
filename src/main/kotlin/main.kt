import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties

fun main() {

    var test = JObject("test", parent = null)
    var testtest = JObject("testtest", parent = test)
    var testtesttest = JObject("testtesttest", parent = testtest)

    testtesttest.children = mutableListOf(JString("nomestr1", "str1", testtesttest))

    testtest.children = mutableListOf(JString("nomestr2", "str2", testtest), testtesttest)

    var objInArray = JObject("", parent = null)

    objInArray.children = mutableListOf(JString("chave3", "valor3", objInArray))

    test.children = mutableListOf(
        JString("name", "Nome", test),
        JNumber("age", 19, test),
        JArray("arrayTest", arrayOf(
            JString("", "test1", null),
            objInArray,
            JNumber("", 1, null),
            JArray("array2", arrayOf(
                JString("", "test3", null),
                JNumber("", 2, null)), null),
            JArray("t", arrayOf(
                JString("", "test4", null),
                JArray("", arrayOf(
                    JString("", "test5", null)
                ), null)), null)), test),
        testtest)

    println(toJsonString(testtesttest))
    var numero = countObj(testtesttest)
    println(numero)
}

/**
 * retorna a string em formato json
 */
fun toJsonString(obj: JObject): String {
    var v = JsonVisitor()
    obj.accept(v)
    // drop last ,
    return "${v.json.dropLast(1)}\n}"
}

/*
pega no objeto json e visita todos os childs
se o child for um leaf, da append a string para json string
se nao visita outros childs
 */


fun countObj(obj: JObject): Int{
    var count = 0
    obj.children.forEach{
        when(obj){
            is JObject -> count++
        }
    }

    return count
}

/*fun countObj(obj: JObject): Int{
    var count = 0
    obj.children.forEach{
        if(it is JObject) {
            count++
        }
    }

    return count
}*/