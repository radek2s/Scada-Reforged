import { Root, Scrollbar, time, Tooltip } from "@amcharts/amcharts5";
import { AxisRendererX } from "@amcharts/amcharts5/.internal/charts/xy/axes/AxisRendererX";
import { AxisRendererY } from "@amcharts/amcharts5/.internal/charts/xy/axes/AxisRendererY";
import { DateAxis } from "@amcharts/amcharts5/.internal/charts/xy/axes/DateAxis";
import { ValueAxis } from "@amcharts/amcharts5/.internal/charts/xy/axes/ValueAxis";
import { LineSeries } from "@amcharts/amcharts5/.internal/charts/xy/series/LineSeries";
import { XYChart } from "@amcharts/amcharts5/.internal/charts/xy/XYChart";
import { XYCursor } from "@amcharts/amcharts5/.internal/charts/xy/XYCursor";
import { AfterViewChecked, Component, Input, OnInit } from "@angular/core";
import am5themes_Animated from "@amcharts/amcharts5/themes/Animated";


@Component({
    selector: 'amchart-component',
    templateUrl: './amchart.component.html'
})
export default class AmChartComponent implements OnInit {

    @Input() data?: any[]

    private root!: Root
    private chart!: XYChart
    private date!: Date
    private value = 100;
    

    constructor() { }
    ngOnInit(): void {
        this.root = Root.new("chartdiv")
        this.chart = this.root.container.children.push(XYChart.new(this.root, {
            panX: true,
            panY: true,
            wheelX: "panX"
        }))

        this.root.setThemes([
            am5themes_Animated.new(this.root)
          ])

        const cursor = this.chart.set("cursor", XYCursor.new(this.root, {
            behavior: "none"
        }))


        cursor.lineY.set("visible", false)

        let xAxis = this.chart.xAxes.push(DateAxis.new(this.root, {
            maxDeviation: 0.2,
            baseInterval: {
                timeUnit: "second",
                count: 1
            },
            renderer: AxisRendererX.new(this.root, {}),
            tooltip: Tooltip.new(this.root, {})
        }));

        let yAxis = this.chart.yAxes.push(ValueAxis.new(this.root, {
            renderer: AxisRendererY.new(this.root, {})
        }));


        // Add series
        // https://www.amcharts.com/docs/v5/charts/xy-chart/series/
        let series = this.chart.series.push(LineSeries.new(this.root, {
            name: "Series",
            xAxis: xAxis,
            yAxis: yAxis,
            valueYField: "value",
            valueXField: "time",
            tooltip: Tooltip.new(this.root, {
                labelText: "{valueY}"
            })
        }));


        // Add scrollbar
        // https://www.amcharts.com/docs/v5/charts/xy-chart/scrollbars/
        this.chart.set("scrollbarX", Scrollbar.new(this.root, {
            orientation: "horizontal"
        }));


        // Set data
        // let data = this.generateDatas(1200);
        console.log(this.data)
        series.data.setAll(this.data || []);


        // Make stuff animate on load
        // https://www.amcharts.com/docs/v5/concepts/animations/
        series.appear(1000);
        // this.chart.appear()
        this.chart.appear(1000, 100)
    }

    generateData() {
        let date = new Date()
        date.setHours(0, 0, 0, 0)
        time.add(date, "day", 1)
        return {
            date: date.getTime(),
            value: Math.round((Math.random() * 10 - 5) + this.value)
        }
    }

    generateDatas(count: number) {
        let data = []
        for (var i = 0; i < count; ++i) {
            data.push(this.generateData())
        }
        return data
    }



    


    // Create axes
    // https://www.amcharts.com/docs/v5/charts/xy-chart/axes/

}
