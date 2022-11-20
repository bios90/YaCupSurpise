package com.photogramma.logic.utils

import android.util.Patterns
import android.webkit.URLUtil
import androidx.appcompat.app.AppCompatActivity
import com.bios.yacupsurpise.R
import com.bios.yacupsurpise.base.getStringApp
import com.bios.yacupsurpise.base.safe
import com.bios.yacupsurpise.base.view_models.BaseEffectsData
import com.bios.yacupsurpise.utils.BuilderAlerter
import com.bios.yacupsurpise.utils.files.FileItem
import java.util.*
import kotlin.collections.ArrayList

fun validate(action: ValidationData.() -> Unit) = ValidationData().apply(action)

fun ValidationData.validateStr(str: String?, fieldName: String, minChars: Int?) =
    this.validateNotNullField(str, fieldName, minChars)

fun ValidationData.validateAny(str: Any?, fieldName: String) =
    this.validateNotNullField(str, fieldName, null)

fun ValidationData.validateCondition(
    condition: Boolean?,
    fieldName: String? = null,
    errorMessage: String? = null,
) =
    this.validateAnyBoolean(condition, fieldName, errorMessage)

fun ValidationData.validateCondition(
    condition: () -> Boolean?,
    fieldName: String? = null,
    errorMessage: String? = null,
) =
    this.validateAnyBoolean(condition.invoke(), fieldName, errorMessage)

fun ValidationData.validateEmail(email: String?) = this.validateEmailInner(email)

fun ValidationData.getErrorAlerterOrMake(
    onError: (BaseEffectsData.Alerter) -> Unit,
    onSuccess: () -> Unit,
) {
    if (this.isValid.not()) {
        val alerterBuilder = BuilderAlerter.getRedBuilder(this.getErrorMessage())
        onError.invoke(BaseEffectsData.Alerter(alerterBuilder))
    } else {
        onSuccess.invoke()
    }
}

fun ValidationData.getErrorMessageOrMake(
    onError: (String) -> Unit,
    onSuccess: () -> Unit,
) {
    if (this.isValid.not()) {
        onError.invoke(this.getErrorMessage())
    } else {
        onSuccess.invoke()
    }
}

fun ValidationData.runIfValid(action: () -> Unit) {
    if (this.isValid) {
        action.invoke()
    }
}

class ValidationData(var isValid: Boolean = true, var errors: ArrayList<String> = ArrayList()) {

    fun getErrorMessage(): String {
        return errors.joinToString("\n")
    }

    fun validateNotNullField(str: Any?, fieldName: String, minChars: Int? = null) {
        if (str == null || (str is String && str.isEmpty())) {
            isValid = false
            errors.add(getPleaseFillStr(fieldName))
        } else if (minChars != null && str is String) {
            if (str.length < minChars) {
                isValid = false
                errors.add("Поле \"$fieldName\" должно содержать минимум $minChars символов")
            }
        }
    }

    fun validateEmailInner(email: String?) {
        if (email.isNullOrEmpty()) {
            isValid = false
            errors.add(getPleaseFillStr("Email"))
        } else if (isEmail(email).not()) {
            errors.add(getStringApp(R.string.please_enter_email))
            isValid = false
        }
    }

    fun validateNickname(name: String?) {
        if (name.isNullOrEmpty()) {
            isValid = false
            errors.add(getPleaseFillStr("Имя"))
        } else {
            if (name.length < 3) {
                isValid = false
                errors.add("Поле \"Имя\" должно содержать минимум 3 слова")
            }
            if (name.trim().splitToSequence(' ').toList().size > 1) {
                isValid = false
                errors.add("Поле \"Имя\" должно быть одним словом")
            }
        }
    }

    fun validateAnyBoolean(condition: Boolean?, fieldName: String?, errorMessage: String?) {
        if (condition.safe().not()) {
            isValid = false
            when {
                fieldName.isNullOrEmpty().not() -> errors.add(getPleaseFillStr(fieldName!!))
                errorMessage.isNullOrEmpty().not() -> errors.add(errorMessage!!)
            }
        }
    }

    fun validatePasswords(password: String?) {
        if (password.isNullOrEmpty()) {
            isValid = false
            errors.add(getPleaseFillStr("Пароль"))
        } else if (password.length < 8) {
            isValid = false
            errors.add("Пароль должен содержать минимум 8 символов")
        }
    }

    fun validateFile(fileItem: FileItem?, fieldN: String) {
        val errorStr = "Добавьте файл '$fieldN'"
        if (fileItem == null) {
            isValid = false
            errors.add(errorStr)
        } else if (fileItem.getFile().length() == 0L) {
            isValid = false
            errors.add(errorStr)
        }
    }

    fun validateUrl(str: String?, fieldName: String) {
        if (str.isNullOrEmpty()) {
            isValid = false
            errors.add(getPleaseFillStr(fieldName))
        } else if (!URLUtil.isValidUrl(str)) {
            isValid = false
            errors.add("Поле '$fieldName' должно быть ссылкой")
        }
    }

    fun validateTime(time: Date?, fieldName: String?) {
        if (time == null) {
            isValid = false
            errors.add("Поле '$fieldName' должно быть заполнено")
        }
    }


    fun showErrors(act: AppCompatActivity) {
        BuilderAlerter.getRedBuilder(this.getErrorMessage())
            .show(act)
    }
}

private fun getPleaseFillStr(field: String): String {
    return getStringApp(R.string.please_fill, field)
}

private fun isEmail(email: String): Boolean {
    val matcher = Patterns.EMAIL_ADDRESS.matcher(email)
    return matcher.matches()
}