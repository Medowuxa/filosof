import kotlin.random.Random

fun main() {
    // Количество философов
    println("Введите количество философов:")
    val n = readLine()?.toIntOrNull() ?: return

    // Инициализация вилок и статусов
    val forks = BooleanArray(n) { false } // false - вилка свободна
    val philosophers = MutableList(n) { "размышляет" }
    val forkChoices = MutableList(n) { "" } // Список, где будет храниться, с какой стороны философ взял вилку

    // Случайный выбор философов, создаем случайную очередь
    val order = (0 until n).shuffled()

    println("Случайная очередь философов: ${order.joinToString(", ")}")

    // 1. Поочередное определение, какой философ с какой стороны берет вилку
    println("\nПоочередное определение, какой философ с какой стороны берет вилку:")

    for (philosopher in order) {
        val leftFork = philosopher         // Левая вилка - индекс философа
        val rightFork = (philosopher - 1 + n) % n  // Правая вилка - индекс предыдущего философа

        // Сначала философ случайным образом выбирает, какую вилку попытаться взять
        val pickLeft = Random.nextBoolean()

        // Попытка взять вилку
        when {
            pickLeft && !forks[leftFork] -> { // Если философ выбрал левую вилку, и она свободна
                forks[leftFork] = true
                philosophers[philosopher] = "обедает"
                forkChoices[philosopher] = "слева"
                println("Философ $philosopher взял вилку слева.")
            }
            !pickLeft && !forks[rightFork] -> { // Если философ выбрал правую вилку, и она свободна
                forks[rightFork] = true
                philosophers[philosopher] = "обедает"
                forkChoices[philosopher] = "справа"
                println("Философ $philosopher взял вилку справа.")
            }
            pickLeft && forks[leftFork] && !forks[rightFork] -> { // Левая занята, но правая свободна
                forks[rightFork] = true
                philosophers[philosopher] = "обедает"
                forkChoices[philosopher] = "справа"
                println("Философ $philosopher не смог взять вилку слева, взял вилку справа.")
            }
            !pickLeft && forks[rightFork] && !forks[leftFork] -> { // Правая занята, но левая свободна
                forks[leftFork] = true
                philosophers[philosopher] = "обедает"
                forkChoices[philosopher] = "слева"
                println("Философ $philosopher не смог взять вилку справа, взял вилку слева.")
            }
            else -> {
                // Обе вилки заняты - философ продолжает размышлять
                philosophers[philosopher] = "размышляет"
                forkChoices[philosopher] = "не взял вилку"
                println("Философ $philosopher не смог взять вилку.")
            }
        }
    }

    // 2. Кто обедает, а кто размышляет
    println("\nРезультат:")
    philosophers.forEachIndexed { index, status ->
        if (status == "обедает") {
            println("Философ $index: $status, взял вилку ${forkChoices[index]}")
        } else {
            println("Философ $index: $status")
        }
    }
}
