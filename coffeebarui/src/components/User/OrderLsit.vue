<template>
    <div class="menu_star">
        <!-- 面包屑导航 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/mainpage', }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>我的订单</el-breadcrumb-item>
            <el-breadcrumb-item>订单列表</el-breadcrumb-item>
        </el-breadcrumb>

        <el-card v-if="isShow" class="box2-card">
            <div align="center">您未在本店消费，暂无订单记录！</div>
        </el-card>
        <el-card class="box-card" v-for="item in orderList" :key="item.orderid">
            <div slot="header" class="clearfix">
                <span>{{'订单号：'+item.orderid+'号 | '+item.date}}</span>
            </div>
            <el-table :data="item.detailslist" style="width: 100%">
                <el-table-column label="菜品图片">
                    <template slot-scope="scope">
                        <img :src="scope.row.imgurl" width="70" height="70" class="head_pic" />
                    </template>
                </el-table-column>
                <el-table-column prop="menuname" label="菜品名称"></el-table-column>
                <el-table-column prop="unitprice" label="菜品价格"></el-table-column>
                <el-table-column prop="unitnum" label="菜品数量"></el-table-column>
            </el-table>
            <div class="text item">{{'总价: ￥'+item.price }}</div>
        </el-card>
    </div>
</template>


<script>
    export default {
        data() {
            return {
                // queryInfo: {
                    currentId: window.sessionStorage.getItem("id"),
                // },
                total:"",
                isShow:false,
                orderList: [
                    // {
                    //   orderid: "1",
                    //   date: "2020-07-06 15:07:18",
                    //   price: "68",
                    //   detailslist: [
                    //     {
                    //       menuname: "卡布奇诺",
                    //       unitnum: "1",
                    //       imgurl:"https://www.starbucks.com.cn/images/products/cappuccino.jpg",
                    //       unitprice: "29"
                    //     },
                    //     {
                    //       menuname: "拿铁",
                    //       unitnum: "1",
                    //       imgurl:"https://www.starbucks.com.cn/images/products/caffe-latte.jpg",
                    //       unitprice: "39"
                    //     }
                    //   ]
                    // },
                    // {
                    //   orderid: "2",
                    //   date: "2020-07-07 15:07:18",
                    //   price: "58",
                    //   detailslist: [
                    //     {
                    //       menuid: "3",
                    //       menuname: "冰美式",
                    //       unitnum: "1",
                    //       imgurl:
                    //         "https://www.starbucks.com.cn/images/products/caffe-americano.jpg",
                    //       unitprice: "19"
                    //     },
                    //     {
                    //       menuid: "5",
                    //       menuname: "拿铁",
                    //       unitnum: "1",
                    //       imgurl:
                    //         "https://www.starbucks.com.cn/images/products/caffe-americano.jpg",
                    //       unitprice: "39"
                    //     }
                    //   ]
                    // }
                ]
            };
        },
        created() {
            this.getOrderList();
        },

        methods: {
            async getOrderList() {
                console.log(this.queryInfo);
                const { data: res } = await this.$http.get("userOrder/"+ this.currentId);//todo
                if (res.status == "error"){
                    this.isShow=true;
                    return this.$message("您未在本店消费，暂无订单记录！");
                }
                this.orderList = res.orderList;
                this.total=res.total;
                console.log("debug");
                console.log(this.orderList);
            }
        }
    };
</script>
<style lang="less" scoped>
    .text {
        font-size: 18px;
        text-align: right;
    }

    .item {
        margin-bottom: 3px;
    }

    .box-card {
        width: 80%;
        transform: translate(10%, 10%);
        margin-bottom: 18px;
        padding: 10px;
    }

    .box2-card {
        width: 400px;
        transform: translate(70%, 20%);
    }
</style>

<style>
</style>
