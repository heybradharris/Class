import android.graphics.Color
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.BackgroundColorSpan
import com.example.aclass.diff.DiffParser.Companion.parseDiff
                val diffItems = withContext(defaultDispatcher) { parseDiff(diff) }
                _viewState.postValue(ViewState.Diff(diffItems))
            val diffItems: List<DiffRecyclerItem>