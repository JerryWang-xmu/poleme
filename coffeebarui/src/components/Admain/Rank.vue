<template>
    <div class="menu_star">
        <!-- 面包屑导航 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/mainpage', }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>菜单</el-breadcrumb-item>
            <el-breadcrumb-item>排行榜</el-breadcrumb-item>
        </el-breadcrumb>

        <el-row :gutter="40">
            <el-col :span="6" v-for="item in topList" :key="item.id" style="margin-bottom:100px">
                <div class="avatar_box" @mouseover="mouseOver" @mouseleave="mouseLeave">
                    <img :src="item.imgurl" @click="jump(item.id)" alt />
                    <h1 align="center">{{'NO.'+item.order+item.name}}</h1>
                </div>
            </el-col>
        </el-row>

        <el-card class="box-card" >
            <el-table :data="allList" style="width: 100%">
                <el-table-column prop="order" label="欢迎度排序"></el-table-column>
                <el-table-column label="菜品图片">
                    <template slot-scope="scope">
                        <img :src="scope.row.imgurl" width="70" height="70" class="head_pic" />
                    </template>
                </el-table-column>
                <el-table-column prop="name" label="菜品名称"></el-table-column>
                <el-table-column prop="price" label="菜品价格"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <!-- 详情 -->
                        <el-tooltip effect="dark" content="详情" placement="top-start" :enterable="false">
                            <el-button type="primary" plain  @click="jump(scope.row.id)">菜品详情</el-button>
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
                    class="el-pagi"
            ></el-pagination>

        </el-card>
    </div>
</template>


<script>
    export default {
        data() {
            return {
                queryInfo: {
                    pageNum: 1,
                    pageSize: 5
                },
                total: 10, // 最大数据记录

                topList: [
                    // {
                    //   order:1,
                    //   id: 5,
                    //   name: "卡布奇诺",
                    //   price: "39",
                    //   imgurl: "https://www.starbucks.com.cn/images/products/cappuccino.jpg"
                    // },
                    // {
                    //   order:2,
                    //   id: 6,
                    //   name: "拿铁",
                    //   price: "39",
                    //   imgurl: "https://www.starbucks.com.cn/images/products/caffe-latte.jpg"
                    // },
                    // {
                    //   order:3,
                    //   id: 7,
                    //   name: "馥芮白",
                    //   unitprice: "39",
                    //   imgurl: "https://www.starbucks.com.cn/images/products/flat-white.jpg"
                    // }
                ],
                allList: [
                    //   {
                    //  order:4,
                    //   id: 2,
                    //   name: "卡布奇诺",
                    //   price: "39",
                    //   imgurl: "https://www.starbucks.com.cn/images/products/cappuccino.jpg"
                    // },
                    // {
                    //   order:5,
                    //   id: 3,
                    //   name: "拿铁",
                    //   price: "39",
                    //   imgurl: "https://www.starbucks.com.cn/images/products/caffe-latte.jpg"
                    // },
                    // {
                    //   order:6,
                    //   id: 4,
                    //   name: "馥芮白",
                    //   price: "39",
                    //   imgurl: "https://www.starbucks.com.cn/images/products/flat-white.jpg"
                    // }
                ]
            };
        },
        created() {
            this.getList();
            this.gettopList();
        },

        methods: {
            async getList() {
                const {data: res} = await this.$http.post("allrank", this.queryInfo);
                if (res.flag == "ok") {
                    this.allList = res.data;
                    this.total = res.total;
                } else {
                    this.$message.error("信息获取失败！");
                }
            },
            async gettopList() {
                const {data: res} = await this.$http.post("toprank");
                if (res.flag == "ok") {
                    this.topList = res.data;
                    this.total = res.total;
                } else {
                    this.$message.error("信息获取失败！");
                }
            },

            jump(identity) {
                window.sessionStorage.setItem("menuId", identity);
                this.$router.push("/goods");
            },
            mouseOver(event) {
                let _el = event.currentTarget;
                this.active = "transform: translate(0%, -5%)";
                _el.setAttribute("style", this.active);
            },
            mouseLeave(event) {
                let _el = event.currentTarget;
                this.active = "";
                _el.setAttribute("style", this.active);
            },
            handleSizeChange(newSize) {
                this.queryInfo.pageSize = newSize;
                this.getList(); // 数据发生改变重新申请数据
            },
            // 监听pageNum改变的事件
            handleCurrentChange(newPage) {
                this.queryInfo.pageNum = newPage;
                this.getList(); // 数据发生改变重新申请数据
            },
        }
    };
</script>
<style lang="less" scoped>
    .el-row {
        padding-top: 90px;
        padding-left: 100px;
    }

    .box-card {
        width: 80%;
        transform: translate(10%, 10%);
        margin-bottom: 100px;
        padding: 10px;
    }

    .avatar_box {
        margin-right:40px;
        margin-left:140px;
        width: 130px;
        height: 130px;
        border: 1px solid #eee;
        border-radius: 50%;
        padding: 10px;
        box-shadow: 0 0 10px #ddd;
        /*position: absolute;*/
        left: 50%;
        /*transform: translate(-50%, -50%);*/
        background-color: #ffffff;
        cursor: pointer;

        img {
            width: 100%;
            height: 100%;
            border-radius: 50%;
        }
    }

    .el-pagi{
        margin-bottom: 30px;
        margin-top: 30px ;
    }

</style>
