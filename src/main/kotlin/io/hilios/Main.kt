import io.hilios.calculator.CalculatorError.Companion.show
import io.hilios.calculator.Expr
import io.hilios.calculator.Expr.Companion.show
import io.hilios.PostfixCalculator
import io.hilios.data.Left
import io.hilios.data.Stack
import io.hilios.data.map
import io.hilios.data.mkString
import kotlin.system.exitProcess

/**
 * Evaluate the input and execute the side effects.
 */
tailrec fun runLoop(state: Stack<Expr>) {
    print("> ")
    val input = readLine()
        ?.split("""\s+""".toRegex())
        ?.filterNot { s -> s.isEmpty() }
        ?: exitProcess(1)
    
    val (stack, result) = PostfixCalculator.parse(*input.toTypedArray()).get(state)
    
    if (result is Left) println("error: ${result.value.show()}")
    println("stack: ${stack.map { expr -> expr.show() }.mkString(" ")}")
    
    runLoop(stack)
}

fun main(args: Array<String>) {
    println("""
     _     __   _    __   _    _     __ ________  ___  
    | |_/ / /\ | |  / /` | | || |   / /\ | |/ / \| |_) 
    |_| \/_/--\|_|__\_\_,\_\_/|_|__/_/--\|_|\_\_/|_| \
    
    A command line RPN calculator.
    
    Operations: +, -, *, /, sqrt, clean, undo
    Exit: Ctrl + C

    """.trimIndent())

    runLoop(Stack.Empty)
    exitProcess(0)
}
