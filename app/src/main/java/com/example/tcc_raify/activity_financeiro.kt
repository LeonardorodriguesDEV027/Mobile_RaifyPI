package com.example.tcc_raify

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class activity_financeiro : AppCompatActivity() {

    private lateinit var graficoBarras: BarChart
    private lateinit var btn6Meses: TextView
    private lateinit var btn3Meses: TextView
    private lateinit var btn1Mes: TextView
    private lateinit var tabVisaoGeral: TextView
    private lateinit var tabTransacoes: TextView
    private lateinit var tabAnalises: TextView
    private lateinit var btnFiltroMes: TextView
    private lateinit var btnNovoRegistro: androidx.appcompat.widget.AppCompatButton
    private lateinit var btnMenu: ImageButton
    private lateinit var btnNotificacao: ImageButton
    private lateinit var btnPerfil: ImageButton
    private lateinit var logoHeader: android.widget.FrameLayout
    private lateinit var tvMediaReceitas: TextView
    private lateinit var tvMediaDespesas: TextView
    private lateinit var tvMediaLucros: TextView
    private lateinit var tvPeriodo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_financeiro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupChart()
        setupListeners()
        loadChartData(6) // Padrão: 6 meses
    }

    private fun initViewsGrafico() {
        graficoBarras = findViewById(R.id.graficoBarras)
        btn6Meses = findViewById(R.id.btn6Meses)
        btn3Meses = findViewById(R.id.btn3Meses)
        btn1Mes = findViewById(R.id.btn1Mes)
        tabVisaoGeral = findViewById(R.id.tabVisaoGeral)
        tabTransacoes = findViewById(R.id.tabTransacoes)
        tabAnalises = findViewById(R.id.tabAnalises)
        btnFiltroMes = findViewById(R.id.btnFiltroMes)
        btnNovoRegistro = findViewById(R.id.btnNovoRegistro)
        btnMenu = findViewById(R.id.btnMenu)
        btnNotificacao = findViewById(R.id.btnnotificacao)
        btnPerfil = findViewById(R.id.btnPerfil)
        logoHeader = findViewById(R.id.logoHeader)
        tvMediaReceitas = findViewById(R.id.tvMediaReceitas)
        tvMediaDespesas = findViewById(R.id.tvMediaDespesas)
        tvMediaLucros = findViewById(R.id.tvMediaLucros)
        tvPeriodo = findViewById(R.id.tvPeriodo)
    }

    private fun setupChartGrafico() {
        graficoBarras.description.isEnabled = false
        graficoBarras.legend.isEnabled = false
        graficoBarras.setDrawValueAboveBar(true)
        graficoBarras.setPinchZoom(false)
        graficoBarras.setScaleEnabled(false)
        graficoBarras.isDoubleTapToZoomEnabled = false

        val xAxis = graficoBarras.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true
        xAxis.setCenterAxisLabels(true)
        xAxis.position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM

        val yAxisLeft = graficoBarras.axisLeft
        yAxisLeft.setDrawGridLines(true)
        yAxisLeft.axisMinimum = 0f

        val yAxisRight = graficoBarras.axisRight
        yAxisRight.isEnabled = false
    }

    private fun setupListeners() {
        btn6Meses.setOnClickListener {
            updatePeriodSelection(6)
            loadChartData(6)
        }
        btn3Meses.setOnClickListener {
            updatePeriodSelection(3)
            loadChartData(3)
        }
        btn1Mes.setOnClickListener {
            updatePeriodSelection(1)
            loadChartData(1)
        }

        tabVisaoGeral.setOnClickListener { selectTab(0) }
        tabTransacoes.setOnClickListener {
            selectTab(1)
            val intent = Intent(this, Tela_Historico::class.java)
            startActivity(intent)
        }
        tabAnalises.setOnClickListener { selectTab(2) }

        btnFiltroMes.setOnClickListener {
            Toast.makeText(this, "Selecionar Mês/Filtro", Toast.LENGTH_SHORT).show()
        }

        btnNovoRegistro.setOnClickListener {
            Toast.makeText(this, "Novo Registro", Toast.LENGTH_SHORT).show()
        }

        btnMenu.setOnClickListener {
            Toast.makeText(this, "Menu aberto", Toast.LENGTH_SHORT).show()
        }
        btnNotificacao.setOnClickListener {
            Toast.makeText(this, "Notificações", Toast.LENGTH_SHORT).show()
        }
        btnPerfil.setOnClickListener {
            Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show()
        }
        logoHeader.setOnClickListener {
            Toast.makeText(this, "Raify - Início", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updatePeriodSelection(months: Int) {
        val activeColor = Color.parseColor("#7C4DFF")
        val inactiveColor = Color.parseColor("#757575")

        btn6Meses.setTextColor(if (months == 6) activeColor else inactiveColor)
        btn3Meses.setTextColor(if (months == 3) activeColor else inactiveColor)
        btn1Mes.setTextColor(if (months == 1) activeColor else inactiveColor)

        tvPeriodo.text = when (months) {
            6 -> "Últimos 6 meses"
            3 -> "Últimos 3 meses"
            else -> "Último mês"
        }
    }

    private fun selectTabGrafico(index: Int) {
        val normalColor = Color.parseColor("#000000")
        val grayColor = Color.parseColor("#757575")

        tabVisaoGeral.setTextColor(if (index == 0) normalColor else grayColor)
        tabTransacoes.setTextColor(if (index == 1) normalColor else grayColor)
        tabAnalises.setTextColor(if (index == 2) normalColor else grayColor)
    }

    private fun loadChartData(months: Int) {
        val incomeEntries = ArrayList<BarEntry>()
        val expenseEntries = ArrayList<BarEntry>()

        val allMonths = listOf("Dez", "Jan", "Fev", "Mar", "Abr", "Mai")
        val startIndex = (allMonths.size - months).coerceAtLeast(0)
        val selectedMonths = allMonths.subList(startIndex, allMonths.size)

        var totalIncome = 0f
        var totalExpense = 0f

        for (i in selectedMonths.indices) {
            val income = 12000f + (i * 800f)
            val expense = 7000f + (i * 600f)

            incomeEntries.add(BarEntry(i.toFloat(), income))
            expenseEntries.add(BarEntry(i.toFloat(), expense))

            totalIncome += income
            totalExpense += expense
        }

        val incomeDataSet = BarDataSet(incomeEntries, "Receitas").apply {
            color = Color.parseColor("#4CC264")
            setDrawValues(false)
        }

        val expenseDataSet = BarDataSet(expenseEntries, "Despesas").apply {
            color = Color.parseColor("#EF4B4B")
            setDrawValues(false)
        }

        val data = BarData(incomeDataSet, expenseDataSet)
        
        // Configuração de barras agrupadas (Grouped Bar Chart)
        val groupSpace = 0.2f
        val barSpace = 0.05f
        val barWidth = 0.35f
        data.barWidth = barWidth

        graficoBarras.data = data
        data.groupBars(0f, groupSpace, barSpace)

        val xAxis = graficoBarras.xAxis
        xAxis.axisMinimum = 0f
        xAxis.axisMaximum = 0f + data.getGroupWidth(groupSpace, barSpace) * selectedMonths.size
        xAxis.valueFormatter = IndexAxisValueFormatter(selectedMonths)
        xAxis.labelCount = selectedMonths.size

        graficoBarras.axisLeft.axisMaximum = 22000f
        graficoBarras.invalidate()

        // Cálculo das Médias
        val avgIncome = totalIncome / months
        val avgExpense = totalExpense / months
        val avgProfit = avgIncome - avgExpense

        tvMediaReceitas.text = "R$ %.1f".format(avgIncome)
        tvMediaDespesas.text = "R$ %.1f".format(avgExpense)
        tvMediaLucros.text = "R$ %.1f".format(avgProfit)
    }
}
