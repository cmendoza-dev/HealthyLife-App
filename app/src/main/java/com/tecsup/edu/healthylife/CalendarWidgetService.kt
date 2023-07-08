package com.tecsup.edu.healthylife

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CalendarWidgetService(private val context: Context, intent: Intent) : RemoteViewsService.RemoteViewsFactory {

    private val calendar: Calendar = Calendar.getInstance()
    private val dates: ArrayList<Date> = ArrayList()

    override fun onCreate() {
        // Inicializar los datos del calendario
        val currentDate = calendar.time
        val monthStart = getMonthStartDate(currentDate)
        val monthEnd = getMonthEndDate(currentDate)

        calendar.time = monthStart
        while (!calendar.time.after(monthEnd)) {
            dates.add(calendar.time)
            calendar.add(Calendar.DATE, 1)
        }
    }

    override fun onDataSetChanged() {
        // No requerido en este ejemplo
    }

    override fun onDestroy() {
        // No requerido en este ejemplo
    }

    override fun getCount(): Int {
        return dates.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val date = dates[position]
        val dayOfMonth = SimpleDateFormat("d", Locale.getDefault()).format(date)

        val remoteViews = RemoteViews(context.packageName, R.layout.calendar_widget)
        remoteViews.setTextViewText(R.id.txtDate, dayOfMonth)

        // Personaliza la apariencia de la celda según tus necesidades
        // Por ejemplo, puedes resaltar las fechas seleccionadas o mostrar un indicador para días ocupados

        return remoteViews
    }

    override fun getLoadingView(): RemoteViews? {
        // No requerido en este ejemplo
        return null
    }

    override fun getViewTypeCount(): Int {
        // No requerido en este ejemplo
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        // No requerido en este ejemplo
        return false
    }

    private fun getMonthStartDate(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        resetTime(calendar)
        return calendar.time
    }

    private fun getMonthEndDate(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.MONTH, 1)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.add(Calendar.DATE, -1)
        resetTime(calendar)
        return calendar.time
    }

    private fun resetTime(calendar: Calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
    }
}
