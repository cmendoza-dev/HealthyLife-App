package com.tecsup.edu.healthylife.view

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.tecsup.edu.healthylife.R
import com.tecsup.edu.healthylife.data.RegisterBody
import com.tecsup.edu.healthylife.data.ValidateEmailBody
import com.tecsup.edu.healthylife.databinding.ActivityRegisterBinding
import com.tecsup.edu.healthylife.repository.AuthRepository
import com.tecsup.edu.healthylife.utils.APIService
import com.tecsup.edu.healthylife.utils.VibrateView
import com.tecsup.edu.healthylife.view_model.RegisterActivityViewModel
import com.tecsup.edu.healthylife.view_model.RegisterActivityViewModelFactory


class RegisterActivity : AppCompatActivity(), View.OnClickListener, View.OnFocusChangeListener,
    View.OnKeyListener, TextWatcher {

    private lateinit var mBinding: ActivityRegisterBinding
    private lateinit var mViewModel: RegisterActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.apply {
            // Mostrar la flecha de retroceso
            setDisplayHomeAsUpEnabled(true)
            // Centrar el título
            setDisplayShowTitleEnabled(false)
            val customTitle = layoutInflater.inflate(R.layout.custom_actionbar_title, null)
            customTitle.findViewById<TextView>(R.id.titleTextView).text =
                getString(R.string.RegistroUsuario)
            // Establecer la vista personalizada como título centrado
            customTitle.layoutParams = ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER
            )
            setCustomView(customTitle)
            setDisplayShowCustomEnabled(true)
        }

        // Establecer el color de fondo de la ActionBar
        val drawable = ColorDrawable(ContextCompat.getColor(this, R.color.purple_700))
        supportActionBar?.setBackgroundDrawable(drawable)



        mBinding = ActivityRegisterBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        mBinding.NameEt.onFocusChangeListener = this
        mBinding.LastEt.onFocusChangeListener = this
        mBinding.EmailEt.onFocusChangeListener = this
        mBinding.DniEt.onFocusChangeListener = this
        mBinding.MobileEt.onFocusChangeListener = this
        mBinding.PasswordEt.onFocusChangeListener = this
        mBinding.ConfirmPasswordEt.onFocusChangeListener = this
        mBinding.ConfirmPasswordEt.setOnKeyListener(this)
        mBinding.ConfirmPasswordEt.addTextChangedListener(this)
        mBinding.registerBtn.setOnClickListener(this)
        mViewModel = ViewModelProvider(
            this, RegisterActivityViewModelFactory(
                AuthRepository(
                    APIService.getService()
                ), application
            )
        ).get(RegisterActivityViewModel::class.java)

        setupObservers()


        val linkTextView = findViewById<TextView>(R.id.linkTextView)
        linkTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private fun setupObservers() {
        mViewModel.getIsLoading().observe(this) {
            mBinding.progressBar.isVisible = it

        }

        mViewModel.getIsUniqueEmail().observe(this) {
            if (validateEmail(shouldUpdateView = false)) {
                if (it) {
                    mBinding.EmailTil.apply {
                        if (isErrorEnabled) isErrorEnabled = false
                        setStartIconDrawable(R.drawable.baseline_check_circle_24)
                        setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                    }
                } else {
                    mBinding.EmailTil.apply {
                        if (startIconDrawable != null) startIconDrawable = null
                        isErrorEnabled = true
                        error = "Este correo ya está siendo utilizado"
                    }
                }

            }
        }

        mViewModel.getErrorMessage().observe(this) {
            // fullName, email, password
            val formErrorKeys = arrayOf(
                "Nombres",
                "Apellidos",
                "Dni",
                "Celular",
                "Correo electrónico",
                "Contraseña"
            )
            val message = StringBuilder()
            it.map { entry ->
                if (formErrorKeys.contains(entry.key)) {
                    when (entry.key) {
                        "Nombres" -> {
                            mBinding.NameTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }

                        "Apellidos" -> {
                            mBinding.LastTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }

                        "Dni" -> {
                            mBinding.DniTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }

                        "Celular" -> {
                            mBinding.MobileTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }

                        "Correo electrónico" -> {
                            mBinding.EmailTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }

                        "Contraseña" -> {
                            mBinding.PasswordTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }

                    }

                } else {
                    message.append(entry.value).append("\n")
                }

                if (message.isNotEmpty()) {
                    AlertDialog.Builder(this)
                        .setIcon(R.drawable.info_24)
                        .setTitle("INFORMACIÓN")
                        .setMessage(message)
                        .setPositiveButton("OK") { dialog, _ -> dialog!!.dismiss() }
                        .show()
                }
            }

        }

        mViewModel.getUser().observe(this) {
            if (it != null) {
                startActivity(Intent(this, HomeActivity::class.java))
            }
        }

    }

    private fun validateName(shouldVibrateView: Boolean = true): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.NameEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Sus nombres son requeridos"
        }

        if (errorMessage != null) {
            mBinding.NameTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(this@RegisterActivity, this)
            }
        }

        return errorMessage == null
    }

    private fun validateLastName(shouldVibrateView: Boolean = true): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.LastEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Sus apellidos son requeridos"
        }

        if (errorMessage != null) {
            mBinding.LastTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(this@RegisterActivity, this)
            }
        }

        return errorMessage == null
    }

    private fun validateDni(shouldVibrateView: Boolean = true): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.DniEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Sus apellidos son requeridos"
        } else if (value.first() == '0') {
            errorMessage = "El número de DNI no puede comenzar con cero."
        }

        if (errorMessage != null) {
            mBinding.DniTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(this@RegisterActivity, this)
            }
        }

        return errorMessage == null
    }

    private fun validateMobile(shouldVibrateView: Boolean = true): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.MobileEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "El número de celular es requerido"
        } else if (!value.startsWith("9")) {
            errorMessage = "El número de celular debe comenzar con '9'."
        }

        if (errorMessage != null) {
            mBinding.MobileTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(this@RegisterActivity, this)
            }
        }

        return errorMessage == null
    }


    private fun validateEmail(
        shouldUpdateView: Boolean = true,
        shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.EmailEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "El correo es requerido"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            errorMessage = "El correo es no válido"
        }

        if (errorMessage != null && shouldUpdateView) {
            mBinding.EmailTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(this@RegisterActivity, this)
            }
        }

        return errorMessage == null
    }

    private fun validatePassword(
        shouldUpdateView: Boolean = true,
        shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.PasswordEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "La contraseña es requerida"
        } else if (value.length < 8) {
            errorMessage = "La contraseña debe ser mayor a 8 caracteres"
        }

        if (errorMessage != null && shouldUpdateView) {
            mBinding.PasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(this@RegisterActivity, this)
            }
        }

        return errorMessage == null
    }

    private fun validateConfirmPassword(
        shouldUpdateView: Boolean = true,
        shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.ConfirmPasswordEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Confirmar contraseña"
        } else if (value.length < 8) {
            errorMessage = "La confirmación de contraseña debe ser mayor a 8 caracteres"
        }

        if (errorMessage != null && shouldUpdateView) {
            mBinding.ConfirmPasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(this@RegisterActivity, this)
            }
        }

        return errorMessage == null
    }

    private fun validatePasswordAndConfirmPassword(
        shouldUpdateView: Boolean = true,
        shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val password: String = mBinding.PasswordEt.text.toString()
        val confirmpassword: String = mBinding.ConfirmPasswordEt.text.toString()
        if (password != confirmpassword) {
            errorMessage = "La confirmación de contraseña no coincide"
        }

        if (errorMessage != null && shouldUpdateView) {
            mBinding.ConfirmPasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(this@RegisterActivity, this)
            }
        }

        return errorMessage == null
    }


    override fun onClick(view: View?) {
        if (view != null && view.id == R.id.registerBtn)
            onSubmit()
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null) {
            when (view.id) {
                R.id.NameEt -> {
                    if (hasFocus) {
                        if (mBinding.NameTil.isErrorEnabled) {
                            mBinding.NameTil.isErrorEnabled = false
                        }
                    } else {
                        validateName()
                    }
                }

                R.id.LastEt -> {
                    if (hasFocus) {
                        if (mBinding.LastTil.isErrorEnabled) {
                            mBinding.LastTil.isErrorEnabled = false
                        }
                    } else {
                        validateLastName()
                    }
                }

                R.id.EmailEt -> {
                    if (hasFocus) {
                        if (mBinding.EmailTil.isErrorEnabled) {
                            mBinding.EmailTil.isErrorEnabled = false
                        }
                    } else {
                        if (validateEmail()) {
                            mViewModel.validateEmailAddress(ValidateEmailBody(mBinding.EmailEt.text!!.toString()))
                        }
                    }
                }

                R.id.DniEt -> {
                    if (hasFocus) {
                        if (mBinding.DniTil.isErrorEnabled) {
                            mBinding.DniTil.isErrorEnabled = false
                        }
                    } else {
                        validateDni()
                    }
                }

                R.id.MobileEt -> {
                    if (hasFocus) {
                        if (mBinding.MobileTil.isErrorEnabled) {
                            mBinding.MobileTil.isErrorEnabled = false
                        }
                    } else {
                        validateMobile()
                    }
                }

                R.id.PasswordEt -> {
                    if (hasFocus) {
                        if (mBinding.PasswordTil.isErrorEnabled) {
                            mBinding.PasswordTil.isErrorEnabled = false
                        }
                    } else {
                        if (validatePassword() && mBinding.ConfirmPasswordEt.text!!.isEmpty() && validateConfirmPassword()
                            && validatePasswordAndConfirmPassword()
                        ) {
                            if (mBinding.ConfirmPasswordTil.isErrorEnabled) {
                                mBinding.ConfirmPasswordTil.isErrorEnabled = false
                            }
                            mBinding.ConfirmPasswordTil.apply {
                                setStartIconDrawable(R.drawable.baseline_check_circle_24)
                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                            }
                        }
                    }
                }

                R.id.ConfirmPasswordEt -> {
                    if (hasFocus) {
                        if (mBinding.ConfirmPasswordTil.isErrorEnabled) {
                            mBinding.ConfirmPasswordTil.isErrorEnabled = false
                        }
                    } else {
                        if (validateConfirmPassword() && validatePassword() && validatePasswordAndConfirmPassword()) {
                            if (mBinding.PasswordTil.isErrorEnabled) {
                                mBinding.PasswordTil.isErrorEnabled = false
                            }
                            mBinding.ConfirmPasswordTil.apply {
                                setStartIconDrawable(R.drawable.baseline_check_circle_24)
                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                            }

                        }
                    }
                }
            }
        }

    }

    override fun onKey(view: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (KeyEvent.KEYCODE_ENTER == keyCode && event?.action == KeyEvent.ACTION_UP) {
            // realizar el registro
            onSubmit()

        }
        return false
    }


    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (validatePassword(shouldUpdateView = false) && validateConfirmPassword(shouldUpdateView = false) && validatePasswordAndConfirmPassword(
                shouldUpdateView = false
            )
        ) {
            mBinding.ConfirmPasswordTil.apply {
                if (isErrorEnabled) isErrorEnabled = false
                setStartIconDrawable(R.drawable.baseline_check_circle_24)
                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
            }
        } else {
            if (mBinding.ConfirmPasswordTil.startIconDrawable != null)
                mBinding.ConfirmPasswordTil.startIconDrawable = null
        }
    }

    override fun afterTextChanged(p0: Editable?) {}

    private fun onSubmit() {
        if (validate()) {
            //make api request
            mViewModel.registerUser(
                RegisterBody(
                    mBinding.NameEt.text!!.toString(),

                    mBinding.EmailEt.text!!.toString(),
                    mBinding.PasswordEt.text!!.toString()
                )
            )
        }
    }

    private fun validate(): Boolean {
        var isValid = true

        if (!validateName(shouldVibrateView = false)) isValid = false
        if (!validateLastName(shouldVibrateView = false)) isValid = false
        if (!validateDni(shouldVibrateView = false)) isValid = false
        if (!validateMobile(shouldVibrateView = false)) isValid = false
        if (!validateEmail(shouldVibrateView = false)) isValid = false
        if (!validatePassword(shouldVibrateView = false)) isValid = false
        if (!validateConfirmPassword(shouldVibrateView = false)) isValid = false
        if (isValid && !validatePasswordAndConfirmPassword(shouldVibrateView = false)) isValid =
            false

        if (!isValid) VibrateView.vibrate(this, mBinding.cardView)

        return isValid

    }

}