import java.util.*
import kotlin.reflect.jvm.internal.impl.serialization.ProtoBuf

// Explore a simple class

println("UW Homework: Simple Kotlin")

// write a "whenFn" that takes an arg of type "Any" and returns a String
fun whenFn(x: Any): String{
    when(x) {
        "Hello" -> return "world"
        is String -> return "Say what?"
        0 -> return "zero"
        1 -> return "one"
        in 2..10 -> return "low number"
        is Int -> return "a number"
        else -> return "I don't understand"
    }
}
// write an "add" function that takes two Ints, returns an Int, and adds the values
fun add(number01: Int, number02: Int): Int{
    return number01 + number02
}
// write a "sub" function that takes two Ints, returns an Int, and subtracts the values
fun sub(number01: Int, number02: Int): Int {
    return number01 - number02
}
// write a "mathOp" function that takes two Ints and a function (that takes two Ints and returns an Int), returns an Int, and applies the passed-in-function to the arguments
fun mathOp(number01: Int, number02: Int, functionOperation: (a: Int, b: Int) -> Int): Int {
    return functionOperation(number01, number02)
}
// write a class "Person" with first name, last name and age
class Person(var firstName: String, var lastName: String, var age: Int) {
    fun equals(otherFirstName: String, otherLastName: String, otherAge: Int): Boolean? {
        if(otherFirstName == firstName && otherLastName == lastName && otherAge == age) {
            return true
        }
        return false
    }
    override fun hashCode() = Objects.hash(firstName + lastName + age)
    var debugString: String = ""
        get() = "[Person firstName:" + firstName + " lastName:" + lastName + " age:" + age + "]"
}
// write a class "Money"
class Money(var amount: Int, var currency: String) {
    fun convert(other: String): Money {
        if((currency == "USD" || currency == "GBP" || currency == "EUR" || currency == "CAN") && amount >= 0) {
            var currency01 = currency
            var amount01 = amount
            if(currency01 != "USD") {
                if(currency01 == "GBP") {
                    amount01 = (amount01 / 0.5).toInt()
                } else if(currency01 == "EUR"){
                    amount01 = (amount01 / 1.5).toInt()
                } else if(currency01 == "CAN") {
                    amount01 = (amount01 / 1.25).toInt()
                }
                currency01 = "USD"
            }
            if(currency01 != other) {
                if(other == "GBP") {
                    amount01 = (amount01 * 0.5).toInt()
                } else if(other == "EUR"){
                    amount01 = (amount01 * 1.5).toInt()

                } else if(other == "CAN") {
                    amount01 = (amount01 * 1.25).toInt()
                }
                currency01 = other
            }
            return Money(amount = amount01, currency = currency01)
        }else if(amount < 0){
            throw IllegalArgumentException("Invalid Amount")
        } else {
            throw IllegalArgumentException("Invalid Currency")
        }
    }

    operator fun plus(other:Money): Money {
        var newMoney: Money = other
        if (this.currency != other.currency) {
            newMoney = other.convert(this.currency)
        }
        return Money(this.amount + newMoney.amount, this.currency)
    }

}


// ============ DO NOT EDIT BELOW THIS LINE =============

print("When tests: ")
val when_tests = listOf(
        "Hello" to "world",
        "Howdy" to "Say what?",
        "Bonjour" to "Say what?",
        0 to "zero",
        1 to "one",
        5 to "low number",
        9 to "low number",
        17.0 to "I don't understand"
)
for ((k,v) in when_tests) {
    print(if (whenFn(k) == v) "." else "!")
}
println("")

print("Add tests: ")
val add_tests = listOf(
        Pair(0, 0) to 0,
        Pair(1, 2) to 3,
        Pair(-2, 2) to 0,
        Pair(123, 456) to 579
)
for ( (k,v) in add_tests) {
    print(if (add(k.first, k.second) == v) "." else "!")
}
println("")

print("Sub tests: ")
val sub_tests = listOf(
        Pair(0, 0) to 0,
        Pair(2, 1) to 1,
        Pair(-2, 2) to -4,
        Pair(456, 123) to 333
)
for ( (k,v) in sub_tests) {
    print(if (sub(k.first, k.second) == v) "." else "!")
}
println("")

print("Op tests: ")
print(if (mathOp(2, 2, { l,r -> l+r} ) == 4) "." else "!")
print(if (mathOp(2, 2, ::add ) == 4) "." else "!")
print(if (mathOp(2, 2, ::sub ) == 0) "." else "!")
print(if (mathOp(2, 2, { l,r -> l*r} ) == 4) "." else "!")
println("")


print("Person tests: ")
val p1 = Person("Ted", "Neward", 47)
print(if (p1.firstName == "Ted") "." else "!")
p1.age = 48
print(if (p1.debugString == "[Person firstName:Ted lastName:Neward age:48]") "." else "!")
println("")

print("Money tests: ")
val tenUSD = Money(10, "USD")
val twelveUSD = Money(12, "USD")
val fiveGBP = Money(5, "GBP")
val fifteenEUR = Money(15, "EUR")
val fifteenCAN = Money(15, "CAN")
val convert_tests = listOf(
        Pair(tenUSD, tenUSD),
        Pair(tenUSD, fiveGBP),
        Pair(tenUSD, fifteenEUR),
        Pair(twelveUSD, fifteenCAN),
        Pair(fiveGBP, tenUSD),
        Pair(fiveGBP, fifteenEUR)
)
for ( (from,to) in convert_tests) {
    print(if (from.convert(to.currency).amount == to.amount) "." else "!")
}
val moneyadd_tests = listOf(
        Pair(tenUSD, tenUSD) to Money(20, "USD"),
        Pair(tenUSD, fiveGBP) to Money(20, "USD"),
        Pair(fiveGBP, tenUSD) to Money(10, "GBP")
)
for ( (pair, result) in moneyadd_tests) {
    print(if ((pair.first + pair.second).amount == result.amount &&
            (pair.first + pair.second).currency == result.currency) "." else "!")
}
println("")
