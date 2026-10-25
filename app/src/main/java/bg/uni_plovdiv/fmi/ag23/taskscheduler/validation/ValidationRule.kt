package bg.uni_plovdiv.fmi.ag23.taskscheduler.validation

data class ValidationRule(
    val regex: Regex,
    val errorMessage: String
) : Validatable {
    var currentInput: String = ""

    override fun validate(): Boolean {
        return regex.matches(currentInput)
    }

    fun validate(input: String): Boolean {
        currentInput = input
        return validate()
    }
}