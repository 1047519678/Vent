function inityibiaopan(item) {
    var myChart = echarts.init(document.getElementById('oil'));
    var option = {
        tooltip: {
            formatter: "{a} <br/>{b} : {c}%"
        },
        toolbox: {
            feature: {
                restore: {},
                saveAsImage: {
                    name: '三合一仪表盘视图'
                }
            }
        },
        series: [{
            name: '直通率',
            type: 'gauge',
            center: ['70%', '70%'], // 默认全局居中
            radius: '75%',
            detail: {
                formatter: '{value}%'
            },
            data: [{
                value: item.cqy_fty,
                name: '直通率'
            }],
            axisLine: { // 坐标轴线
                lineStyle: { // 属性lineStyle控制线条样式
                    color: [
                        [0.79, 'red'],
                        [0.80, 'green'],
                        [1, 'green']
                    ],
                    width: 2,
                    shadowColor: '#fff', //默认透明
                    shadowBlur: 10
                }
            },
            axisLabel: { // 坐标轴小标记
                textStyle: { // 属性lineStyle控制线条样式
                    fontWeight: 'bolder',
                    color: 'green',
                    shadowColor: '#fff', //默认透明
                    shadowBlur: 10
                }
            },
            title: {
                textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    fontWeight: 'bolder',
                    fontSize: 20,
                    // fontStyle: 'italic',
                    color: 'green',
                    shadowColor: '#fff', //默认透明
                    shadowBlur: 10
                }
            },
        }, {
            name: '综合合格率',
            type: 'gauge',
            radius: '80%',
            detail: {
                formatter: '{value}%'
            },
            data: [{
                value: item.cqy,
                name: '综合合格率',

            }],
            axisLine: { // 坐标轴线
                lineStyle: { // 属性lineStyle控制线条样式
                    color: [
                        [0.7, 'red'],
                        [0.86, '#4682B4'],
                        [1, '#4682B4']
                    ],
                    width: 2,
                    shadowColor: '#fff', //默认透明
                    shadowBlur: 10
                }
            },
            axisLabel: { // 坐标轴小标记
                textStyle: { // 属性lineStyle控制线条样式
                    fontWeight: 'bolder',
                    color: '#4682B4',
                    shadowColor: '#fff', //默认透明
                    shadowBlur: 10
                }
            },
            title: {
                textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    fontWeight: 'bolder',
                    fontSize: 20,
                    // fontStyle: 'italic',
                    color: '#4682B4',
                    shadowColor: '#fff', //默认透明
                    shadowBlur: 10
                }
            },

        },
            {
                name: '损失率',
                type: 'gauge',
                center: ['30%', '70%'], // 默认全局居中
                radius: '75%',
                detail: {
                    formatter: '{value}%'
                },
                data: [{
                    value: item.cqy_dt,
                    name: '损失率'
                }],
                axisLine: { // 坐标轴线
                    lineStyle: { // 属性lineStyle控制线条样式
                        color: [
                            [0.05, 'green'],
                            [0.86, 'red'],
                            [1, '#ff4500']
                        ],
                        width: 2,
                        shadowColor: '#fff', //默认透明
                        shadowBlur: 10
                    }
                },
                axisLabel: { // 坐标轴小标记
                    textStyle: { // 属性lineStyle控制线条样式
                        fontWeight: 'bolder',
                        color: 'red',
                        shadowColor: '#fff', //默认透明
                        shadowBlur: 10
                    }
                },
                title: {
                    textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                        fontWeight: 'bolder',
                        fontSize: 20,
                        //z粗体/斜体
                        // fontStyle: 'italic',
                        color: 'red',
                        shadowColor: '#fff', //默认透明
                        shadowBlur: 10
                    }
                },
            }
        ]
    }

    myChart.setOption(option);
    //qxtu();
}


function qxtu(xdata) {
    var ftydata = new Array;
    var dtdata = new Array;
    var cqydata=new Array;
    var xdatemonth3 = 0, xdatemonth2 = 0, xdatemonth1 = 0, xdateweek3 = 0, xdateweek2 = 0, xdateweek1 = 0
    var xdatemonth3dt = 0, xdatemonth2dt = 0, xdatemonth1dt = 0, xdateweek3dt = 0, xdateweek2dt = 0, xdateweek1dt = 0
    var xdatemonth3cqy = 0, xdatemonth2cqy = 0, xdatemonth1cqy = 0, xdateweek3cqy = 0, xdateweek2cqy = 0, xdateweek1cqy = 0
    if (!(xdata.xdatemonth3.length == 0)) {
        xdatemonth3 = xdata.xdatemonth3[0].Fty;
    }
    if (!(xdata.xdatemonth2.length == 0)) {
        xdatemonth2 = xdata.xdatemonth2[0].Fty;
    }
    if (!(xdata.xdatemonth1.length == 0)) {
        xdatemonth1 = xdata.xdatemonth1[0].Fty;
    }
    if (!(xdata.xdateweek3.length == 0)) {
        xdateweek3 = xdata.xdateweek3[0].Fty;
    }
    if (!(xdata.xdateweek2.length == 0)) {
        xdateweek2 = xdata.xdateweek2[0].Fty;
    }
    if (!(xdata.xdateweek1.length == 0)) {
        xdateweek1 = xdata.xdateweek1[0].Fty;
    }
    //DT
    if (!(xdata.xdatemonth3.length == 0)) {
        xdatemonth3dt = xdata.xdatemonth3[0].Dt;
    }
    if (!(xdata.xdatemonth2.length == 0)) {
        xdatemonth2dt = xdata.xdatemonth2[0].Dt;
    }
    if (!(xdata.xdatemonth1.length == 0)) {
        xdatemonth1dt = xdata.xdatemonth1[0].Dt;
    }
    if (!(xdata.xdateweek3.length == 0)) {
        xdateweek3dt = xdata.xdateweek3[0].Dt;
    }
    if (!(xdata.xdateweek2.length == 0)) {
        xdateweek2dt = xdata.xdateweek2[0].Dt;
    }
    if (!(xdata.xdateweek1.length == 0)) {
        xdateweek1dt = xdata.xdateweek1[0].Dt;
    }
    //cqy
    if (!(xdata.xdatemonth3.length == 0)) {
        xdatemonth3cqy = xdata.xdatemonth3[0].Cqy;
    }
    if (!(xdata.xdatemonth2.length == 0)) {
        xdatemonth2cqy = xdata.xdatemonth2[0].Cqy;
    }
    if (!(xdata.xdatemonth1.length == 0)) {
        xdatemonth1cqy = xdata.xdatemonth1[0].Cqy;
    }
    if (!(xdata.xdateweek3.length == 0)) {
        xdateweek3cqy = xdata.xdateweek3[0].Cqy;
    }
    if (!(xdata.xdateweek2.length == 0)) {
        xdateweek2cqy= xdata.xdateweek2[0].Cqy;
    }
    if (!(xdata.xdateweek1.length == 0)) {
        xdateweek1cqy = xdata.xdateweek1[0].Cqy;
    }

    ftydata.push(xdatemonth3)
    ftydata.push(xdatemonth2)
    ftydata.push(xdatemonth1)
    ftydata.push(xdateweek1)
    ftydata.push(xdateweek2)
    ftydata.push(xdateweek3)
    //dt
    dtdata.push(xdatemonth3dt)
    dtdata.push(xdatemonth2dt)
    dtdata.push(xdatemonth1dt)
    dtdata.push(xdateweek1dt)
    dtdata.push(xdateweek2dt)
    dtdata.push(xdateweek3dt)

    //cqy
    cqydata.push(xdatemonth3cqy)
    cqydata.push(xdatemonth2cqy)
    cqydata.push(xdatemonth1cqy)
    cqydata.push(xdateweek1cqy)
    cqydata.push(xdateweek2cqy)
    cqydata.push(xdateweek3cqy)
    var myChart = echarts.init(document.getElementById('qxtu'));
    var option = {
        tooltip: {
            trigger: 'axis',
            show: true,
        },
        toolbox: {
            show: true,
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: {
                    readOnly: false
                },
                magicType: {
                    type: ['line', 'bar']
                },
                restore: {},
                saveAsImage: {
                    name: '三合一视图'
                }
            }
        },
        legend: {
            data: ["直通率", "综合合格率", "损失率"]
        },
        xAxis: [{
            type: 'category',
            data: [xdata.xdatemap.xdatemonth3, xdata.xdatemap.xdatemonth2, xdata.xdatemap.xdatemonth1, xdata.xdatemap.xdateweek3, xdata.xdatemap.xdateweek2, xdata.xdatemap.xdateweek1, xdata.xdatemap.xdatenewweek1, xdata.xdatemap.xdatenewweek2, xdata.xdatemap.xdatenewweek3, xdata.xdatemap.xdatenewweek4, xdata.xdatemap.xdatenewweek5, xdata.xdatemap.xdatenewweek6, xdata.xdatemap.xdatenewweek7],
            axisLabel: {
                interval: 0,
                rotate: 40
            }
        }],
        yAxis: [{
            type: 'value',
            name: '单位:%',
            axisLabel: {
                formatter: '{value}%',
            }
        }],
        series: [{
            name: "直通率",
            type: "line",
            axisLabel: {
                formatter: '{value}'
            },
            label: {
                show: true,
                position: 'inside',
                formatter: '{value}%'
            },
            data: ftydata,
            itemStyle: {
                normal: {
                    color:'green',
                    // color: function (params) {
                    //     var datacount = params.value;
                    //     var colorList = ['rgb(0, 128, 0)', 'rgb(255, 0, 0)'];
                    //     if (datacount > 90) {
                    //         return colorList[0];
                    //     } else {
                    //         return colorList[1];
                    //     }
                    // },
                    //鼠标悬停时：
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    },
                    label: {
                        show: true,
                        formatter: '{c}%'
                    }
                }
            },


            markLine: {
                lineStyle: {
                    normal: {
                        color: 'green'
                    }
                },
                data: [{
                    name: '基准线',
                    yAxis: 90
                }]
            },

        }, {
            name: "综合合格率",
            type: "line",
            axisLabel: {
                formatter: '{value}'
            },
            label: {
                show: true,
                position: 'inside',
                formatter: '{value}%'

            },
            data: [0,1,2],
            itemStyle: {
                normal: {
                    color:'Blue',
                    // color: function (params) {
                    //     var datacount = params.value;
                    //     var colorList = ['rgb(15, 50, 202)', 'rgb(255, 0, 0)'];
                    //     if (datacount > 90) {
                    //         return colorList[0];
                    //     } else {
                    //         return colorList[1];
                    //     }
                    // },
                    //鼠标悬停时：
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    },
                    label: {
                        show: true,
                        formatter: '{c}%'
                    }
                }
            }
        }, {
            name: "损失率",
            type: "line",
            axisLabel: {
                formatter: '{value}'
            },
            label: {
                show: true,
                position: 'inside',
                formatter: '{value}%'

            },
            data: dtdata,
            itemStyle: {
                normal: {
                    color: 'red',
                    label: {
                        show: true,
                        formatter: '{c}%'
                    }
                }
            }
        }]
    };

    myChart.setOption(option);

}



function sort(arr) {
    for (var i = 0; i < arr.length - 1; i++) {
        for (var j = 0; j < arr.length - i - 1; j++) {
            if (arr[j].dt < arr[j + 1].dt) {// 相邻元素两两对比
                var hand = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = hand;

            }
            // console.log('i:' + i + ' j:' + j + '  当前数组为：' + arr);
        }
    }
    return arr;
}

var ar = [5, 100, 6, 3, -12];
sort(ar);

function ntf(xydata) {
    //top3在前显示
    sort(xydata.listfty)
    var xdata = new Array;
    var ydata = new Array;
    if (xydata != "undefined") {
        for (var i = 0; i < xydata.listfty.length; i++) {
            xdata.push(xydata.listfty[i].workstation)
            ydata.push(xydata.listfty[i].dt)
        }
    } else {
        return;
    }
    var myChart = echarts.init(document.getElementById('ntf'));
    var option = {
        tooltip: {
            trigger: 'axis',
            show: true
        },
        toolbox: {
            show: true,
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: {
                    readOnly: false
                },
                magicType: {
                    type: ['line', 'bar']
                },
                restore: {},
                saveAsImage: {
                    name: '损失率'
                }
            }
        },
        legend: {
            data: ["损失率"]
        },
        xAxis: [{
            type: 'category',
            data: xdata,
            axisLabel: {
                interval: 0,
                rotate: 40
            }
        }],
        yAxis: [{
            type: 'value',
            name: '单位:%',
            axisLabel: {
                formatter: '{value}%',
            }
        }],
        series: [{
            name: "损失率",
            type: "bar",
            axisLabel: {
                formatter: '{value}%'
            },
            label: {
                show: true,
                position: 'inside',
                formatter: '{value}%'

            },
            data: ydata,
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        formatter: '{c}%'
                    }
                    ,
                    color: function (params) {
                        var datacount = params.value;
                        var colorList = ['rgb(255,0,0)', 'rgb(189,13,13)'];
                        if (params.dataIndex < 3) {
                            return colorList[0];
                        } else {
                            return colorList[1];
                        }

                    }

                }
            },


            markLine: {
                lineStyle: {
                    normal: {
                        color: 'green'
                    }
                },
                data: [{
                    name: '基准线',
                    yAxis: 4
                }]
            },

        },]
    };

    myChart.setOption(option);
    //  fty();
}


function fty(listfty) {
    var xdata = new Array;
    var ydata = new Array;
    if (listfty != "undefined") {
        for (var i = 0; i < listfty.length; i++) {
            xdata.push(listfty[i].workstation)
            ydata.push(listfty[i].theoryfty)
        }
    } else {
        return;
    }
    var myChart = echarts.init(document.getElementById('fty'));
    var option = {
        tooltip: {
            trigger: 'axis',
            show: true
        },
        toolbox: {
            show: true,
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: {
                    readOnly: false
                },
                magicType: {
                    type: ['line', 'bar']
                },
                restore: {},
                saveAsImage: {
                    name: '直通率'
                }
            }
        },
        legend: {
            data: ["直通率"]
        },
        xAxis: [{
            type: 'category',
            data: xdata,
            axisLabel: {
                interval: 0,
                rotate: 40
            }
        }],
        yAxis: [{
            type: 'value',
            name: '单位:%',
            axisLabel: {
                formatter: '{value}%',
            }
        }],
        series: [{
            name: "直通率",
            type: "bar",
            axisLabel: {
                formatter: '{value}%'
            },
            label: {
                show: true,
                position: 'top',
                formatter: '{value}%'

            },
            data: ydata,
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        formatter: '{c}%'
                    },
                    color: function (params) {
                        var datacount = params.value;
                        var colorList = ['rgb(0, 128, 0)', 'rgb(255, 0, 0)'];
                        if (params.dataIndex < 3) {
                            return colorList[1];
                        } else {
                            if (datacount > 90) {
                                return colorList[0];
                            } else {
                                return colorList[1];
                            }
                        }

                    }
                },
                //鼠标悬停时：
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }

            },

            markLine: {
                lineStyle: {
                    normal: {
                        color: 'green'
                    }
                },
                data: [{
                    name: '基准线',
                    yAxis: 90
                }]
            },

        },]
    };
    myChart.setOption(option);
}