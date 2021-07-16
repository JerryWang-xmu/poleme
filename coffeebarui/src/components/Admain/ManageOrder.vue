<template>
    <div class="main_welcome">
        <!-- 面包屑导航 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/mainpage', }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>订单</el-breadcrumb-item>
            <el-breadcrumb-item>订单管理</el-breadcrumb-item>
        </el-breadcrumb>
        <!-- 卡片视图区 -->
        <el-card style="width: 85%">
            <el-row :gutter="25">
                <el-col :span="10">
                    <el-input placeholder="请输入要搜索的用户号" v-model="queryInfo.query" clearable @clear="getOrderList()">
                        <el-button slot="append" icon="el-icon-search" @click="getOrderList()"></el-button>
                    </el-input>
                </el-col>
            </el-row>

            <!-- 列表 -->
            <el-table class="ordertable" :data="orderlist" border stripe>
                <el-table-column type="index"></el-table-column>
                <el-table-column label="订单号" prop="orderid"></el-table-column>
                <el-table-column label="用户号" prop="userid"></el-table-column>
                <el-table-column label="订单总价" prop="price"></el-table-column>
                <el-table-column label="订单日期" prop="date"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <!-- 详情 -->
                        <el-tooltip effect="dark" content="订单详情" placement="top-start" :enterable="false">
                            <el-button type="primary" plain @click="showDialog(scope.row.orderid)">订单详情</el-button>
                        </el-tooltip>
                    </template>
                </el-table-column>

            </el-table>

            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="queryInfo.pageNum"
                    :page-sizes="[1, 2, 5, 100]"
                    :page-size="queryInfo.pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total"
            ></el-pagination>
        </el-card>

        <!-- 详情对话框 -->
        <el-dialog title="订单详情" :visible.sync="showDialogVisible" width="50%"
        >
            <!-- 内容主体区域 -->
            <el-table class="detailable" :data="orderDetails" border stripe>
                <el-table-column type="index"></el-table-column>
                <el-table-column label="菜品名称" prop="menuname"></el-table-column>
                <el-table-column label="菜品图片">
                    　
                    <template slot-scope="scope">
                        　　　 　 <img :src="scope.row.imgurl" width="90" height="90" class="head_pic"/>
                        　　
                    </template>
                </el-table-column>
                <el-table-column label="菜品数量" prop="unitnum"></el-table-column>
                <el-table-column label="菜品单价" prop="unitprice"></el-table-column>
            </el-table>
        </el-dialog>
    </div>
</template>
<style lang="less" scoped>

    .elspan {
        position: absolute;
        transform: translate(105%, 0%);
    }

    .el-radio {
        transform: translate(120%, 0%);
    }

    .el-card {
        transform: translate(3%, 8%);
    }

    .ordertable {
        transform: translate(0, 10%);
        padding-bottom: 20px;
    }

    .el-pagination {
        padding-top: 130px;
    }
</style>
<script>
    export default {
        inject: ['reload'],
        data() {
            return {
                radio: '1',
                queryInfo: {
                    query: "",//根据用户号查询
                    currentId: window.sessionStorage.getItem('id'),
                    pageNum: 1,
                    pageSize: 5,
                },
                orderlist: [
                    //     {
                    //     orderid:"1",
                    //     userid:"2",
                    //     price:"99",
                    //     date:"2020/7/6",
                    // }
                ],// 列表
                total: 100, // 最大数据记录
                detailsQuery: {
                    orderid: "",
                },
                orderDetails: [
                    //     {
                    //         menuid:"1",
                    //         unitnum:"1",
                    //         imgurl:"https://www.starbucks.com.cn/images/products/caffe-americano.jpg",
                    //         unitprice:"9.9",

                    // }
                ],//单个的列表
                showDialogVisible: false,
            }
        },
        created() {
            this.getOrderList();
        },
        methods: {
            async getOrderList() {
                const {data: res} = await this.$http.post("allOrder", this.queryInfo);
                if (res.data != "-1") {
                    this.orderlist = res.data;
                    this.total = res.total;
                    console.log(this.total);
                } else {
                    this.$message.error("对不起，您无权访问，请检查您的登录状态！");
                    this.$router.push({path: "/home"});
                    window.sessionStorage.setItem('activePath', "/mainpage")
                    this.reload();
                }
            },
            // 监听添加
            addDialogClosed() {
                this.$refs.addFormRef.resetFields();// 重置表单项
            },
            handleSizeChange(newSize) {
                this.queryInfo.pageSize = newSize;
                this.getOrderList(); // 数据发生改变重新申请数据
            },
            // 监听pageNum改变的事件
            handleCurrentChange(newPage) {
                this.queryInfo.pageNum = newPage;
                this.getOrderList(); // 数据发生改变重新申请数据
            },
            //显示详情
            async showDialog(orderid) {
                this.showDialogVisible = true;
                this.detailsQuery.orderid = orderid;
                const {data: res} = await this.$http.post("orderDetails", this.detailsQuery);
                if (res.data != "-1") {
                    this.orderDetails = res.data;
                } else {
                    this.$message.error("对不起，您无权访问，请检查您的登录状态！");
                    this.$router.push({path: "/home"});
                    window.sessionStorage.setItem('activePath', "/mainpage")
                    this.reload();
                }

            },

        }
    }
</script>