package semicolon.dev.yourpersona.util.firebase

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.google.firebase.inappmessaging.FirebaseInAppMessaging
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDisplay
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDisplayCallbacks
import com.google.firebase.inappmessaging.model.CardMessage
import com.google.firebase.inappmessaging.model.InAppMessage
import com.google.firebase.inappmessaging.model.MessageType
import semicolon.dev.yourpersona.databinding.FiamCardBinding

class FiamBuilder(private val context: Context) : FirebaseInAppMessagingDisplay {

    override fun displayMessage(
        inAppMessage: InAppMessage,
        callbacks: FirebaseInAppMessagingDisplayCallbacks
    ) {
        when (inAppMessage.messageType) {
            MessageType.CARD -> showCustomCard(inAppMessage as? CardMessage, callbacks)
            else -> callbacks.messageDismissed(FirebaseInAppMessagingDisplayCallbacks.InAppMessagingDismissType.UNKNOWN_DISMISS_TYPE)
        }
    }

    private fun showCustomCard(
        inAppMessage: CardMessage?,
        callbacks: FirebaseInAppMessagingDisplayCallbacks
    ) {
        val binding by lazy { FiamCardBinding.inflate(LayoutInflater.from(context)) }

        binding.apply {
            inAppMessage?.let {
                tvTitle.text = it.title.text
                tvBody.text = it.body?.text

                Glide.with(context).load(it.portraitImageData?.imageUrl).into(ivBanner)

                btnCta.apply {
                    text = it.primaryAction.button?.text?.text
                    setOnClickListener {
                        FirebaseInAppMessaging.getInstance()
                            .triggerEvent(inAppMessage.primaryAction.actionUrl.toString())
                    }
                }
            }
        }

        // Tampilkan dialog dengan tampilan kustom
        val dialog = AlertDialog.Builder(context)
            .setView(binding.root)
            .setOnDismissListener {
                FirebaseInAppMessaging.getInstance().triggerEvent("dialog_dismissed")
            }
            .create()

        dialog.show()
    }
}