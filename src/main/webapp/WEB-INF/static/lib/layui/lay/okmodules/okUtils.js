"use strict";
layui.define(["layer"], function (exprots) {
    var $ = layui.jquery;
    var okUtils = {
        /**
         * 是否前后端分离
         */
        isFrontendBackendSeparate: true,
        /**
         * 服务器地址
         */
        baseUrl: "/Phihong",
        /**
         * 获取body的总宽度
         */
        getBodyWidth: function () {
            return document.body.scrollWidth;
        },
        /**
         * 主要用于对ECharts视图自动适应宽度
         */
        echartsResize: function (element) {
            var element = element || [];
            window.addEventListener("resize", function () {
                var isResize = localStorage.getItem("isResize");
                // if (isResize == "false") {
                for (let i = 0; i < element.length; i++) {
                    element[i].resize();
                }
                // }
            });
        },
        /**
         * ajax()函数二次封装
         * @param url
         * @param type
         * @param params
         * @param load
         * @returns {*|never|{always, promise, state, then}}
         */
        ajax: function (url, type, params, load) {
            var deferred = $.Deferred();
            var loadIndex;
            $.ajax({
                url: okUtils.isFrontendBackendSeparate ? okUtils.baseUrl + url : url,
                type: type || "get",
                data: JSON.stringify(params) || {},
                dataType: "json",
                contentType: 'application/json',
                beforeSend: function () {
                    // console.log(url.toString()+type.toString()+data.toString());
                    if (load) {
                        loadIndex = layer.load(0, {shade: 0.3});
                    }
                },
                success: function (data) {
                    switch (data.code) {

                        case "loginsucceed": {
                            window.location = "/Phihong/phihongmain?" + Math.round(Math.random() * 10);
                            return;
                        }
                        case "regsucceed": {
                            layer.alert("注册成功", {icon: 1})
                            window.location = "/Phihong/phihongmain?" + Math.round(Math.random() * 10);
                            return;
                        }
                        case "changesucceed": {
                            layer.alert("修改密码成功", {icon: 1})
                            if (top.location != self.location) top.location = self.location;
                            return;
                        }
                        case "worktimesucceed": {

                            layer.alert("上班开始时间维护成功", {icon: 1})
                            return;
                        }

                        case "notdtsucceed": {
                            layer.alert("非Dt异常维护成功", {icon: 1})
                            return;
                        }

                        case "updatesucceed": {
                            layer.alert("推送设置成功", {icon: 1})
                            return;
                        }


                        case "updateul_llsucceed": {
                            layer.alert("上下限设置成功", {icon: 1})
                            return;
                        }

                        case "update_extimesucceed": {
                            layer.alert("设施DT维护成功", {icon: 1})
                            return;
                        }
                    }
                    layer.alert(data.code, {icon: 2});
                    deferred.reject("okUtils.ajax warn: " + data.code);
                },
                complete: function () {
                    if (load) {
                        layer.close(loadIndex);
                    }
                },
                error: function () {
                    layer.close(loadIndex);
                    layer.alert("服务器错误", {icon: 2, time: 2000});
                    deferred.reject("okUtils.ajax error: 服务器错误");
                }
            });
            return deferred.promise();
        },
        //echars
        getcode: function (url, type, params, load) {
            var index = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
            var loadIndex;
            $.ajax({
                url: okUtils.isFrontendBackendSeparate ? okUtils.baseUrl + url : url,
                type: type || "get",
                data: JSON.stringify(params) || {},
                dataType: "json",
                contentType: 'application/json',
                beforeSend: function () {
                    // console.log(url.toString()+type.toString()+data.toString());
                    if (load) {
                        loadIndex = layer.load(0, {shade: 0.3});
                    }
                },
                success: function (data) {
                    layer.close(index);
                    layer.close(loadIndex);
                    switch (data.code) {


                        case "repair": {

                            Showechar(data.PASSCOUNT, data.FAILCOUNT);
                            return false;
                        }
                        case "apikanbanmodel": {

                            Showkanbanmodel(data.model);
                            return false;
                        }

                        case "kanbanlist": {
                            try {

                                showtabledata(data.model);
                            } catch (err) {

                            }
                            try {

                                //直通率
                                fty(data.listfty)

                            } catch (err) {

                            }
                            try {

                                //前三月，前三周，当周
                                qxtu(data);

                            } catch (err) {

                            }
                            try {

                                //fty ,cqy,dt 仪表盘
                                inityibiaopan(data.listfty[0]);

                            } catch (err) {

                            }
                            try {

                                //dt 损失
                                ntf(data)

                            } catch (err) {

                            }


                            try {

                                //dt 损失
                                kanbanhead(data.listfty[0])

                            } catch (err) {

                            }

                            return false;
                        }

                    }
                    layer.close(index);
                    layer.close(loadIndex);
                    layer.alert(data.code, {icon: 2, time: 2000});

                },
                complete: function () {
                    layer.close(index);
                    if (load) {

                        layer.close(loadIndex);
                    }
                },
                error: function () {
                    layer.close(index);
                    layer.close(loadIndex);
                    layer.alert("服务器错误", {icon: 2, time: 2000});
                }
            });

            return false;
        },

        /**
         * 主要用于针对表格批量操作操作之前的检查
         * @param table
         * @returns {string}
         */
        tableBatchCheck: function (table) {
            var checkStatus = table.checkStatus("tableId");
            var rows = checkStatus.data.length;
            if (rows > 0) {
                var idsStr = "";
                for (var i = 0; i < checkStatus.data.length; i++) {
                    idsStr += checkStatus.data[i].id + ",";
                }
                return idsStr;
            } else {
                layer.msg("未选择有效数据", {offset: "t", anim: 6});
            }
        },
        /**
         * 在表格页面操作成功后弹窗提示
         * @param content
         */
        tableSuccessMsg: function (content) {
            layer.msg(content, {icon: 1, time: 1000}, function () {
                // 刷新当前页table数据
                $(".layui-laypage-btn")[0].click();
            });
        },
        /**
         * 获取父窗体的okTab
         * @returns {string}
         */
        getOkTab: function () {
            return parent.objOkTab;
        },
        /**
         * 格式化当前日期
         * @param date
         * @param fmt
         * @returns {void | string}
         */
        dateFormat: function (date, fmt) {
            var o = {
                "M+": date.getMonth() + 1,
                "d+": date.getDate(),
                "h+": date.getHours(),
                "m+": date.getMinutes(),
                "s+": date.getSeconds(),
                "q+": Math.floor((date.getMonth() + 3) / 3),
                "S": date.getMilliseconds()
            };
            if (/(y+)/.test(fmt))
                fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
            for (var k in o)
                if (new RegExp("(" + k + ")").test(fmt))
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            return fmt;
        },
        number: {
            /**
             * 判断是否为一个正常的数字
             * @param num
             */
            isNumber: function (num) {
                if (num && !isNaN(num)) {
                    return true;
                }
                return false;
            },
            /**
             * 判断一个数字是否包括在某个范围
             * @param num
             * @param begin
             * @param end
             */
            isNumberWith: function (num, begin, end) {
                if (this.isNumber(num)) {
                    if (num >= begin && num <= end) {
                        return true;
                    }
                    return false;
                }
            },
        }
    };
    exprots("okUtils", okUtils);

    // 基于准备好的dom，初始化echarts实例
    function Showechar(passcount, failcount) {
        var myChart = echarts.init(document.getElementById('main'));
        var option = {
            title: {
                text: '24 小时维修完成率',
                subtext: '24 小时维修完成率',
                left: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c} ({d}%)'
            },
            series: [
                {
                    name: '24 小时维修完成率',
                    type: 'pie',
                    radius: '80%',
                    center: ['50%', '60%'],
                    data: [
                        {value: passcount, name: '24小时内维修OK达成率', itemStyle: {color: '#12cc0e'}},
                        {value: failcount, name: '24小时未维修达成率', itemStyle: {color: '#cc1814'}},
                    ],
                    label: {
                        normal: {
                            formatter: '{b}   数量:{c}   ({d}%)',
                            textStyle: {
                                fontWeight: 'normal',
                                fontSize: 15
                            }
                        }
                    },
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        myChart.setOption(option);
        window.addEventListener("resize", function () {
            myChart.resize();
        });
    }

    function Showkanbanmodel(model) {
        $("#ProductModel").append("<option value=''>请选择一个机种" + "</option>");
        for (var i = 0; i < model.length; i++) {
            $("#ProductModel").append("<option value='" + model[i].ProductModel + "'>" + model[i].ProductModel + "</option>");
        }
        layui.use('form', function () {
            var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
            form.render();
        });
    }

    var DeleteData = [];
    var FilterArray = function (DataIndex) {
        DeleteData = filter_array(DeleteData);
        for (var i = 0; i < DeleteData.length; i++) {
            if (DeleteData[i] == DataIndex) {
                delete DeleteData[i];
            }
            DeleteData = filter_array(DeleteData);
        }
        return DeleteData;
    }

    function filter_array(array) {
        return array.filter(item => item);
    }

    function showtabledata(data) {
        let table = layui.table;
        let releft = table.render({
            elem: '#tableft',
            width: 790,
            limit: 11,
            cellMinWidth: 80, //全局定义常规单元格的最小宽度
            data: data,
            cols: [[
                {field: "工位", title: "工位", width: 130},
                {field: "產出目標_LL", title: "產出目標_LL", width: 150},
                {field: "產出目標_UL", title: "產出目標_UL", width: 150},
                {field: "達成個數", title: "達成個數", width: 130},
                {field: "應達成個數", title: "應達成個數", width: 130},
                {field: "達成率", title: "達成率", width: 100, templet: "#statusvoer"},
            ]], done: function (res, curr, count) {
                var i = 0;
                res.data.forEach(function (item, index) {
                    i += 1;
                });

                //添加到表格最后
                $(html).appendTo($(".layui-table-body>.layui-table"));
            },
            height: 550,

        });
        let reright = table.render({
            elem: '#tabright',
            data: data,
            limit: 13,
            height: 550,
            cols: [[
                {field: "0000", title: "00:00", width: 80},
                {field: "0010", title: "00:10", width: 80},
                {field: "0020", title: "00:20", width: 80},
                {field: "0030", title: "00:30", width: 80},
                {field: "0040", title: "00:40", width: 80},
                {field: "0050", title: "00:50", width: 80},
                {field: "0100", title: "01:00", width: 80},
                {field: "0110", title: "01:10", width: 80},
                {field: "0120", title: "01:20", width: 80},
                {field: "0130", title: "01:30", width: 80},
                {field: "0140", title: "01:40", width: 80},
                {field: "0150", title: "01:50", width: 80},
                {field: "0200", title: "02:00", width: 80},
                {field: "0210", title: "02:10", width: 80},
                {field: "0220", title: "02:20", width: 80},
                {field: "0230", title: "02:30", width: 80},
                {field: "0240", title: "02:40", width: 80},
                {field: "0250", title: "02:50", width: 80},
                {field: "0300", title: "03:00", width: 80},
                {field: "0310", title: "03:10", width: 80},
                {field: "0320", title: "03:20", width: 80},
                {field: "0330", title: "03:30", width: 80},
                {field: "0340", title: "03:40", width: 80},
                {field: "0350", title: "03:50", width: 80},
                {field: "0400", title: "04:00", width: 80},
                {field: "0410", title: "04:10", width: 80},
                {field: "0420", title: "04:20", width: 80},
                {field: "0430", title: "04:30", width: 80},
                {field: "0440", title: "04:40", width: 80},
                {field: "0450", title: "04:50", width: 80},
                {field: "0500", title: "05:00", width: 80},
                {field: "0510", title: "05:10", width: 80},
                {field: "0520", title: "05:20", width: 80},
                {field: "0530", title: "05:30", width: 80},
                {field: "0540", title: "05:40", width: 80},
                {field: "0550", title: "05:50", width: 80},
                {field: "0600", title: "06:00", width: 80},
                {field: "0610", title: "06:10", width: 80},
                {field: "0620", title: "06:20", width: 80},
                {field: "0630", title: "06:30", width: 80},
                {field: "0640", title: "06:40", width: 80},
                {field: "0650", title: "06:50", width: 80},
                {field: "0700", title: "07:00", width: 80},
                {field: "0710", title: "07:10", width: 80},
                {field: "0720", title: "07:20", width: 80},
                {field: "0730", title: "07:30", width: 80},
                {field: "0740", title: "07:40", width: 80},
                {field: "0750", title: "07:50", width: 80},
                {field: "0800", title: "08:00", width: 80},
                {field: "0810", title: "08:10", width: 80},
                {field: "0820", title: "08:20", width: 80},
                {field: "0830", title: "08:30", width: 80},
                {field: "0840", title: "08:40", width: 80},
                {field: "0850", title: "08:50", width: 80},
                {field: "0900", title: "09:00", width: 80},
                {field: "0910", title: "09:10", width: 80},
                {field: "0920", title: "09:20", width: 80},
                {field: "0930", title: "09:30", width: 80},
                {field: "0940", title: "09:40", width: 80},
                {field: "0950", title: "09:50", width: 80},
                {field: "1000", title: "10:00", width: 80},
                {field: "1010", title: "10:10", width: 80},
                {field: "1020", title: "10:20", width: 80},
                {field: "1030", title: "10:30", width: 80},
                {field: "1040", title: "10:40", width: 80},
                {field: "1050", title: "10:50", width: 80},
                {field: "1100", title: "11:00", width: 80},
                {field: "1110", title: "11:10", width: 80},
                {field: "1120", title: "11:20", width: 80},
                {field: "1130", title: "11:30", width: 80},
                {field: "1140", title: "11:40", width: 80},
                {field: "1150", title: "11:50", width: 80},
                {field: "1200", title: "12:00", width: 80},
                {field: "1210", title: "12:10", width: 80},
                {field: "1220", title: "12:20", width: 80},
                {field: "1230", title: "12:30", width: 80},
                {field: "1240", title: "12:40", width: 80},
                {field: "1250", title: "12:50", width: 80},
                {field: "1300", title: "13:00", width: 80},
                {field: "1310", title: "13:10", width: 80},
                {field: "1320", title: "13:20", width: 80},
                {field: "1330", title: "13:30", width: 80},
                {field: "1340", title: "13:40", width: 80},
                {field: "1350", title: "13:50", width: 80},
                {field: "1400", title: "14:00", width: 80},
                {field: "1410", title: "14:10", width: 80},
                {field: "1420", title: "14:20", width: 80},
                {field: "1430", title: "14:30", width: 80},
                {field: "1440", title: "14:40", width: 80},
                {field: "1450", title: "14:50", width: 80},
                {field: "1500", title: "15:00", width: 80},
                {field: "1510", title: "15:10", width: 80},
                {field: "1520", title: "15:20", width: 80},
                {field: "1530", title: "15:30", width: 80},
                {field: "1540", title: "15:40", width: 80},
                {field: "1550", title: "15:50", width: 80},
                {field: "1600", title: "16:00", width: 80},
                {field: "1610", title: "16:10", width: 80},
                {field: "1620", title: "16:20", width: 80},
                {field: "1630", title: "16:30", width: 80},
                {field: "1640", title: "16:40", width: 80},
                {field: "1650", title: "16:50", width: 80},
                {field: "1700", title: "17:00", width: 80},
                {field: "1710", title: "17:10", width: 80},
                {field: "1720", title: "17:20", width: 80},
                {field: "1730", title: "17:30", width: 80},
                {field: "1740", title: "17:40", width: 80},
                {field: "1750", title: "17:50", width: 80},
                {field: "1800", title: "18:00", width: 80},
                {field: "1810", title: "18:10", width: 80},
                {field: "1820", title: "18:20", width: 80},
                {field: "1830", title: "18:30", width: 80},
                {field: "1840", title: "18:40", width: 80},
                {field: "1850", title: "18:50", width: 80},
                {field: "1900", title: "19:00", width: 80},
                {field: "1910", title: "19:10", width: 80},
                {field: "1920", title: "19:20", width: 80},
                {field: "1930", title: "19:30", width: 80},
                {field: "1940", title: "19:40", width: 80},
                {field: "1950", title: "19:50", width: 80},
                {field: "2000", title: "20:00", width: 80},
                {field: "2010", title: "20:10", width: 80},
                {field: "2020", title: "20:20", width: 80},
                {field: "2030", title: "20:30", width: 80},
                {field: "2040", title: "20:40", width: 80},
                {field: "2050", title: "20:50", width: 80},
                {field: "2100", title: "21:00", width: 80},
                {field: "2110", title: "21:10", width: 80},
                {field: "2120", title: "21:20", width: 80},
                {field: "2130", title: "21:30", width: 80},
                {field: "2140", title: "21:40", width: 80},
                {field: "2150", title: "21:50", width: 80},
                {field: "2200", title: "22:00", width: 80},
                {field: "2210", title: "22:10", width: 80},
                {field: "2220", title: "22:20", width: 80},
                {field: "2230", title: "22:30", width: 80},
                {field: "2240", title: "22:40", width: 80},
                {field: "2250", title: "22:50", width: 80},
                {field: "2300", title: "23:00", width: 80},
                {field: "2310", title: "23:10", width: 80},
                {field: "2320", title: "23:20", width: 80},
                {field: "2330", title: "23:30", width: 80},
                {field: "2340", title: "23:40", width: 80},
                {field: "2350", title: "23:50", width: 80}

            ]], done: function (res, page, count) {

                var that = this.elem.next();
                var i = 0;
                res.data.forEach(function (item, index) {
                    i += 1;
                    if (i < 12) {

                        var tr = that.find("[data-index=" + index + "]").children();
                        tr.each(function () {
                            var b = $(this).text();
                            if (b >= item.產出目標_LL && b <= item.產出目標_UL) {
                                $(this).css("background-color", "#00ff7b");
                                $(this).css("color", "#fff");
                            } else if (b > item.產出目標_UL) {
                                $(this).css("background-color", "yellow");
                                //$(this).css("color", "#fff");
                            } else if (b < item.產出目標_LL) {
                                $(this).css("background-color", "red");
                                $(this).css("color", "#fff");
                            }
                        })
                    }
                });

            }
        });

        layui.use('form', function () {
            // 重载
            var form = layui.form;
            form.render();
        });

    }

    function kanbanhead(data) {
        $("#BeatCount").html(data.beatcount);
        $("#Upm").html(data.tenmrate);
        $("#qty").html((data.pyqty + data.fail_qty));
        $("#theoryqty").html(data.theoryqty);

    }
});
