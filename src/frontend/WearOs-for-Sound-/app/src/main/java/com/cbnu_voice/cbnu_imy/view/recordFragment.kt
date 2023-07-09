package com.cbnu_voice.cbnu_imy.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.cbnu_voice.cbnu_imy.CustomBarChartRender
import com.cbnu_voice.cbnu_imy.R
import com.cbnu_voice.cbnu_imy.databinding.FragmentRecordBinding
import com.cbnu_voice.cbnu_imy.viewmodel.MainViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class recordFragment : Fragment() {

    private var binding: FragmentRecordBinding? = null
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentRecordBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.fetchPulseAvg()
            mainViewModel.fetchPulseAbove()
        }

        lifecycleScope.launch {
            mainViewModel.getPulseAvg.collect { pulseAvg ->
                binding?.recordAvgBpmMonth?.text = pulseAvg.toString().plus("BPM")
            }
        }

        lifecycleScope.launch {
            mainViewModel.getPulseAbove.collect { pulseAbove ->
                binding?.recordAboveBpmMonth?.text = pulseAbove.toString().plus("회")
            }
        }

        var barChart: BarChart = binding?.bpmBarChart!! // barChart 생성

        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(1f,80.0f))
        entries.add(BarEntry(2f,60.0f))
        entries.add(BarEntry(3f,70.0f))
        entries.add(BarEntry(4f,75.0f))
        entries.add(BarEntry(5f,80.0f))
        entries.add(BarEntry(6f,95.0f))
        entries.add(BarEntry(7f,90.0f))
        entries.add(BarEntry(8f,100.0f))
        entries.add(BarEntry(9f,70.0f))
        entries.add(BarEntry(10f,110.0f))
        entries.add(BarEntry(11f,70.0f))
        entries.add(BarEntry(12f,120.0f))
        entries.add(BarEntry(13f,90.0f))
        entries.add(BarEntry(14f,140.0f))
        entries.add(BarEntry(15f,120.0f))
        entries.add(BarEntry(16f,99.0f))
        entries.add(BarEntry(17f,78.0f))
        entries.add(BarEntry(18f,80.0f))
        entries.add(BarEntry(19f,101.0f))
        entries.add(BarEntry(20f,99.0f))
        entries.add(BarEntry(21f,80.0f))
        entries.add(BarEntry(22f,120.0f))
        entries.add(BarEntry(23f,70.0f))
        entries.add(BarEntry(24f,73.0f))
        entries.add(BarEntry(25f,76.0f))
        entries.add(BarEntry(26f,69.0f))
        entries.add(BarEntry(27f,81.0f))
        entries.add(BarEntry(28f,85.0f))
        entries.add(BarEntry(29f,102.0f))
        entries.add(BarEntry(30f,90.0f))
        entries.add(BarEntry(31f,80.0f))


        val barChartRender =
            CustomBarChartRender(barChart, barChart.animator, barChart.viewPortHandler)
        barChartRender.setRadius(20)
        barChart.renderer = barChartRender

        barChart.run {
            description.isEnabled = false // 차트 옆에 별도로 표기되는 description을 안보이게 설정 (false)
            setMaxVisibleValueCount(31) // 최대 보이는 그래프 개수를 31개로 지정
            setPinchZoom(false) // 핀치줌(두손가락으로 줌인 줌 아웃하는것) 설정
            setDrawBarShadow(false) //그래프의 그림자
            setDrawGridBackground(false)//격자구조 넣을건지
            axisLeft.run { //왼쪽 축. 즉 Y방향 축을 뜻한다.
                axisMaximum = 140f //100 위치에 선을 그리기 위해 101f로 맥시멈값 설정
                axisMinimum = 0f // 최소값 0
                granularity = 50f // 50 단위마다 선을 그리려고 설정.
                setDrawLabels(false) // 값 적는거 허용 (0, 50, 100)
                setDrawGridLines(false) //격자 라인 활용
                setDrawAxisLine(false) // 축 그리기 설정
                axisLineColor = ContextCompat.getColor(context,R.color.imy_text_gray) // 축 색깔 설정
                gridColor = ContextCompat.getColor(context,R.color.imy_text_gray) // 축 아닌 격자 색깔 설정
                textColor = ContextCompat.getColor(context,R.color.imy_text_gray) // 라벨 텍스트 컬러 설정
                textSize = 13f //라벨 텍스트 크기
            }
            xAxis.run {
                position = XAxis.XAxisPosition.BOTTOM //X축을 아래에다가 둔다.
                granularity = 1f // 1 단위만큼 간격 두기
                setDrawAxisLine(false) // 축 그림
                setDrawGridLines(false) // 격자
                labelCount = 31
                textColor = ContextCompat.getColor(context,R.color.imy_text_gray) //라벨 색상
                textSize = 12f // 텍스트 크기
                val days = arrayOf("00:00", "", "", "", "", "", "06:00", "", "", "", "", "", "12:00", "", "", "", "", "", "17:00", "", "", "", "", "", "20:00", "", "", "", "", "", "23:00")
                val xAxisFormatter = MyXAxisFormatter(days)
                valueFormatter = xAxisFormatter // X축 라벨값(밑에 표시되는 글자) 바꿔주기 위해 설정
            }
            axisRight.isEnabled = false // 오른쪽 Y축을 안보이게 해줌.
            setTouchEnabled(false) // 그래프 터치해도 아무 변화없게 막음
            animateY(1000) // 밑에서부터 올라오는 애니매이션 적용
            legend.isEnabled = false //차트 범례 설정
        }

        var set = BarDataSet(entries,"DataSet") // 데이터셋 초기화

        set.color = ContextCompat.getColor(requireContext(),R.color.imy_chart)
        set.highLightColor = ContextCompat.getColor(requireContext(),R.color.imy_chart_gray)
        set.setDrawValues(false)

        val colors = mutableListOf<Int>()
        for (entry in entries) {
            if (entry.y >= 100f) {
                colors.add(ContextCompat.getColor(requireContext(),R.color.imy_chart))
            } else {
                colors.add(ContextCompat.getColor(requireContext(),R.color.imy_chart_gray))
            }
        }
        set.colors = colors


        val dataSet :ArrayList<IBarDataSet> = ArrayList()
        dataSet.add(set)
        val data = BarData(dataSet)
        data.barWidth = 0.5f //막대 너비 설정
        barChart.run {
            this.data = data //차트의 데이터를 data로 설정해줌.
            setFitBars(true)
            invalidate()
        }
    }

    inner class MyXAxisFormatter(private val days: Array<String>) : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return days.getOrNull(value.toInt()-1) ?: ""
        }
    }
}