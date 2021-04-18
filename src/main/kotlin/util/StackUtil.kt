package util

import java.util.*

/**
 * Creates a stack from the given elements. Left is the bottom of the stack and right is the top
 *
 * @param elements The elements of the stack from bottom to top
 *
 * @return A stack of the given elements
 */
fun <T> stackOf(vararg elements: T): Stack<T> {
    val stack = Stack<T>()
    for (element: T in elements) {
        stack.push(element)
    }
    return stack
}

inline fun <reified E> Collection<E>.toStack(): Stack<E> = stackOf(*toTypedArray())