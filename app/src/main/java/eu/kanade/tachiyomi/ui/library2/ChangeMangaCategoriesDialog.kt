package eu.kanade.tachiyomi.ui.library2

import android.app.Dialog
import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.bluelinelabs.conductor.Controller
import eu.kanade.tachiyomi.R
import eu.kanade.tachiyomi.data.database.models.Category
import eu.kanade.tachiyomi.data.database.models.Manga
import eu.kanade.tachiyomi.ui.base.controller.DialogController

class ChangeMangaCategoriesDialog<T>(bundle: Bundle? = null) :
        DialogController(bundle) where T : Controller, T : ChangeMangaCategoriesDialog.Listener {

    private var mangas = emptyList<Manga>()

    private var categories = emptyList<Category>()

    private var preselected = emptyArray<Int>()

    constructor(target: T, mangas: List<Manga>, categories: List<Category>,
                preselected: Array<Int>) : this() {

        this.mangas = mangas
        this.categories = categories
        this.preselected = preselected
        targetController = target
    }

    override fun onCreateDialog(savedViewState: Bundle?): Dialog {
        return MaterialDialog.Builder(activity!!)
                .title(R.string.action_move_category)
                .items(categories.map { it.name })
                .itemsCallbackMultiChoice(preselected) { dialog, _, _ ->
                    val newCategories = dialog.selectedIndices?.map { categories[it] }.orEmpty()
                    (targetController as? Listener)?.updateCategoriesForMangas(mangas, newCategories)
                    true
                }
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .build()
    }

    interface Listener {
        fun updateCategoriesForMangas(mangas: List<Manga>, categories: List<Category>)
    }

}